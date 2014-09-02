package com.blockcypher.context;

import com.blockcypher.service.*;
import com.blockcypher.utils.config.EndpointConfig;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

/**
 * BlockCypher Context holds the following services:
 * - addressService
 * - blockChainService
 * - transactionService
 * - webhookService
 * - infoService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class BlockCypherContext {

    private static final Logger logger = Logger.getLogger(BlockCypherContext.class);

    private AddressService addressService;
    private BlockChainService blockChainService;
    private TransactionService transactionService;
    private WebhookService webhookService;
    private InfoService infoService;
    private EndpointConfig endpointConfig;

    /**
     * Constructor. If you do not provide a version currency and network it will be read from blockcypher.endpoint.properties in classpath
     */
    public BlockCypherContext() {
        endpointConfig = new EndpointConfig();
        if (!endpointConfig.isValid()) {
            throw new RuntimeException("Creating BlockCypherContext() but you did not provide:" + EndpointConfig.PROPERTY_FILE);
        }
        logger.info(MessageFormat.format("Creating BlockCypherContext with version {0}, " +
                        "currency {1}, network {2} on endpoint {3}",
                endpointConfig.getVersion(), endpointConfig.getCurrency(), endpointConfig.getNetwork(), endpointConfig.getEndpoint()));
        createServices(endpointConfig);
    }

    /**
     * Constructor.
     * @param version API version, ie: v1
     * @param currency currency, ie: btc (bitcoin), ltc (lightcoin), uro (urocoin)
     * @param network network, ie: main, test, test3
     */
    public BlockCypherContext(String version, String currency, String network) {
        endpointConfig = new EndpointConfig(version, currency, network);
        logger.info(MessageFormat.format("Creating BlockCypherContext with version {0}, " +
                        "currency {1}, network {2} on endpoint {3}",
                version, currency, network, endpointConfig.getEndpoint()));
        createServices(endpointConfig);
    }

    private void createServices(EndpointConfig endpointConfig) {
        try {
            this.addressService = this.getPrivateConstructor(AddressService.class).newInstance(endpointConfig);
            this.blockChainService = this.getPrivateConstructor(BlockChainService.class).newInstance(endpointConfig);
            this.transactionService = this.getPrivateConstructor(TransactionService.class).newInstance(endpointConfig);
            this.webhookService = this.getPrivateConstructor(WebhookService.class).newInstance(endpointConfig);
            this.infoService = this.getPrivateConstructor(InfoService.class).newInstance(endpointConfig);
            logger.info("Services created");
        } catch (Exception e) {
            logger.error("Error while creating services", e);
            throw new RuntimeException(e);
        }
    }

    private <T> Constructor<T> getPrivateConstructor(final Class<T> clazz) throws Exception {
        //Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class, String.class);
        Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(EndpointConfig.class);
        declaredConstructor.setAccessible(true);
        return declaredConstructor;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public BlockChainService getBlockChainService() {
        return blockChainService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public WebhookService getWebhookService() {
        return webhookService;
    }

    public InfoService getInfoService() {
        return infoService;
    }

    public EndpointConfig getEndpointConfig() {
        return endpointConfig;
    }

}
