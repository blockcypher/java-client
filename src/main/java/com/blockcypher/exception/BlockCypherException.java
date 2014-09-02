package com.blockcypher.exception;

import com.blockcypher.model.error.BlockCypherError;

/**
 * BlockCypherException
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class BlockCypherException extends Exception {

    private int status;
    private BlockCypherError blockCypherError;
    private Exception exception;

    public BlockCypherException(String message, int status, BlockCypherError blockCypherError) {
        super(message);
        this.status = status;
        this.blockCypherError = blockCypherError;
    }

    public BlockCypherException(Exception exception) {
        this.exception = exception;
    }

    public BlockCypherError getBlockCypherError() {
        return blockCypherError;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "BlockCypherException{" +
                "message=" + getMessage() +
                ", status=" + status +
                ", blockCypherError=" + (blockCypherError != null ? blockCypherError.getErrors() : "") +
                ", exception=" + exception +
                '}';
    }

}
