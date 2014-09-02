package com.blockcypher.message;

import com.blockcypher.utils.gson.GsonFactory;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Reader for Webhook in order to read the input stream and return Object
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class WebhookJsonReader<T> implements MessageBodyReader<T> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations,
                      MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                      InputStream entityStream) throws IOException, WebApplicationException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(entityStream, writer, "UTF-8");
        String json = writer.toString();
        if (String.class == genericType)
            return type.cast(json);
        return GsonFactory.getGson().fromJson(json, genericType);
    }

}