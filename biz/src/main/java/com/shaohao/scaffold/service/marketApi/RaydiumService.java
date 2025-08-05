package com.shaohao.scaffold.service.marketApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

/**
 *  Raydium API
 *  https://github.com/raydium-io
 *  https://docs.raydium.io/raydium/traders/trade-api
 */
@Service
public class RaydiumService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${raydium.api.base-url:https://api.raydium.io}")
    private String baseUrl;



    /**
     * 根据代币地址获取价格（以 USDC 为基准）或市值。
     * @param mintAddress 代币的 Mint 地址
     * @param calculateMarketCap 是否计算市值（true 为市值，false 为价格）
     * @return 价格（USDC/代币）或市值（USDC）
     * @throws Exception 如果无法找到池或数据解析失败
     */
    public BigDecimal getTokenPriceOrMarketCap(String mintAddress, boolean calculateMarketCap) throws Exception {
        // 1. 构建 API 请求，获取代币相关的流动性池信息
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/pools/info/token")
                .queryParam("tokenMint", mintAddress)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // 2. 调用 Raydium API 获取池数据
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getBody() == null) {
            throw new Exception("无法获取流动性池数据");
        }

        // 3. 解析 JSON 响应
        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
        // 假设返回的数据中包含池列表
//        Map<String, Object> poolData = ((Map<String, Object>) responseMap.get("data")).get(0);
        Map<String, Object> poolData = null;

        // 4. 提取池的储备量
        BigDecimal baseReserve = new BigDecimal(poolData.get("baseReserve").toString());
        BigDecimal quoteReserve = new BigDecimal(poolData.get("quoteReserve").toString());
        int baseDecimals = Integer.parseInt(poolData.get("baseDecimals").toString());
        int quoteDecimals = Integer.parseInt(poolData.get("quoteDecimals").toString());

        // 5. 计算价格（quote/base，假设 quote 是 USDC）
        BigDecimal price = quoteReserve
                .divide(baseReserve, 10, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(Math.pow(10, baseDecimals - quoteDecimals)));

        // 6. 如果需要市值，获取代币总供应量
        if (calculateMarketCap) {
            // 注意：Raydium API 不直接提供总供应量，需通过 Solana 区块链 RPC 或其他 API 获取
            BigDecimal totalSupply = getTotalSupply(mintAddress);
            return price.multiply(totalSupply);
        }

        return price;
    }

    /**
     * 获取代币总供应量（示例，需替换为实际 Solana RPC 调用）
     * @param mintAddress 代币地址
     * @return 总供应量
     * @throws Exception 如果无法获取供应量
     */
    private BigDecimal getTotalSupply(String mintAddress) throws Exception {
        // 示例：通过 Solana RPC 或其他 API（如 Solscan）获取总供应量
        // 这里仅为占位实现，实际需调用 Solana 的 getTokenSupply 方法
        String url = "https://api.mainnet-beta.solana.com";
        String requestBody = String.format(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getTokenSupply\",\"params\":[\"%s\"]}",
                mintAddress);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
        Map<String, Object> result = (Map<String, Object>) responseMap.get("result");
        Map<String, Object> value = (Map<String, Object>) result.get("value");

        return new BigDecimal(value.get("amount").toString())
                .divide(new BigDecimal(Math.pow(10, ((Integer) value.get("decimals")))));
    }

    /**
     * 获取所有AMM V4池的基本信息列表。
     * @return API响应体（JSON数组，包含池数据）
     */
    public String getAmmV4Pools() {
        String url = baseUrl + "/v4/ammV4/ammPools";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    /**
     * 获取AMM V5池的基本信息列表。
     * @return API响应体（JSON数组，包含池数据）
     */
    public String getAmmV5Pools() {
        String url = baseUrl + "/v5/ammV5/ammPools";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    /**
     * 获取CLMM（Concentrated Liquidity Market Maker）池列表。
     * @return API响应体（JSON数组，包含池数据）
     */
    public String getClmmPools() {
        String url = baseUrl + "/v2/ammV3/clmmpools";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    /**
     * 获取特定池的交易历史。
     * @param poolId 池ID（例如：AMM池地址）
     * @param startTime 开始时间戳（Unix时间，毫秒）
     * @param endTime 结束时间戳（Unix时间，毫秒）
     * @return API响应体
     */
    public String getPoolTrades(String poolId, long startTime, long endTime) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/v2/ammV3/poolTrades")
                .queryParam("pool", poolId)
                .queryParam("startTime", startTime)
                .queryParam("endTime", endTime)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    // 可以添加更多方法，例如获取SDK配置或特定代币价格
    /**
     * 获取主网流动性池的SDK配置JSON。
     * @return API响应体
     */
    public String getMainnetLiquiditySdk() {
        String url = baseUrl + "/v2/sdk/liquidity/mainnet.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }
}
