package com.shaohao.scaffold.service.marketApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.generated.Int192;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint160;
import org.web3j.abi.datatypes.generated.Uint8;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

/**
 *  Uniswap API
 *  https://docs.uniswap.org/api/subgraph/guides/v3-examples
 *  https://thegraph.com/docs/en/subgraphs/querying/from-an-application/
 */
@Service
public class UniswapService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${uniswap.api.base-url:https://api.thegraph.com/subgraphs/name/uniswap/uniswap-v3}")
    private String baseUrl;


    /**
     * 执行自定义GraphQL查询。
     * @param graphqlQuery GraphQL查询字符串
     * @return API响应体
     */
    private String executeQuery(String graphqlQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");

        String requestBody = "{\"query\": \"" + graphqlQuery.replace("\"", "\\\"").replace("\n", " ") + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }


    /**
     * 获取Uniswap V3工厂的当前全局数据（池计数、交易计数、总交易量）。
     * @return API响应体
     */
    public String getFactoryGlobalData() {
        String query = "{ factory(id: \"0x1F98431c8aD98523631AE4a59f267346ea31F984\") { poolCount txCount totalVolumeUSD totalVolumeETH } }";
        return executeQuery(query);
    }

    /**
     * 获取特定池的基本信息（如ETH-USDC池）。
     * @param poolId 池ID（例如：0x8ad599c3a0ff1de082011efddc58f1908eb6e6d8）
     * @return API响应体
     */
    public String getPoolInfo(String poolId) {
        String query = "{ pool(id: \"" + poolId + "\") { tick token0 { symbol id decimals } token1 { symbol id decimals } feeTier sqrtPrice liquidity } }";
        return executeQuery(query);
    }

    /**
     * 获取前N个最具流动性的池。
     * @param limit 返回的数量（最大1000）
     * @return API响应体
     */
    public String getTopPoolsByLiquidity(int limit) {
        if (limit > 1000) limit = 1000;
        String query = "{ pools(first: " + limit + ", orderBy: liquidity, orderDirection: desc) { id } }";
        return executeQuery(query);
    }

    /**
     * 获取特定池的最近N个 swaps。
     * @param poolId 池ID
     * @param limit 返回的数量（默认100，最大1000）
     * @return API响应体
     */
    public String getPoolSwaps(String poolId, int limit) {
        if (limit > 1000) limit = 1000;
        String query = "{ swaps(first: " + limit + ", orderBy: timestamp, orderDirection: desc, where: { pool: \"" + poolId + "\" }) { id amount0 amount1 amountUSD timestamp transaction { id } } }";
        return executeQuery(query);
    }

    // 可以添加更多方法，例如获取位置（positions）或代币信息
}
