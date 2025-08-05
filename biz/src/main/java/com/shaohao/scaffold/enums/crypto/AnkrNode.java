package com.shaohao.scaffold.enums.crypto;

//300 requests/min
public enum AnkrNode {


    BSC("https://rpc.ankr.com/bsc"),
    POLYGON("https://rpc.ankr.com/polygon"),
    ETH("https://rpc.ankr.com/eth"),
    FANTOM("https://rpc.ankr.com/fantom"),
    ARB("https://rpc.ankr.com/arbitrum"),
    AVAX("https://rpc.ankr.com/avalanche"),
    SOL("https://rpc.ankr.com/solana_devnet");


    private String url;

    AnkrNode( String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
