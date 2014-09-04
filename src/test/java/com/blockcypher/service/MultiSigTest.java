package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.address.Address;
import com.blockcypher.model.transaction.Transaction;
import com.blockcypher.model.transaction.intermediary.IntermediaryTransaction;
import com.blockcypher.utils.sign.SignUtils;
import com.blockcypher.utils.gson.GsonFactory;
import com.google.bitcoin.core.AddressFormatException;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.websocket.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Test for MultiSig BlockCypher API
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class MultiSigTest extends AbstractServiceTest {

    private static final Logger logger = Logger.getLogger(MultiSigTest.class);

    private static final String EVENT_PING = "{\"event\": \"ping\"}";
    private static final String EVENT_PONG = "{\"event\": \"pong\"}";

    @Test
    public void testMultiSigWithWebsocketOnConfirmedTransaction() throws BlockCypherException, AddressFormatException, IOException, DeploymentException {
        //System.getProperties().put("javax.net.debug", "all");

        Address addressA = blockCypherContext.getAddressService().createAddress();
        logger.info(MessageFormat.format("Adress {0} created", addressA.getAddress()));
        Address addressB = blockCypherContext.getAddressService().createAddress();
        logger.info(MessageFormat.format("Adress {0} created", addressB.getAddress()));
        Address addressC = blockCypherContext.getAddressService().createAddress();
        logger.info(MessageFormat.format("Adress {0} created", addressC.getAddress()));

        // Noneed for this
        //fillAddress(addressA.getAddress(), addressB.getAddress(), addressC.getAddress());

        IntermediaryTransaction unsignedTransaction = blockCypherContext.getTransactionService()
                .newFundingTransaction(new ArrayList<String>(Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz")),
                        new ArrayList<String>(Arrays.asList(addressA.getPublic(), addressB.getPublic(), addressC.getPublic())),
                        25000, false
                );
        logger.info("Unsigned 1: " + unsignedTransaction);
        SignUtils.signWithBase58KeyWithPubKey(unsignedTransaction, MY_PRIVATE_KEY);

        Transaction transaction = blockCypherContext.getTransactionService().sendTransaction(unsignedTransaction);
        logger.info(MessageFormat.format("Sent transaction {0} to address {1} of {2} BTC sent", transaction.getHash(),
                transaction.getOutputs().get(0).getAddresses().get(0),
                transaction.getOutputs().get(0).getValue().divide(new BigDecimal(100000000))));

        waitTransactionConfirmedWithWebsocket(transaction);
        //waitTransactionConfirmedByPullingEvery30Seconds(transaction);

        // new 2-of-3 Transaction Signed aggainst Key C
        IntermediaryTransaction unsignedTransaction2Of3C = blockCypherContext.getTransactionService()
                .newFundingTransaction(new ArrayList<String>(Arrays.asList(addressA.getPublic(), addressB.getPublic(), addressC.getPublic())),
                        Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"),
                        15000, true
                );
        logger.info("Unsigned 2Of3C: " + unsignedTransaction2Of3C);
        // BlockCypher returns the hex string, not the WIF Base 58 key
        SignUtils.signWithHexKeyNoPubKey(unsignedTransaction2Of3C, addressC.getPrivate());
        Transaction transaction2Of3C = blockCypherContext.getTransactionService().sendTransaction(unsignedTransaction2Of3C);
        logger.info(MessageFormat.format("Sent transaction {0} to address {1} of {2} BTC sent", transaction2Of3C.getHash(),
                transaction2Of3C.getOutputs().get(0).getAddresses().get(0),
                transaction2Of3C.getOutputs().get(0).getValue().divide(new BigDecimal(100000000))));

        // new 2-of-3 Transaction Signed aggainst Key A
        IntermediaryTransaction unsignedTransaction2Of3A = blockCypherContext.getTransactionService()
                .newFundingTransaction(new ArrayList<String>(Arrays.asList(addressA.getPublic(), addressB.getPublic(), addressC.getPublic())),
                        Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"),
                        15000, true
                );
        logger.info("Unsigned 2Of3A: " + unsignedTransaction2Of3A);

        SignUtils.signWithHexKeyNoPubKey(unsignedTransaction2Of3A, addressA.getPrivate());
        Transaction transaction2Of3A = blockCypherContext.getTransactionService().sendTransaction(unsignedTransaction2Of3A);
        logger.info(MessageFormat.format("Sent transaction {0} to address {1} of {2} BTC sent", transaction2Of3A.getHash(),
                transaction2Of3A.getOutputs().get(0).getAddresses().get(0),
                transaction2Of3A.getOutputs().get(0).getValue().divide(new BigDecimal(100000000))));

        waitTransactionConfirmedWithWebsocket(transaction2Of3A);
    }

    private void waitTransactionConfirmedWithWebsocket(Transaction transaction) throws DeploymentException, IOException {
        WSClient wsClient = new WSClient();
        wsClient.setTxHash(transaction.getHash());

        WebSocketContainer container = null;
        container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(wsClient, URI.create(MessageFormat.format(
                "wss://socket.blockcypher.com/{0}/{1}/{2}",
                blockCypherContext.getEndpointConfig().getVersion(),
                blockCypherContext.getEndpointConfig().getCurrency(),
                blockCypherContext.getEndpointConfig().getNetwork()
        )));
        wsClient.wait4TerminateSignal();
    }

    private void waitTransactionConfirmedByPullingEvery30Seconds(Transaction transaction) {
        while (true) {
            Transaction transactionFetched = null;
            try {
                transactionFetched = blockCypherContext.getTransactionService()
                        .getTransaction(transaction.getHash());
                if (transactionFetched.getConfirmations() > 0) {
                    logger.info(MessageFormat.format("Transaction {0} confirmed", transactionFetched.getHash()));
                    break;
                }
            } catch (BlockCypherException e) {
                logger.error(MessageFormat.format("Exception while getting transaction {0}", transaction.getHash()), e);
            }
            try {
                Thread.sleep(30000);
                logger.debug(MessageFormat.format("Transaction {0} not confirmed yet", transaction.getHash()));
            } catch (InterruptedException e) {
                logger.error("Error while sleep", e);
            }
        }
    }

    private void fillAddress(String address, String address1, String address2) throws BlockCypherException, IOException, DeploymentException {
        IntermediaryTransaction unsignedTransaction = blockCypherContext.getTransactionService()
                .newTransaction(new ArrayList<String>(Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz")),
                        new ArrayList<String>(Arrays.asList(address, address1, address2)),
                        250000
                );
        SignUtils.signWithBase58KeyWithPubKey(unsignedTransaction, MY_PRIVATE_KEY);

        Transaction transaction = blockCypherContext.getTransactionService().sendTransaction(unsignedTransaction);
        logger.info("Filling address Transaction sent: " + transaction);
        //waitTransactionConfirmedByPullingEvery30Seconds(transaction);
        waitTransactionConfirmedWithWebsocket(transaction);
    }

    @ClientEndpoint
    public class WSClient {
        private CountDownLatch transactionConfirmed = new CountDownLatch(1);
        private String txHash;

        public void setTxHash(String txHash) {
            this.txHash = txHash;
        }

        private void wait4TerminateSignal() {
            try {
                transactionConfirmed.await();
            } catch (InterruptedException e) {
                logger.error("Error while await", e);
            }
        }

        @OnMessage
        public void onMessage(String message, Session session) {
            if (EVENT_PONG.equals(message)) {
                logger.info("Pong!");
            } else {
                logger.info("Received msg: " + message);
                Transaction transaction = GsonFactory.getGson().fromJson(message, Transaction.class);
                if (transaction.getHash().equals(txHash) && transaction.getConfirmations() > 0) {
                    logger.info(MessageFormat.format("Transaction {0} has {1} confirmation(s)",
                            transaction.getHash(), transaction.getConfirmations()));
                    transactionConfirmed.countDown();
                }
            }
        }

        @OnOpen
        public void onOpen(Session session) {
            logger.info("WebSocket opened: " + session.getId());
            final RemoteEndpoint.Basic remote = session.getBasicRemote();
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("filter", "event=new-block-tx&hash="+ txHash);
                logger.info("Sent " + jsonObject);
                remote.sendText(jsonObject.toString());
                pingRegularly(remote);
            } catch (IOException e) {
                logger.error("onOpen", e);
            }
        }

        private void pingRegularly(RemoteEndpoint.Basic remote) {
            // Send regularly ping otherwise the websocket closes after 60 seconds
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            service.scheduleAtFixedRate(new Ping(remote), 0, 20, TimeUnit.SECONDS);
        }

        @OnClose
        public void onClose(Session session) {
            logger.info("WebSocket closed: " + session.getId());
        }

        @OnError
        public void onError(Session session, Throwable t) {
            logger.error("WebSocket error: " + session.getId(), t);
        }

        private class Ping implements Runnable {
            RemoteEndpoint.Basic remote;

            public Ping(RemoteEndpoint.Basic remote) {
                this.remote = remote;
            }

            @Override
            public void run() {
                logger.info("Ping!");
                try {
                    //remote.sendPing(ByteBuffer.wrap(EVENT_PING.getBytes()));
                    remote.sendText(EVENT_PING);
/*                    Transaction transactionFetched = blockCypherContext.getTransactionService()
                            .getTransaction(txHash);
                    if (transactionFetched.getConfirmations() > 0) {
                        logger.info(MessageFormat.format("Transaction {0} confirmed with {1} confirmations",
                                transactionFetched.getHash(), transactionFetched.getConfirmations()));
                    } else {
                        logger.info(MessageFormat.format("Transaction {0} not confirmed yet", transactionFetched.getHash()));
                    }*/
                } catch (IOException e) {
                    logger.error("Error while Pinging", e);
                } /*catch (BlockCypherException e) {
                    logger.error("Error while Querying Transaction", e);
                }*/
            }
        }
    }

}
