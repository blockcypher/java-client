package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.blockchain.BlockChain;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.rest.RestUtils;

import java.io.IOException;

/**
 * Service which provides read access on BlockChain details
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class BlockChainService extends AbstractService {

    private static final String ABSOLUTE_PATH = "/{0}/{1}/{2}/blocks/{3}";

    private BlockChainService(EndpointConfig endpointConfig) {
        super(endpointConfig);
    }

    @Override
    protected String getAbsolutePath() {
        return ABSOLUTE_PATH;
    }

    /**
     * Get Block Chain infos
     * @param hash Block Chain Hash
     * @return BlockChain
     * @throws IOException
     */
    public BlockChain getBlockChain(String hash) throws BlockCypherException {
        return RestUtils.get(RestUtils.formatUrl(resourceUrl, endpointConfig, hash), hash, BlockChain.class);
    }

}
