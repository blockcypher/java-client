package com.blockcypher.service;

import com.blockcypher.context.BlockCypherContext;
import com.blockcypher.utils.BlockCypherRestfulConstants;
import org.junit.BeforeClass;

/**
 * Abstract Test Service Class
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class AbstractServiceTest {

    // In order to signWithBase58Key.. Of course do not store this is plain your java code... This is for test only!
    protected static final String MY_PRIVATE_KEY = "cQvo4s4QLbZ4V3r9yxuoooZbF98XsGniq5o69qPXk7pmgrLbUqwm";

    protected static BlockCypherContext blockCypherContext;

    @BeforeClass
    public static void proxySettings() {
        // proxy only works for @GET, webhooks will NOT work!
        setProxySettings();
        createBlockCypherContext();
    }

    protected static void createBlockCypherContext() {
        blockCypherContext = new BlockCypherContext(BlockCypherRestfulConstants.VERSION_V1,
                BlockCypherRestfulConstants.CURRENCY_BTC,
                BlockCypherRestfulConstants.NETWORK_BTC_TESTNET);
                //BlockCypherRestfulConstants.NETWORK_MAIN);
    }

    private static void setProxySettings() {
        System.setProperty("http.proxyHost", "myproxy.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("http.nonProxyHosts", "localhost|127.*|192.*");
        System.setProperty("https.proxyHost", "myproxy.com");
        System.setProperty("https.proxyPort", "80");
        System.setProperty("https.nonProxyHosts", "localhost|127.*|192.*");

        /*Authenticator a = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return(new PasswordAuthentication(login, pwd.toCharArray()));
            }
        };
        // Sets the default Authenticator
        Authenticator.setDefault(a);
        client.get();
        logger.info("Using proxy " + proxyHost + " as " + login);*/
    }

}
