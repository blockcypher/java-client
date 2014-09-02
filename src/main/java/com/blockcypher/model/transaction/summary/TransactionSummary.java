package com.blockcypher.model.transaction.summary;

import java.math.BigDecimal;

/**
 * Transaction Summary from an adress, ie:
 * {
 * "tx_hash": "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35",
 * "block_height": 271609,
 * "tx_input_n": -1,
 * "tx_output_n": 0,
 * "value": 100000000,
 * "spent": false,
 * "confirmations": 1867,
 * "confirmed": "2014-08-03T15:52:11Z"
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class TransactionSummary {

    private String txHash;
    private Long blockHeight;
    private Long txInputN;
    private Long txOutputN;
    private BigDecimal value;
    private boolean spent;
    private Long confirmations;
    // todo use converter for this
    private String confirmed;

    public TransactionSummary() {
    }

    public String getTxHash() {
        return txHash;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public Long getTxInputN() {
        return txInputN;
    }

    public Long getTxOutputN() {
        return txOutputN;
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean isSpent() {
        return spent;
    }

    public Long getConfirmations() {
        return confirmations;
    }

    public String getConfirmed() {
        return confirmed;
    }
}
