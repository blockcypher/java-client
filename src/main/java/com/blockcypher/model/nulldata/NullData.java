package com.blockcypher.model.nulldata;

import com.blockcypher.utils.gson.GsonFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Representation of a NullData, ie:
 *{
 *  "data": "I am the walrus",
 *  "encoding": "string",
 *  "hash": "cb6974e0fd57c91b70403e85ef48c840eecdca4804dfc4897b1321d5328e4f18"
 *}
 */
@Path("/txs/data/")
@Consumes(MediaType.APPLICATION_JSON)
public class NullData {

    private static final Logger logger = Logger.getLogger(NullData.class);

		private String data;
		private String encoding;
		private String hash;

    public NullData() {
    }

    public String getData() {
        return data;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getHash() {
        return hash;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

}
