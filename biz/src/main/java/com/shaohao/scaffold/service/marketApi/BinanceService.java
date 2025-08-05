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
 *  Binance API
 *  https://developers.binance.com/docs/zh-CN/binance-spot-api-docs
 */
@Service
public class BinanceService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${binance.api.base-url:https://api.binance.com}")
    private String baseUrl;


    /**
     * 获取服务器时间（公共端点，无需认证）。
     * @return API响应体（包含服务器时间）
     */
    public String getServerTime() {
        String url = baseUrl + "/api/v3/time";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    /**
     * 获取特定交易对的最新价格（公共端点）。
     * @param symbol 交易对符号（如 BTCUSDT）
     * @return API响应体
     */
    public String getTickerPrice(String symbol) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/v3/ticker/price")
                .queryParam("symbol", symbol)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    /**
     * 获取K线数据（公共端点）。
     * @param symbol 交易对符号（如 BTCUSDT）
     * @param interval K线间隔（如 1m, 5m, 1h）
     * @param limit 返回的数量（默认500，最大1000）
     * @return API响应体
     */
    public String getKlines(String symbol, String interval, Integer limit) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/v3/klines")
                .queryParam("symbol", symbol)
                .queryParam("interval", interval);
        if (limit != null) {
            builder.queryParam("limit", limit);
        }
        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // 可以添加更多方法，例如需要认证的端点（如获取账户信息），但需额外处理签名
    // 注意：认证端点需要API Key和Secret签名，这里仅示例公共端点
}
