package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.transaction.Transaction;
import com.blockcypher.model.transaction.intermediary.IntermediaryTransaction;
import com.blockcypher.utils.sign.SignUtils;
import com.blockcypher.utils.gson.GsonFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;


/**
 * Test for TransactionService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class TransactionServiceTest extends AbstractServiceTest {

    private static final Logger logger = Logger.getLogger(TransactionServiceTest.class);

    @Test
    public void testGetTransaction() throws BlockCypherException, IOException {
        Transaction transaction = blockCypherContext.getTransactionService()
                .getTransaction("09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35");
        assertEquals(new BigDecimal(0), transaction.getFees());
        assertEquals(new Long(271609), transaction.getBlockHeight());
    }

    @Test(expected = BlockCypherException.class)
    public void testNewTransactionFromJsonStringWithError() throws BlockCypherException {
        String transactionSkeleton = "{\"inputs\": [{\"addresses\": [\"1DEP8i3QJCsomS4BSMY2RpU1upv62aGvhD\"]}],\"outputs\": [{\"addresses\": [\"1FGAsJFNgWvFz2tWQAnRq8S6fVX9Zmuxje\"], \"value\": 500000}]}";
        IntermediaryTransaction transaction = blockCypherContext.getTransactionService()
                .newTransaction(transactionSkeleton);
    }

    @Test(expected = BlockCypherException.class)
    public void testNewTransactionFromInputsWithError() throws BlockCypherException {
        try {
            IntermediaryTransaction transaction = blockCypherContext.getTransactionService()
                    .newTransaction(new ArrayList<String>(Arrays.asList("1DEP8i3QJCsomS4BSMY2RpU1upv62aGvhD")),
                            new ArrayList<String>(Arrays.asList("1FGAsJFNgWvFz2tWQAnRq8S6fVX9Zmuxje")),
                            500000
                    );
        } catch (BlockCypherException ex) {
            logger.error("Exception while NewTransactionFromInputsWithError", ex);
            assertEquals(ex.getMessage(), "Bad Request");
            assertEquals(ex.getStatus(), 400);
            throw ex;
        }
    }

    @Test
    public void testNewTransactionFromInputs() {
        try {
            IntermediaryTransaction unsignedTransaction = blockCypherContext.getTransactionService()
                    .newTransaction(new ArrayList<String>(Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz")),
                            new ArrayList<String>(Arrays.asList("n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB")),
                            500000
                    );
            SignUtils.signWithBase58KeyWithPubKey(unsignedTransaction, MY_PRIVATE_KEY);

            Transaction transaction = blockCypherContext.getTransactionService().sendTransaction(unsignedTransaction);

            logger.info("Sent transaction: " + GsonFactory.getGson().toJson(transaction));
        } catch (BlockCypherException e) {
            logger.error("Exception while creating new Transaction", e);
            fail("Fail CreateTransaction");
        }
    }

}
