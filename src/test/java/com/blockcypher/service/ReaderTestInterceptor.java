package com.blockcypher.service;

import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interceptor register against the EmbeddedServer to intercept WebHook posts
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class ReaderTestInterceptor implements ReaderInterceptor {

    private static final Logger logger = Logger.getLogger(ReaderTestInterceptor.class);

    private List receivedEvents = new ArrayList();

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
        logger.info("Coolio we received something!");
        Object returnedObject = readerInterceptorContext.proceed();
        logger.info("Received:" + returnedObject);
        receivedEvents.add(returnedObject);
        return returnedObject;
    }

    public List getReceivedEvents() {
        return receivedEvents;
    }

}