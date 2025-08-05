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

/**
 *  CoinGecko API
 *  https://docs.coingecko.com/reference/introduction
 */
@Service
public class CoinGeckoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${coingecko.api.base-url:https://api.coingecko.com/api/v3}")
    private String baseUrl;


    /**
     * 获取指定代币的当前价格。
     * @param ids 代币ID列表，用逗号分隔（例如：bitcoin,ethereum,solana）
     * @param vsCurrencies 对比货币，用逗号分隔（例如：usd,eur）
     * @return API响应体（JSON对象，包含价格）
     */
    public String getTokenPrices(String ids, String vsCurrencies) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/simple/price")
                .queryParam("ids", ids)
                .queryParam("vs_currencies", vsCurrencies)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    // 示例：添加更多参数，如包含24h变化
    /**
     * 获取指定代币的详细价格信息，包括24h变化、市场市值等。
     * @param ids 代币ID列表，用逗号分隔
     * @param vsCurrency 对比货币（默认usd）
     * @return API响应体
     */
    public String getTokenMarketData(String ids, String vsCurrency) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/coins/markets")
                .queryParam("vs_currency", vsCurrency)
                .queryParam("ids", ids)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }
}
