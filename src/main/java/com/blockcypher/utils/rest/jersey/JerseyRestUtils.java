package com.blockcypher.utils.rest.jersey;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.error.BlockCypherError;
import com.blockcypher.utils.gson.GsonFactory;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

/**
 * All HTTP REST operations (POST, GET, DELETE) go through this class
 * It is using Jersey
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class JerseyRestUtils {

    private static final Logger logger = Logger.getLogger(JerseyRestUtils.class);

    private static <T> T post(String inputString, WebTarget clientResource, Class<T> clazz) throws BlockCypherException {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Posting to {0}: {1}", clientResource.getUri(), inputString));
        }
        Response response = clientResource.request(MediaType.APPLICATION_JSON).post(Entity.entity(inputString, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201) {
            throw getBlockCypherException(clientResource, response);
        } else {
            T returnedObject = GsonFactory.getGson().fromJson(response.readEntity(String.class), clazz);
            return returnedObject;
        }
    }

    private static void delete(WebTarget clientResource, Class clazz) throws BlockCypherException {
        Response response = clientResource.request(MediaType.APPLICATION_JSON).delete();
        if (response.getStatus() != 204) {
            throw getBlockCypherException(clientResource, response);
        }
    }

    public static <T> T post(String resourceUrl, String inputString, String id, Class<T> clazz) throws BlockCypherException {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(resourceUrl);
        return post(inputString, webTarget, clazz);
    }

    public static void delete(String resourceUrl, String id, Class clazz) throws BlockCypherException {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(resourceUrl);
        delete(webTarget, clazz);
    }

    public static <T> T get(String resourceUrl, String id, Class<T> clazz) throws BlockCypherException {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(resourceUrl);
        return get(webTarget, clazz);
    }

    private static <T> T get(WebTarget webTarget, Class<T> clazz) throws BlockCypherException {
        Response response = null;
        try {
            response = webTarget.request(MediaType.APPLICATION_JSON).get();
            T returnedObject = GsonFactory.getGson().fromJson(response.readEntity(String.class), clazz);
            if (response.getStatus() != 200) {
                throw getBlockCypherException(webTarget, response);
            } else {
                return returnedObject;
            }
        } catch (IllegalStateException ex) {
            throw getBlockCypherException(webTarget, response);
        }
    }

    private static BlockCypherException getBlockCypherException(WebTarget resource, Response ex) {
        try {
            String response = ex.readEntity(String.class);
            BlockCypherError blockCypherError = GsonFactory.getGson().fromJson(response, BlockCypherError.class);
            if (blockCypherError == null || blockCypherError.getErrors().isEmpty()) {
                // sometimes it's not embedded into errors[] array.. try without..
                com.blockcypher.model.error.Error error = GsonFactory.getGson().fromJson(response, com.blockcypher.model.error.Error.class);
                blockCypherError = new BlockCypherError();
                blockCypherError.addError(error);
            }
            return new BlockCypherException(ex.getStatusInfo().getReasonPhrase(), ex.getStatus(), blockCypherError);
        } catch (JsonSyntaxException jsonException) {
            return new BlockCypherException(ex.getStatusInfo().getReasonPhrase() + " - " + ex, ex.getStatus(), null);
        } catch (IllegalStateException illegalStateException) {
            if (ex != null && ex.getStatusInfo() != null) {
                return new BlockCypherException(ex.getStatusInfo().getReasonPhrase() + " - " + ex, ex.getStatus(), null);
            } else {
                return new BlockCypherException(illegalStateException);
            }
        }
    }

}
