package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.address.Address;
import com.blockcypher.model.transaction.summary.TransactionSummary;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.TransformerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * Test for AddressService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class AddressServiceTest extends AbstractServiceTest {

    private static final Logger logger = Logger.getLogger(AddressServiceTest.class);

    @Test
    public void testCreateAddress() throws BlockCypherException {
        Address address = blockCypherContext.getAddressService().createAddress();
        logger.info(MessageFormat.format("Adress {0} created", address.getAddress()));
    }

    @Test
    public void testGetAddress() throws BlockCypherException, IOException {
        String addressM = "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz";
        Address address = blockCypherContext.getAddressService().getAddress(addressM);

        logger.info(MessageFormat.format("Adress {0} has the following transactions: ", addressM));
        logger.info(StringUtils.join(CollectionUtils.collect(address.getTxrefs(),
                TransformerUtils.invokerTransformer("getTxHash")), "\n"));

        TransactionSummary transactionSummary = (TransactionSummary) CollectionUtils.find(address.getTxrefs(),
                new Predicate() {
                    public boolean evaluate(Object object) {
                        return ((TransactionSummary) object).getTxHash()
                                .equals("f8c652d90b1aa2e510ab0963525836e1e1bcc1f93f7beca65a8902c54ed77d5e");
                    }
                });
        assertNotNull(transactionSummary);
        assertEquals(new Long(271558), transactionSummary.getBlockHeight());
        assertEquals(new BigDecimal(100270000), transactionSummary.getValue());
    }

}
