package com.blockcypher.utils.config;

import com.blockcypher.utils.BlockCypherRestfulConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * EndpointConfig holds the endpoint config: version, currency, network
 * If empty constructor is provided attempt to read it from blockcypher.endpoint.properties on classpath
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class EndpointConfig {

    public static final String PROPERTY_FILE = "blockcypher.endpoint.properties";
    private static final Logger logger = Logger.getLogger(EndpointConfig.class);
    private static String propertyFileVersion;
    private static String propertyFileCurrency;
    private static String propertyFileNetwork;
    private static String propertyFileEndpoint;
    private String version = null;
    private String currency = null;
    private String network = null;
    private String endpoint = null;

    public EndpointConfig(String version, String currency, String network) {
        this(version, currency, network, BlockCypherRestfulConstants.BLOCK_CYPHER_ENDPOINT);
    }

    public EndpointConfig(String version, String currency, String network, String endpoint) {
        this.version = version;
        this.currency = currency;
        this.network = network;
        this.endpoint = endpoint;
    }

    public EndpointConfig() {
        this.version = propertyFileVersion;
        this.currency = propertyFileCurrency;
        this.network = propertyFileNetwork;
        this.endpoint = propertyFileEndpoint;
    }

    static {
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(PROPERTY_FILE);
            if (stream != null) {
                prop.load(stream);
                propertyFileVersion = prop.getProperty("version");
                propertyFileCurrency = prop.getProperty("currency");
                propertyFileNetwork = prop.getProperty("network");
                propertyFileEndpoint = prop.getProperty("endpoint");
            }
        } catch (IOException e) {
            logger.info("No Init Property file found: " + PROPERTY_FILE, e);
        }
    }

    public String getVersion() {
        return version;
    }

    public String getCurrency() {
        return currency;
    }

    public String getNetwork() {
        return network;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public boolean isValid() {
        return !(StringUtils.isBlank(version) ||
                StringUtils.isBlank(currency) ||
                StringUtils.isBlank(network) ||
                StringUtils.isBlank(endpoint));
    }

}
