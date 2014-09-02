package com.blockcypher.model.transaction;

import com.blockcypher.model.transaction.input.Input;
import com.blockcypher.model.transaction.output.Output;
import com.blockcypher.utils.gson.GsonFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Transaction, ie:
 * {
 * "block_hash": "00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c",
 * "block_height": 271609,
 * "hash": "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35",
 * "addresses": [
 * "mqz1CxAGWahHuaTnjHFnitfv8VguUwe7dN",
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz",
 * "mz8KgrQiSqZ3UmaXsx23hQZMVDet5wYBFF",
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "total": 499950000,
 * "fees": 0,
 * "relayed_by": "",
 * "confirmed": "2014-08-03T15:52:11Z",
 * "received": "2014-08-03T15:51:46.512Z",
 * "ver": 1,
 * "lock_time": 0,
 * "vin_sz": 1,
 * "vout_sz": 3,
 * "confirmations": 1392,
 * "inputs": [
 * {
 * "prev_hash": "c3fe841599794f88374b0aaf0cbd5b3897d75c4dc897a846e6040054d5495d66",
 * "output_index": 0,
 * "script": "483045022100ddb75ef19a31eb5e25595cb35c6b5f058912cc168a32a215c680a5532900904202200efb197876164fa246ff5009a04f39ff51db70adb90ee342f0aa97ec19d776eb012103f78041c92a4aea6e44ac937c8bd7e504e14768a40879dc7655e533a749fea55b",
 * "output_value": 499950000,
 * "addresses": [
 * "mqz1CxAGWahHuaTnjHFnitfv8VguUwe7dN"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ],
 * "outputs": [
 * {
 * "value": 100000000,
 * "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
 * "spent_by": "",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": 100000000,
 * "script": "76a914f343f510e12156df80fee18ea1a319002f55747788ac",
 * "spent_by": "2d43c093db79ecb03dc44552a05dfe468e504e7f8077841401f055c5ae30b69d",
 * "addresses": [
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": 299950000,
 * "script": "76a914cc22ae49e38122e0edcd97c5197cf12b39be01af88ac",
 * "spent_by": "a26abe3174100bb95bc771e7e3368435c7e8e4afcfa0e91c6f2a37cedc43d7bc",
 * "addresses": [
 * "mz8KgrQiSqZ3UmaXsx23hQZMVDet5wYBFF"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ]
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
@Path("/txs/")
public class Transaction {

    private static final Logger logger = Logger.getLogger(Transaction.class);

    private String hash;
    //@SerializedName("block_hash") Commented use this instead:
    // new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    private String blockHash;
    private Long blockHeight;

    private List<String> addresses;

    private BigDecimal total;
    private BigDecimal fees;
    private String relayedBy;
    private String confirmed;
    private String received;
    private Integer ver;
    private Long lockTime;
    private Long vinSz;
    private Long voutSz;
    private Long confirmations;

    private List<Input> inputs = new ArrayList<Input>();
    private List<Output> outputs = new ArrayList<Output>();

    public Transaction() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Transaction transaction) {
        logger.debug("Transaction Event Received: " + transaction);
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

    public boolean addInput(Input input) {
        return inputs.add(input);
    }

    public boolean addOutput(Output output) {
        return outputs.add(output);
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public String getHash() {
        return hash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getRelayedBy() {
        return relayedBy;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getReceived() {
        return received;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public Long getConfirmations() {
        return confirmations;
    }
}
