package com.blockcypher.model.address;

import com.blockcypher.model.transaction.summary.TransactionSummary;
import com.blockcypher.utils.gson.GsonFactory;
import com.google.gson.annotations.SerializedName;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representation of an Address, ie:
 * {
 * "address": "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz",
 * "balance": 921404538,
 * "unconfirmed_balance": 0,
 * "final_balance": 921404538,
 * "n_tx": 6,
 * "txrefs": [
 * {
 * "tx_hash": "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35",
 * "block_height": 271609,
 * "tx_input_n": -1,
 * "tx_output_n": 0,
 * "value": 100000000,
 * "spent": false,
 * "confirmations": 1867,
 * "confirmed": "2014-08-03T15:52:11Z"
 * },
 * {
 * "tx_hash": "aeda06e4e08b3063b8d85a1f1a576635f6aff9f68c8c53c78dc1d47f5b42b9ed",
 * "block_height": 271593,
 * "tx_input_n": -1,
 * "tx_output_n": 0,
 * "value": 410984538,
 * "spent": false,
 * "confirmations": 1883,
 * "confirmed": "2014-08-03T13:28:41Z"
 * },
 * {
 * "tx_hash": "0b43e5ff363d968570df47c6f01af88afea3a8c8c6e17db8116321a5f3d16918",
 * "block_height": 271559,
 * "tx_input_n": -1,
 * "tx_output_n": 0,
 * "value": 100000000,
 * "spent": false,
 * "confirmations": 1917,
 * "confirmed": "2014-08-03T04:27:19Z"
 * },
 * {
 * "tx_hash": "8aa1084ef581b939bccf96c5383eb86a600f788805860ffce37e0b0c3317f3c0",
 * "block_height": 271559,
 * "tx_input_n": -1,
 * "tx_output_n": 0,
 * "value": 100150000,
 * "spent": false,
 * "confirmations": 1917,
 * "confirmed": "2014-08-03T04:27:19Z"
 * },
 * {
 * "tx_hash": "6a3ac33e8fe8b275257e90b72013753ca35136db71395fd2b11089a38db4dc25",
 * "block_height": 271558,
 * "tx_input_n": -1,
 * "tx_output_n": 1,
 * "value": 110000000,
 * "spent": false,
 * "confirmations": 1918,
 * "confirmed": "2014-08-03T04:07:05Z"
 * },
 * {
 * "tx_hash": "f8c652d90b1aa2e510ab0963525836e1e1bcc1f93f7beca65a8902c54ed77d5e",
 * "block_height": 271558,
 * "tx_input_n": -1,
 * "tx_output_n": 1,
 * "value": 100270000,
 * "spent": false,
 * "confirmations": 1918,
 * "confirmed": "2014-08-03T04:07:05Z"
 * }
 * ],
 * "tx_url": "https://api.blockcypher.com/v1/btc/test3/txs/"
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
@Path("addrs")
@Consumes(MediaType.APPLICATION_JSON)
public class Address {

    private static final Logger logger = Logger.getLogger(Address.class);

    private String address;
    private BigDecimal balance;
    private BigDecimal finalBalance;
    private Long nTx;
    private List<TransactionSummary> txrefs;
    private String txUrl;

    @SerializedName("public")
    private String publicAddress;
    @SerializedName("private")
    private String privateAddress;

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public List<TransactionSummary> getTxrefs() {
        return txrefs;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Address address) {
        logger.debug("Address Event Received: " + address);
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public Long getnTx() {
        return nTx;
    }

    public String getTxUrl() {
        return txUrl;
    }

    public String getPublic() {
        return publicAddress;
    }

    public String getPrivate() {
        return privateAddress;
    }

}
