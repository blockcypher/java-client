package com.blockcypher.model.blockchain;

import com.blockcypher.utils.gson.GsonFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Representation of a BlockChain, ie:
 * {
 * "hash": "00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c",
 * "height": 271609,
 * "chain": "BTC.test3",
 * "total": 960521396717,
 * "fees": 320001,
 * "ver": 2,
 * "time": "2014-08-03T15:52:11Z",
 * "received_time": "2014-08-03T15:52:20.852Z",
 * "bits": 486604799,
 * "nonce": 3612431715,
 * "n_tx": 30,
 * "prev_block": "00000000000045f25f677c0362c57374b38d161d0db477eb8327de4b707581df",
 * "mrkl_root": "9e0ba979777064cc8eb6b7a07880d5442e91d2c8e5be59c668313973a96a7eeb",
 * "txids": [
 * "d0fb877a660bf6db7ecd4a0d510203bf2382f4c085936c95dbf65a2073ae1211",
 * "cabac5b28562e528c3419072ff40c501605d9afc38386f91a244641cef7dde26",
 * "2a562f7c8311025566a1566cd1a2760093a5b2ef4ec69045f23f8e80a963f577",
 * "cbddd645221b692d6b8f0123aec7f594973f5f3279d8cfdf034cb620e4c46768",
 * "332b7cde09b82d8bf437098d64d5bcb9b3b0576058b71cf7c8babe669bbf7c1a",
 * "66b3e13d0231515891277fd17f52b199e9079f4b7654779e261a528614d2b827",
 * "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35",
 * "32131636de1df812ca8b0c16e6f92f1963eccbfa8076e222409f9f1c3068acb6",
 * "226fa8fcdb7c010033e21f2c7df590797aa702b789f5c0f2a9451687c5c2407f",
 * "8b8935958ee19b79f6ba7537631fbedeeddb5ffe10b4fa4b5ff039afb5fdc41c",
 * "7b2f21946c7acf10550dee2fb85b9bb317d10ea46eeb9e0ca0fcdf08d60c4d43",
 * "fd8101cab3233053773f920e43b721c65a7e6d7d906a92680170327656046c70",
 * "f37c8faf0bc1a75c59878f1bcf5348cfeaf02d72dd8ceaaef70194c8fbe3d25b",
 * "d48aab146c6e63ab09eb610a12ba484c96fe8d87ebf2d588f94205e368eff6a0",
 * "529be293a10138293d53dcdafa611d23cbde5ae9638e196bb285f092fbe05294",
 * "d787c79bd4962b706ea04d94a67ba3b03df028b087dbd91c7b65e0ad7b64cb9a",
 * "305ecb48c8b82ff3a949f528adc3570237a1ea5e64f10085104b9786a4e126fa",
 * "cd3329962dbbd0548f2c34fa1cb4701d6d5bd33950d60baedbaee86c424f158b",
 * "8ff5fc295a3e7cf556d12092599b6a8169e09c87c4ce7eaaae9d31bac578868f",
 * "52a49f6e6e0dea10f4eefe583e24c728a46956b29be163df92e068c3e240ce4a"
 * ],
 * "prev_block_url": "https://api.blockcypher.com/v1/btc/test3/blocks/00000000000045f25f677c0362c57374b38d161d0db477eb8327de4b707581df",
 * "tx_url": "https://api.blockcypher.com/v1/btc/test3/txs/",
 * "next_txids": "https://api.blockcypher.com/v1/btc/test3/blocks/00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c?txstart=20\u0026limit=20"
 * }
 *
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class BlockChain {

    private static final Logger logger = Logger.getLogger(BlockChain.class);

    private String hash;
    private Long height;
    private String chain;
    private Long total;
    private Long fees;
    private Long ver;
    // todo convert
    private String time;
    // todo convert
    private String receivedTime;
    private Long bits;
    private Long nonce;
    private Long nTx;
    private String prevBlock;
    private String mrklRoot;
    private List<String> txids;
    private String prevBlockUrl;
    private String txUrl;
    private String nextTxids;

    public BlockChain() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(BlockChain blockChain) {
        logger.debug("BlockChain Event Received: " + blockChain);
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

    public String getHash() {
        return hash;
    }

    public Long getHeight() {
        return height;
    }

    public String getChain() {
        return chain;
    }

    public Long getTotal() {
        return total;
    }

    public Long getFees() {
        return fees;
    }

    public Long getVer() {
        return ver;
    }

    public List<String> getTxids() {
        return txids;
    }

    public String getTime() {
        return time;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public Long getBits() {
        return bits;
    }

    public Long getNonce() {
        return nonce;
    }

    public Long getnTx() {
        return nTx;
    }

    public String getPrevBlock() {
        return prevBlock;
    }

    public String getMrklRoot() {
        return mrklRoot;
    }

    public String getPrevBlockUrl() {
        return prevBlockUrl;
    }

    public String getTxUrl() {
        return txUrl;
    }

    public String getNextTxids() {
        return nextTxids;
    }
}
