package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.address.Address;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.rest.RestUtils;

import java.io.IOException;

/**
 * Address service to give create/read access to address details
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class AddressService extends AbstractService {

    private static final String ABSOLUTE_PATH = "/{0}/{1}/{2}/addrs";

    private AddressService(EndpointConfig endpointConfig) {
        super(endpointConfig);
    }

    @Override
    protected String getAbsolutePath() {
        return ABSOLUTE_PATH;
    }

    /**
     * Get Address details
     * @param address
     * @return
     * @throws IOException
     */
    public Address getAddress(String address) throws BlockCypherException {
        return RestUtils.get(RestUtils.formatUrl(resourceUrl + "/{3}", endpointConfig, address), address, Address.class);
    }

    /**
     * Create an address
     * @return Created Address
     * @throws BlockCypherException
     */
    public Address createAddress() throws BlockCypherException {
        return RestUtils.post(RestUtils.formatUrl(resourceUrl, endpointConfig, null), null, null, Address.class);
    }

}
