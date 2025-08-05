package com.shaohao.scaffold.service.marketApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 *  GeckoTerminal API
 *  30 calls/min
 * https://apiguide.geckoterminal.com/
 * https://www.geckoterminal.com/dex-api
 */
@Service
public class GeckoTerminalService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${geckoterminal.api.base-url:https://api.geckoterminal.com/api/v2}")
    private String baseUrl;


    /**
     * 获取支持的网络列表。
     * @return API响应体
     */
    public String getNetworks() {
        String url = baseUrl + "/networks";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    /**
     * 获取指定网络上代币的价格。
     * @param network 网络ID（例如：ethereum, solana）
     * @param tokenAddresses 逗号分隔的代币地址（最多30个）
     * @return API响应体
     */
    public String getTokenPrice(String network, String tokenAddresses) {
        String url = baseUrl + "/simple/networks/" + network + "/token_price/" + tokenAddresses;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    /**
     * 获取指定网络上特定池的信息。
     * @param network 网络ID
     * @param poolAddress 池地址
     * @return API响应体
     */
    public String getPoolInfo(String network, String poolAddress) {
        String url = baseUrl + "/networks/" + network + "/pools/" + poolAddress;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // 可以添加更多方法，例如获取新池列表
    /**
     * 获取所有网络上的新池列表。
     * @param page 分页（可选，默认1）
     * @return API响应体
     */
    public String getNewPoolsAcrossNetworks(Integer page) {
        String url = baseUrl + "/networks/new_pools";
        if (page != null) {
            url += "?page=" + page;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
