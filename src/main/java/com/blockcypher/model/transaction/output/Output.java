package com.blockcypher.model.transaction.output;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Output of a transaction, ie:
 * {
 * "value": 100000000,
 * "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
 * "spent_by": "",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class Output {

    private BigDecimal value;
    private String script;
    private String spentBy;
    private List<String> addresses = new ArrayList<String>();
    private String scriptType;

    public Output() {
    }

    public boolean addAddress(String address) {
        return addresses.add(address);
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
