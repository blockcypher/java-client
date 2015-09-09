package com.blockcypher.utils.rest;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.rest.jersey.JerseyRestUtils;
import org.apache.log4j.Logger;

import java.text.MessageFormat;

/**
 * All HTTP REST operations (POST, GET, DELETE) go through this class
 * It is using Jersey
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class RestUtils {

    private static final Logger logger = Logger.getLogger(RestUtils.class);

    public static <T> T post(String resourceUrl, String inputString, String id, Class<T> clazz) throws BlockCypherException {
        return JerseyRestUtils.post(resourceUrl, inputString, id, clazz);
    }

    public static void delete(String resourceUrl, String id, Class clazz) throws BlockCypherException {
        JerseyRestUtils.delete(resourceUrl, id, clazz);
    }

    public static <T> T get(String resourceUrl, String id, Class<T> clazz) throws BlockCypherException {
        return JerseyRestUtils.get(resourceUrl, id, clazz);
    }

    public static String formatUrl(String resourceUrl, EndpointConfig endpointConfig, String id) {
        return MessageFormat.format(resourceUrl,
                endpointConfig.getVersion(),
                endpointConfig.getCurrency(),
                endpointConfig.getNetwork(),
                id) + "?token=" + endpointConfig.getToken();
    }

}
