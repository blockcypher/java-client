package com.blockcypher.utils.rest.restlet;

/*import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.error.BlockCypherError;
import com.blockcypher.utils.gson.GsonFactory;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;*/

/**
 * All HTTP REST operations (POST, GET, DELETE) go through this class
 * It is using Restlet
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class RestletRestUtils {

/*
    private static final Logger logger = Logger.getLogger(RestletRestUtils.class);

    private static <T> T post(String inputString, ClientResource clientResource, Class<T> clazz) throws IOException, BlockCypherException {
        try {
            Representation answer = clientResource.post(new StringRepresentation(inputString, MediaType.APPLICATION_JSON));
            T returnedObject = GsonFactory.getGson().fromJson(answer.getText(), clazz);
            return returnedObject;
        } catch (ResourceException ex) {
            throw getBlockCypherException(clientResource, ex);
        }
    }

    private static <T> T delete(ClientResource clientResource, Class<T> clazz) throws IOException, BlockCypherException {
        try {
            Representation answer = clientResource.delete(MediaType.APPLICATION_JSON);
            T returnedObject = GsonFactory.getGson().fromJson(answer.getText(), clazz);
            return returnedObject;
        } catch (ResourceException ex) {
            throw getBlockCypherException(clientResource, ex);
        }
    }

    public static <T> T post(String resourceUrl, String inputString, String id, Class<T> clazz) throws IOException, BlockCypherException {
        ClientResource resource = new ClientResource(resourceUrl);
        return post(inputString, resource, clazz);
    }

    public static <T> T delete(String resourceUrl, String id, Class<T> clazz) throws IOException, BlockCypherException {
        ClientResource resource = new ClientResource(resourceUrl);
        return delete(resource, clazz);
    }

    public static <T> T get(String resourceUrl, String id, Class<T> clazz) throws IOException, BlockCypherException {
        ClientResource resource = new ClientResource(resourceUrl);
        return get(resource, clazz);
    }

    private static <T> T get(ClientResource clientResource, Class<T> clazz) throws IOException, BlockCypherException {
        try {
            Representation answer = clientResource.get(MediaType.APPLICATION_JSON);
            T returnedObject = GsonFactory.getGson().fromJson(answer.getText(), clazz);
            return returnedObject;
        } catch (ResourceException ex) {
            throw getBlockCypherException(clientResource, ex);
        }
    }

    private static BlockCypherException getBlockCypherException(ClientResource resource, ResourceException ex) throws IOException {
        String response = resource.getResponseEntity().getText();
        try {
            BlockCypherError blockCypherError = GsonFactory.getGson().fromJson(response, BlockCypherError.class);
            if (blockCypherError == null || blockCypherError.getErrors().isEmpty()) {
                // sometimes it's not embedded into errors[] array.. try without..
                com.blockcypher.model.error.Error error = GsonFactory.getGson().fromJson(response, com.blockcypher.model.error.Error.class);
                blockCypherError = new BlockCypherError();
                blockCypherError.addError(error);
            }
            return new BlockCypherException(ex.getMessage(), ex.getStatus().getCode(), blockCypherError);
        } catch (JsonSyntaxException jsonException) {
            return new BlockCypherException(ex.getMessage() + " - " + response, ex.getStatus().getCode(), null);
        }
    }
*/

}
