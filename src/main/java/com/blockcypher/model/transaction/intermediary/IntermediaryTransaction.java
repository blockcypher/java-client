package com.blockcypher.model.transaction.intermediary;

import com.blockcypher.model.transaction.Transaction;
import com.blockcypher.utils.gson.GsonFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * IntermediaryTransaction returned for signing with your private key ie:
 * {
 * "tx": {
 * "block_height": -1,
 * "hash": "7911c20a66557c7d558ffd9148a848502eb36297d4ad47981621778783ee2167",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz",
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "total": 100270000,
 * "fees": 0,
 * "relayed_by": "",
 * "confirmed": "0001-01-01T00:00:00Z",
 * "received": "2014-08-10T09:54:09.540826584Z",
 * "ver": 1,
 * "lock_time": 0,
 * "vin_sz": 1,
 * "vout_sz": 2,
 * "confirmations": 0,
 * "inputs": [
 * {
 * "prev_hash": "f8c652d90b1aa2e510ab0963525836e1e1bcc1f93f7beca65a8902c54ed77d5e",
 * "output_index": 1,
 * "script": "",
 * "output_value": 100270000,
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": ""
 * }
 * ],
 * "outputs": [
 * {
 * "value": 500000,
 * "script": "76a914f343f510e12156df80fee18ea1a319002f55747788ac",
 * "spent_by": "",
 * "addresses": [
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": 99770000,
 * "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
 * "spent_by": "",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ]
 * },
 * "tosign": [
 * "95e2f97e742fa930741704b803b9311431b812ec663496c1d58fc81865c70fc0"
 * ]
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class IntermediaryTransaction {

    private Transaction tx;
    private List<String> tosign;
    private List<String> signatures = new ArrayList<String>();
    private List<String> pubkeys = new ArrayList<String>();

    public IntermediaryTransaction() {
    }

    public boolean addSignature(String signature) {
        return signatures.add(signature);
    }

    public boolean addPubKeys(String pubkey) {
        return pubkeys.add(pubkey);
    }

    public List<String> getTosign() {
        return tosign;
    }

    public Transaction getTx() {
        return tx;
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

}
