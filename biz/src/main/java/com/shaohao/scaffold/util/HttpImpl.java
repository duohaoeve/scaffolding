package com.shaohao.scaffold.util;


import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 封装http协议，简化操作
 *
 * @author shaohao
 */
@Service
public class HttpImpl implements HTTP {
    public final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");
    public final MediaType MEDIA_XML = MediaType.parse("application/xml; charset=utf-8");

    //最大尝试次数
    @Value("${task.http.maxRetryCount}")
    private int maxRetryCount;

    //成功状态码
    @Value("${task.http.expectedStatusCode}")
    private int expectedStatusCode;

    @Autowired
    private  OkHttpClient httpClient;

    /**
     *
     * GET同步方法
     *
     * @author liuyi 2016年7月17日
     * @param baseUrl
     * @param queryParams
     * @return
     * @throws IOException
     */
    @Override
    public  String GET(String baseUrl, Map<String, String> queryParams) {
        //拼装param
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        for (Map.Entry<String, String> item : queryParams.entrySet()) {
            urlBuilder.addQueryParameter(item.getKey(), item.getValue());
        }
        HttpUrl httpUrl= urlBuilder.build();

        //发送请求
        Request request = new Request.Builder()
                .url(httpUrl.toString()).get()
                .build();
        return ReqExecute(request);
    }

    @Override
    public  String GET(String baseUrl)  {
        //拼装param
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        HttpUrl httpUrl= urlBuilder.build();
        //发送请求
        Request request = new Request.Builder()
                .url(httpUrl.toString()).get()
                .build();
        return ReqExecute(request);
    }

    @Override
    public String postJson(String baseUrl, String jsonBody)  {

        RequestBody body = RequestBody.create(MEDIA_JSON, jsonBody);
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();
        String result = ReqExecute(request);

        return result;
    }


    @Override
    public String postJson(String baseUrl, String jsonBody, Map<String, String> headers) {

        RequestBody body = RequestBody.create(MEDIA_JSON, jsonBody);
        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl)
                .post(body);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        String result = ReqExecute(request);

        return result;
    }

    public void sleep(int second) {
        try {
            Thread.sleep(1000*second); // 休眠十秒（10000毫秒）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 请求方法
     *
     * @author liuyi 2016年7月17日
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public  String ReqExecute(Request request) {
        int currentRetryCount = 0;
        String result = "";
        while (currentRetryCount < maxRetryCount) {
            try {
                Response response = ReqExecuteCall(request).execute();
                result = new String(response.body().bytes());
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == expectedStatusCode) {
                    // 请求成功，退出循环
                    break;
                } else {
                    // 响应状态码不符合预期，记录日志等
                    currentRetryCount++;
                    if (currentRetryCount < maxRetryCount) {
                        sleep(1);  // 等待 1 秒
                    }
                }
            } catch (IOException e) {
                // 处理请求异常，记录日志等
                currentRetryCount++;
                if (currentRetryCount < maxRetryCount) {
                    sleep(1);
                }
            }
        }

        return result;
    }

    /**
     *
     * 构造CALL方法
     *
     * @author liuyi 2016年7月17日
     * @param request
     * @return
     */
    @Override
    public  Call ReqExecuteCall(Request request){
        return httpClient.newCall(request);
    }

}
