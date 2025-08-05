package com.shaohao.scaffold.enums.crypto;

//60 requests/second
//40 000 / Per day
public enum GetBlockNode {

    BSC("https://go.getblock.io/82ff1012ec8f43fc887331eb129a614b"),
    AVAX("https://go.getblock.io/181d91bbaa1748868731404140ec06f5");

    private String url;

    GetBlockNode( String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
