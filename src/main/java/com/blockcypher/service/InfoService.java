package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.info.Info;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.rest.RestUtils;

import java.io.IOException;

/**
 * Service which returns info on the network/currency you are querying
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class InfoService extends AbstractService {

    private static final String ABSOLUTE_PATH = "/{0}/{1}/{2}";

    public InfoService(EndpointConfig endpointConfig) {
        super(endpointConfig);
    }

    @Override
    protected String getAbsolutePath() {
        return ABSOLUTE_PATH;
    }

    /**
     * Return info on the network/currency you are querying
     * @return
     * @throws IOException
     */
    public Info getInfo() throws BlockCypherException {
        return RestUtils.get(RestUtils.formatUrl(resourceUrl, endpointConfig, null), null, Info.class);
    }

}
