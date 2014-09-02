package com.blockcypher.model.transaction.input;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Input of a transation, ie:
 * <p/>
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
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class Input {

    private String prevHash;
    private Long outputIndex;
    private String script;
    private BigDecimal outputValue;
    private List<String> addresses = new ArrayList<String>();
    private String scriptType;

    public Input() {
    }

    public boolean addAddress(String address) {
        return addresses.add(address);
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }
}
