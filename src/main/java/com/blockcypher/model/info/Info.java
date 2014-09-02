package com.blockcypher.model.info;

import com.blockcypher.utils.gson.GsonFactory;

/**
 * Info on the currency/network, ie:
 * {
 * "name": "BTC.main",
 * "height": 314685,
 * "hash": "0000000000000000037c2d23d120f278ebfafefc2f107ed0a5603d82722ade6b",
 * "time": "2014-08-09T08:43:55.455046869Z",
 * "latest_url": "https://api.blockcypher.com/v1/btc/main/blocks/0000000000000000037c2d23d120f278ebfafefc2f107ed0a5603d82722ade6b",
 * "previous_hash": "000000000000000024827aa7145f264ac004b69c01f3d5096d8ce3c111bb4877",
 * "previous_url": "https://api.blockcypher.com/v1/btc/main/blocks/000000000000000024827aa7145f264ac004b69c01f3d5096d8ce3c111bb4877",
 * "peer_count": 314,
 * "unconfirmed_count": 686
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class Info {

    private String name;
    private Long height;
    private String hash;
    private String time;
    private String latestUrl;
    private String previousHash;
    private String previousUrl;
    private Long peerCount;
    private Long unconfirmedCount;

    public Info() {
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

    public String getName() {
        return name;
    }

    public Long getHeight() {
        return height;
    }

    public String getHash() {
        return hash;
    }

    public String getTime() {
        return time;
    }

    public String getLatestUrl() {
        return latestUrl;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public Long getPeerCount() {
        return peerCount;
    }

    public Long getUnconfirmedCount() {
        return unconfirmedCount;
    }


}
