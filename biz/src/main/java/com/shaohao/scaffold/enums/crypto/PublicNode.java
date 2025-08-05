package com.shaohao.scaffold.enums.crypto;

public enum PublicNode {

    BSC("https://bscrpc.com");


    private String url;

    PublicNode( String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
