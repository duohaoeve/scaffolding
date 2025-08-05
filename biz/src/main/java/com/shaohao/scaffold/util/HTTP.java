package com.shaohao.scaffold.util;


import okhttp3.Call;
import okhttp3.Request;

import java.io.IOException;
import java.util.Map;

/**
 * HTTP客户端常用接口封装，简化操作，提升性能，后续支持注解
 * 参考RestTemplate
 * @version
 *
 */
public interface HTTP {

    String ReqExecute(Request request) ;
    Call ReqExecuteCall(Request request);



    String GET(String baseUrl) ;

    /**
     *
     * GET同步方法
     *
     * @author shaohao 2016年7月17日
     * @param baseUrl
     * @param queryParams
     * @return
     * @throws IOException
     */
    String GET(String baseUrl, Map<String, String> queryParams);


    String postJson(String baseUrl, String jsonBody);

    /**
     * post json格式请求
     * @param baseUrl
     * @param jsonBody
     * @return
     * @throws HttpException
     * @throws IOException
     */
    String postJson(String baseUrl, String jsonBody, Map<String, String> headers);

}

