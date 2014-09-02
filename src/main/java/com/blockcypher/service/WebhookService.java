package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.webhook.Webhook;
import com.blockcypher.utils.config.EndpointConfig;
import com.blockcypher.utils.rest.RestUtils;
import com.google.gson.JsonObject;

/**
 * Service which provides create/read access to a Webhook
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class WebhookService extends AbstractService {

    private static final String ABSOLUTE_PATH = "/{0}/{1}/{2}/hooks";

    private WebhookService(EndpointConfig endpointConfig) {
        super(endpointConfig);
    }

    @Override
    protected String getAbsolutePath() {
        return ABSOLUTE_PATH;
    }

    /**
     * Create a Webhook
     * @param registeredUrl url of the Webhook (ie http://gghhii.ngrok.com:80) This url will receiveall the events from BlockCypher
     * @param filter Events filters
     * @param token token
     * @return Webhook created
     * @throws BlockCypherException
     */
    public Webhook createWebHook(String registeredUrl, String filter, String token) throws BlockCypherException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("url", registeredUrl);
        jsonObject.addProperty("filter", filter);
        jsonObject.addProperty("token", token);
        return RestUtils.post(RestUtils.formatUrl(resourceUrl, endpointConfig, null), jsonObject.toString(), null, Webhook.class);
    }

    /**
     * Get an existing Webhook
     * @param id
     * @return
     * @throws BlockCypherException
     */
    public Webhook getWebHook(String id) throws BlockCypherException {
        return RestUtils.get(RestUtils.formatUrl(resourceUrl + "/{3}", endpointConfig, id), id, Webhook.class);
    }

    /**
     * Delete an existing Webhook
     * @param id
     * @throws BlockCypherException
     */
    public void delete(String id) throws BlockCypherException {
        RestUtils.delete(RestUtils.formatUrl(resourceUrl + "/{3}", endpointConfig, id), id, Webhook.class);
    }

}
