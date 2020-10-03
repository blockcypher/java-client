package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.transaction.Transaction;
import com.blockcypher.model.transaction.TransactionConstants;
import com.blockcypher.model.transaction.input.Input;
import com.blockcypher.model.transaction.intermediary.IntermediaryTransaction;
import com.blockcypher.model.transaction.output.Output;
import com.blockcypher.model.nulldata.NullData;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.gson.GsonFactory;
import com.blockcypher.utils.rest.RestUtils;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 * Service which provides create/read access to Transaction
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class TransactionService extends AbstractService {

    // ie https://api.blockcypher.com/v1/btc/test3/txs/09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35
    private static final String ABSOLUTE_PATH = "/{0}/{1}/{2}/txs/{3}";

    private TransactionService(EndpointConfig endpointConfig) {
        super(endpointConfig);
    }

    @Override
    protected String getAbsolutePath() {
        return ABSOLUTE_PATH;
    }

    /**
     * Get Transaction
     * @param hash Hash of the transaction
     * @return Transaction
     */
    public Transaction getTransaction(String hash) throws BlockCypherException {
        return RestUtils.get(RestUtils.formatUrl(resourceUrl, endpointConfig, hash), hash, Transaction.class);
    }

    /**
     * Create a new Transaction from skeleton
     * @param transactionSkeleton input/output+value simple skeleton
     * @return Partially built transaction with the data to sign
     * @throws BlockCypherException In case there is some error, ie: <code>Address xxx with balance 0 does not have enough funds to transfer 510000.</code>
     */
    public IntermediaryTransaction newTransaction(String transactionSkeleton) throws BlockCypherException {
        return postSkeletonTransaction(transactionSkeleton, "new");
    }

    private IntermediaryTransaction postSkeletonTransaction(String lightTransaction, String id) throws BlockCypherException {
        return RestUtils.post(RestUtils.formatUrl(resourceUrl, endpointConfig, id), lightTransaction, id, IntermediaryTransaction.class);
    }

    private Transaction postTransaction(String signedTransaction, String id) throws BlockCypherException {
        return (RestUtils.post(RestUtils.formatUrl(resourceUrl, endpointConfig, id), signedTransaction, id, IntermediaryTransaction.class)).getTx();
    }

    private NullData postNullData(String nullData, String id) throws BlockCypherException {
        return RestUtils.post(RestUtils.formatUrl(resourceUrl, endpointConfig, id), nullData, id, NullData.class);
    }

    /**
     * Create an Intermediary Transaction from skeleton. You will then need to sign this transaction with your private key
     *
     * @param inputAddresses  input addresses
     * @param outputAddresses output addresses
     * @param satoshis        values
     * @return Partially built transaction with the data to sign
     * @throws BlockCypherException In case there is some error, ie: <code>Address xxx with balance 0 does not have enough funds to transfer 510000.</code>
     */
    public IntermediaryTransaction newTransaction(List<String> inputAddresses, List<String> outputAddresses,
                                                  List<Long> satoshis) throws BlockCypherException {
        Transaction transaction = new Transaction();

        for (String address : inputAddresses) {
            Input input = new Input();
            input.addAddress(address);
            transaction.addInput(input);
        }

        Iterator<Long> satoshiIterator = satoshis.iterator();

        for (String address : outputAddresses) {
            Output output = new Output();
            output.addAddress(address);
            output.setValue(new BigDecimal(satoshiIterator.next()));
            transaction.addOutput(output);
        }
        String transactionJson = GsonFactory.getGson().toJson(transaction);
        return postSkeletonTransaction(transactionJson, "new");
    }


    /**
     * Create an Intermediary Transaction from skeleton. You will then need to sign this transaction with your private key
     *
     * @param inputAddresses  input addresses
     * @param outputAddresses output addresses
     * @param satoshis        value
     * @return Partially built transaction with the data to sign
     * @throws BlockCypherException In case there is some error, ie: <code>Address xxx with balance 0 does not have enough funds to transfer 510000.</code>
     */
    @Deprecated
    public IntermediaryTransaction newTransaction(List<String> inputAddresses, List<String> outputAddresses,
                                                  long satoshis) throws BlockCypherException {
        Transaction transaction = new Transaction();
        for (String address : inputAddresses) {
            Input input = new Input();
            input.addAddress(address);
            transaction.addInput(input);
        }
        for (String address : outputAddresses) {
            Output output = new Output();
            output.addAddress(address);
            output.setValue(new BigDecimal(satoshis));
            transaction.addOutput(output);
        }
        String transactionJson = GsonFactory.getGson().toJson(transaction);
        return postSkeletonTransaction(transactionJson, "new");
    }

    /**
     * Create a Funding multisig-2-of-3 Transaction from skeleton. You will then need to sign the returned transaction
     * @param inputAddresses  input addresses
     * @param outputAddresses output addresses
     * @param satoshis        value
     * @return Partially built transaction with the data to sign
     * @throws BlockCypherException In case there is some error, ie: <code>Address xxx with balance 0 does not have enough funds to transfer 510000.</code>
     */
    public IntermediaryTransaction newFundingTransaction(List<String> inputAddresses, List<String> outputAddresses,
                                                         long satoshis, boolean scriptOnInput) throws BlockCypherException {
        Transaction transaction = new Transaction();
        Input input = new Input();
        for (String address : inputAddresses) {
            input.addAddress(address);
            if (scriptOnInput) {
                input.setScriptType(TransactionConstants.SCRIPT_TYPE_MULTI_SIG_2_OF_3);
            }
        }
        transaction.addInput(input);
        Output output = new Output();
        for (String address : outputAddresses) {
            output.addAddress(address);
            if (!scriptOnInput) {
                output.setScriptType(TransactionConstants.SCRIPT_TYPE_MULTI_SIG_2_OF_3);
            }
            output.setValue(new BigDecimal(satoshis));
        }
        transaction.addOutput(output);
        String transactionJson = GsonFactory.getGson().toJson(transaction);
        return postSkeletonTransaction(transactionJson, "new");
    }

    /**
     * Send a signed transaction. This is the final step once you have signed an intermediary transaction.
     * @param signedTransaction
     * @return
     * @throws BlockCypherException
     */
    public Transaction sendTransaction(IntermediaryTransaction signedTransaction) throws BlockCypherException {
        String transactionJson = GsonFactory.getGson().toJson(signedTransaction);
        return postTransaction(transactionJson, "send");
    }

    /**
     * Send a NullData embed via the transaction API.
     * @param nullData
     * @return Embeded Data
     * @throws BlockCypherException
     */
    public NullData sendNullData(NullData nullData) throws BlockCypherException {
        String nullDataJson = GsonFactory.getGson().toJson(nullData);
        return postNullData(nullDataJson, "data");
    }

}
