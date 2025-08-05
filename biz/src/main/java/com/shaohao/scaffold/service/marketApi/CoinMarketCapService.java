package com.shaohao.scaffold.service.marketApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 *  CoinMarketCap API
 */
@Service
public class CoinMarketCapService {


    @Value("${cmc.key}")
    private String apiKey;

    @Value("${cmc.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取最新的加密货币列表数据。
     * @param start 开始索引（从1开始）
     * @param limit 返回的数量（默认100，最大10000）
     * @param convert 转换货币（默认USD）
     * @return API响应体
     */
    public String getLatestListings(int start, int limit, String convert) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/v1/cryptocurrency/listings/latest")
                .queryParam("start", start)
                .queryParam("limit", limit)
                .queryParam("convert", convert)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // 可以添加更多方法，例如获取特定加密货币的信息
    /**
     * 获取特定加密货币的元数据。
     * @param id 加密货币ID
     * @return API响应体
     */
    public String getCryptocurrencyMetadata(String id) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/v2/cryptocurrency/info")
                .queryParam("id", id)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
