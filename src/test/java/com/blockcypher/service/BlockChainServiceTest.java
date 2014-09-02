package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.blockchain.BlockChain;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Test for BlockChainService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class BlockChainServiceTest extends AbstractServiceTest {

    private static final Logger logger = Logger.getLogger(BlockChainServiceTest.class);

    @Test
    public void testGetBlockChain() throws BlockCypherException {
        BlockChain blockChain = blockCypherContext.getBlockChainService()
                .getBlockChain("00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c");
        assertEquals("d0fb877a660bf6db7ecd4a0d510203bf2382f4c085936c95dbf65a2073ae1211", blockChain.getTxids().get(0));
        assertEquals(new Long(320001), blockChain.getFees());
        assertEquals("00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c", blockChain.getHash());
    }

}
