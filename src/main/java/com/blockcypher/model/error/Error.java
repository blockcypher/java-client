package com.blockcypher.model.error;

/**
 * Error String
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class Error {

    private String error;

    public Error() {
    }

    @Override
    public String toString() {
        return "Error{" +
                "error='" + error + '\'' +
                '}';
    }

}
