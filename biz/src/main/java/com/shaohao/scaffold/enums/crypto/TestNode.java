package com.shaohao.scaffold.enums.crypto;

public enum TestNode {

    BSC("https://data-seed-prebsc-1-s2.binance.org:8545"),
    BSC2("https://bsctestapi.terminet.io/rpc");


    private String url;

    TestNode( String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
