package com.blockcypher.service;

import com.blockcypher.utils.annotation.AnnotationUtils;
import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.address.Address;
import com.blockcypher.model.blockchain.BlockChain;
import com.blockcypher.model.transaction.Transaction;
import com.blockcypher.model.webhook.Webhook;
import com.blockcypher.model.webhook.WebhookConstants;
import com.blockcypher.utils.gson.GsonFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;
import static junit.framework.TestCase.assertFalse;


/**
 * Test for WebhookService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class WebhookServiceTest extends AbstractServiceTest {

    private static final String ROOT_TRANSACTION = AnnotationUtils.getPath(Transaction.class);
    private static final String ROOT_BLOCKCHAIN = AnnotationUtils.getPath(BlockChain.class);
    private static final String ROOT_ADDRESS = AnnotationUtils.getPath(Address.class);

    private static final Logger logger = Logger.getLogger(WebhookServiceTest.class);
    private Webhook webhook;
    private EmbeddedServer embeddedServer;

    @After
    public void tearDownTest() throws IOException, BlockCypherException {
        if (webhook != null) {
            blockCypherContext.getWebhookService().delete(webhook.getId());
            logger.info("Deleted Webhook: " + webhook.getId());
        }
        if (embeddedServer != null) {
            embeddedServer.destroy();
        }
    }

    @Test(timeout = 120000)
    public void testWebHookOnTransactionWithEmbeddedServer() throws Exception {
        embeddedServer = new EmbeddedServer();

        Map<Class, ReaderTestInterceptor> classInterceptor = new HashMap<Class, ReaderTestInterceptor>();
        classInterceptor.put(Transaction.class, new ReaderTestInterceptor());
        classInterceptor.put(BlockChain.class, new ReaderTestInterceptor());
        classInterceptor.put(Address.class, new ReaderTestInterceptor());
        embeddedServer.start(classInterceptor);

        while (embeddedServer.getHost() == null) {
            Thread.sleep(1000);
        }

        logger.info("Webserver init with Host: " + embeddedServer.getHost());
        // ie: "3cc375e5.ngrok.com";
        String myIp = embeddedServer.getHost();
        // We will run on this port our mini server
        int myPort = embeddedServer.getPort();

        logger.info(format("Server started on: http://{0}:{1}", myIp, String.valueOf(myPort)));
        webhook = blockCypherContext.getWebhookService().createWebHook(format("http://{0}:{1}" + ROOT_TRANSACTION,
                        myIp, String.valueOf(myPort)),
                "event=" + WebhookConstants.FILTER_NEW_POOL_TX,
                null);
        logger.info("WebHook: " + GsonFactory.getGson().toJson(webhook));
        while (classInterceptor.get(Transaction.class).getReceivedEvents().isEmpty()) {
            Thread.sleep(1000);
        }
        assertFalse(StringUtils.isBlank(((Transaction) classInterceptor.get(Transaction.class).getReceivedEvents().get(0)).getHash()));
    }

    @Test
    public void testDeleteWebHook() throws Exception {
        //String webHookId = "e07c3633-c381-4605-a2d0-4aff96da46a4";
        //blockCypherContext.getWebhookService().delete(webHookId);
    }

    @Test
    public void testCreateGetAndDeleteWebHook() throws Exception {
        // Here we put a fake address where no server is started just for example
        Webhook createdWebhook = blockCypherContext.getWebhookService().createWebHook(format("http://{0}:{1}" + ROOT_TRANSACTION,
                        "123.456.78.90", "80"),
                "event=" + WebhookConstants.FILTER_NEW_POOL_TX,
                null);
        logger.info("WebHook: " + GsonFactory.getGson().toJson(createdWebhook));
        // Wait a bit
        Thread.sleep(2000);
        Webhook webhook1 = blockCypherContext.getWebhookService().getWebHook(createdWebhook.getId());
        blockCypherContext.getWebhookService().delete(webhook1.getId());
    }

}
