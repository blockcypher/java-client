package com.blockcypher.service;

import com.blockcypher.utils.config.EndpointConfig;

/**
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public abstract class AbstractService {

    protected EndpointConfig endpointConfig;

    protected String resourceUrl;

    AbstractService(EndpointConfig endpointConfig) {
        this.endpointConfig = endpointConfig;
        this.resourceUrl = endpointConfig.getEndpoint() + getAbsolutePath();
    }

    protected abstract String getAbsolutePath();

}
