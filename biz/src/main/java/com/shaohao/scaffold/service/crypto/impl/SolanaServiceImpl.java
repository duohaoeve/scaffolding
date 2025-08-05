package com.shaohao.scaffold.service.crypto.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaohao.scaffold.dto.helius.SolanaTransactionDTO;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shaohao.scaffold.dto.helius.hlReq;
import com.shaohao.scaffold.service.crypto.SolanaService;
import com.shaohao.scaffold.util.HTTP;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Service
public class SolanaServiceImpl implements SolanaService {


    private static String nodeUrl = "https://api.helius.xyz/v0/transactions";

    private static final Logger logger = LoggerFactory.getLogger("ROOT");

    @Autowired
    private HTTP http;

    @Override
    public BigDecimal getBalance(String address) {
        // 创建一个 RPC 客户端，连接到 Solana 主网
//        RpcClient client = new RpcClient("https://api.mainnet-beta.solana.com");
        RpcClient client = new RpcClient("https://cold-hanni-fast-mainnet.helius-rpc.com/");

        // 替换为您要查询的账户地址
        PublicKey publicKey = new PublicKey(address);
        // 查询账户余额
        BigDecimal res = BigDecimal.ZERO;
        try {
            long balance = client.getApi().getBalance(publicKey);
            res = BigDecimal.valueOf(balance).divide(BigDecimal.valueOf(1000000000),5, RoundingMode.HALF_UP);
        } catch (Exception e) {
            logger.info("---------查询账户余额出错---------");
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public SolanaTransactionDTO getTransaction(String tx) {

        try {
            List<String> list = Collections.singletonList(tx); // 创建只读列表
            hlReq req = new hlReq();
            req.setTransactions(list); // 设置交易列表
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(req);

            String result = http.postJson(nodeUrl, jsonString);
            SolanaTransactionDTO transaction = JSON.parseObject(result, SolanaTransactionDTO.class);

            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
