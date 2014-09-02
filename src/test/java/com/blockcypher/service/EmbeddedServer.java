package com.blockcypher.service;

import com.blockcypher.message.WebhookJsonReader;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

/**
 * A small embedded server to test Websockets. It will create an HttpServer using Jersey
 * It will automatically start ngrok and use this as a host to be used when the websocket is created.
 * It will automatically register com.blockcypher.model.** class so that @Post from the @Path of the associated resource
 * is called.
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class EmbeddedServer {

    public static final int SERVER_PORT = 80;
    private static final Logger logger = Logger.getLogger(WebhookServiceTest.class);
    private static final String NGROK_EXEC = "ngrok";
    private static Map classInterceptor;
    private String host;
    private Process p;

    private static HttpServer createHttpServer() throws IOException {
        ResourceConfig config = new ResourceConfig();
        config.packages(true, "com.blockcypher.model");
        config.register(DynamicBinding.class);
        config.register(WebhookJsonReader.class);
        return JdkHttpServerFactory.createHttpServer(getURI(), config);
    }

    private static URI getURI() {
        return UriBuilder.fromUri("http://" + "0.0.0.0" + "/").port(SERVER_PORT).build();
    }

/*    private static String getHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            logger.error("Error while creating http server", e);
        }
        return hostName;
    }*/

    public void start(Map<Class, ReaderTestInterceptor> classInterceptor) {
        EmbeddedServer.classInterceptor = classInterceptor;
        logger.info("Starting Embedded Jersey HTTPServer...\n");
        HttpServer httpServer = null;
        try {
            httpServer = createHttpServer();
        } catch (IOException e) {
            logger.error("Error while creating http server", e);
        }
        logger.info(String.format("\nJersey Application Server started with WADL available at " + "%sapplication.wadl\n", getURI()));
        logger.info("Started Embedded Jersey HTTPServer Successfully !!!");

        logger.info("Starting ngrok");
        ProcessBuilder pb = new ProcessBuilder(NGROK_EXEC, "-log=stdout", String.valueOf(SERVER_PORT));
        try {
            Process p = pb.start();
            inheritIO(p.getInputStream());
            logger.info("ngrok started");
        } catch (IOException e) {
            logger.error("Error while starting ngrok", e);
        }
    }

    public void destroy() {
        p.destroy();
    }

    public String getHost() {
        return host;
    }

    private void inheritIO(final InputStream src) {
        new Thread(new Runnable() {
            public void run() {
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.contains("[client] Tunnel established")) {
                        logger.info(line);
                        host = line.substring(line.lastIndexOf("/") + 1);
                        logger.info("ngrok started on host: " + host);
                    } else if (line.contains("[INFO]")) {
                        logger.info(line);
                    } else if (line.contains("[DEBG]")) {
                        //logger.debug(line);
                    } else if (line.contains("[EROR]")) {
                        logger.error(line);
                    }
                }
            }
        }).start();
    }

    @Provider
    public static class DynamicBinding implements DynamicFeature {

        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            if (classInterceptor.containsKey(resourceInfo.getResourceClass()) && resourceInfo.getResourceMethod()
                    .getName().contains("post")) {
                context.register(classInterceptor.get(resourceInfo.getResourceClass()));
            }
        }

    }

}
