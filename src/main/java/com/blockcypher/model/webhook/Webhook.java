package com.blockcypher.model.webhook;

import com.blockcypher.utils.gson.GsonFactory;

/**
 * Representation of a webhook, ie:
 * {
 * "id": "d8deda9f-af3b-41c0-b17a-e0d90f88dad7",
 * "url": "http://123.456.78.90:8000",
 * "errors": 0,
 * "event": "unconfirmed-tx",
 * "filter": "event=new-pool-tx"
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class Webhook {

    private String id;
    private String url;
    private int errors;
    private String event;
    private String filter;

    public Webhook() {
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getErrors() {
        return errors;
    }

    public String getEvent() {
        return event;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

}
