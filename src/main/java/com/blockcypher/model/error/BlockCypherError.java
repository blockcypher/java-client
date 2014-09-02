package com.blockcypher.model.error;

import com.blockcypher.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception, ie when creating new Transaction:
 * 400 Bad Request,
 * {
 * "errors": [
 * {
 * "error": "Address 1DEP8i3QJCsomS4BSMY2RpU1upv62aGvhD with balance 0 does not have enough funds to transfer 510000."
 * },
 * {
 * "error": "Error during simple transaction validation: Transaction missing input or output."
 * },
 * {
 * "error": "Error validating transaction: Transaction missing input or output."
 * }
 * ],
 * "tx": {
 * "block_height": -1,
 * "hash": "2e0ab6c42179f605849bac6b5bde68f737db066f964ec3855d42a994c41d3fc5",
 * "addresses": [
 * "mun8AMLMVYMWm9N87jkof3eRXV7rVBCTG2",
 * "mskLRm8P7EK4YYXo9vWQFjgLmpWny4F9k9"
 * ],
 * "total": 18446744073709541616,
 * "fees": 10000,
 * "relayed_by": "",
 * "confirmed": "0001-01-01T00:00:00Z",
 * "received": "2014-08-10T08:09:06.408638589Z",
 * "ver": 1,
 * "lock_time": 0,
 * "vin_sz": 0,
 * "vout_sz": 2,
 * "confirmations": 0,
 * "inputs": [],
 * "outputs": [
 * {
 * "value": 500000,
 * "script": "76a9149c703cb3e9f1a2a154e548b3acafd0054d4835a788ac",
 * "spent_by": "",
 * "addresses": [
 * "mun8AMLMVYMWm9N87jkof3eRXV7rVBCTG2"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": -510000,
 * "script": "76a9148629647bd642a2372d846a7660e210c8414f047c88ac",
 * "spent_by": "",
 * "addresses": [
 * "mskLRm8P7EK4YYXo9vWQFjgLmpWny4F9k9"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ]
 * }
 * }
 *
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class BlockCypherError {

    private List<Error> errors = new ArrayList<Error>();
    private Transaction tx;

    public BlockCypherError() {
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Transaction getTx() {
        return tx;
    }

    public boolean addError(Error error) {
        return errors.add(error);
    }

    @Override
    public String toString() {
        return "BlockCypherError{" +
                "errors=" + errors +
                ", tx=" + tx +
                '}';
    }
}
