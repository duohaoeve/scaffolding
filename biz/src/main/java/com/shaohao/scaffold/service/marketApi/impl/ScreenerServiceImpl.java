package com.shaohao.scaffold.service.marketApi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaohao.scaffold.dto.CaDto;
import com.shaohao.scaffold.service.marketApi.ScreenerService;
import com.shaohao.scaffold.util.DateUtil;
import com.shaohao.scaffold.util.HTTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScreenerServiceImpl implements ScreenerService {

    private static final String ScreenerUrl = "https://api.dexscreener.com/tokens/v1/";

    @Autowired
    private HTTP http;

    @Override
    public CaDto calcCa(String ca) {
        CaDto res = new CaDto();
        List<String> evmList = new ArrayList();
        evmList.add("ethereum/");
        evmList.add("bsc/");
        evmList.add("base/");
        if (ca.startsWith("0x")) {
            for (String evm : evmList) {
                String evmUrl = ScreenerUrl + evm + ca;
                try {

                    String responseStr = http.GET(evmUrl);
                    if (!responseStr.equals("[]")) {

                        List<String> list = JSONArray.parseArray(responseStr,String.class);
                        JSONObject object = JSONObject.parseObject(list.get(0));
                        BigDecimal mc = object.getBigDecimal("marketCap");
                        String chainId = object.getString("chainId");
                        String baseToken = object.getString("baseToken");
                        String symbol = JSONObject.parseObject(baseToken).getString("symbol");
                        String name = JSONObject.parseObject(baseToken).getString("name");
                        res.setSymbol(symbol + " (" + name + ")");
                        res.setChain(chainId);
                        res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                        String date = DateUtil.getStringDateByUnix(object.getInteger("pairCreatedAt"));
                        res.setCreatedAt(date);
                        return res;
                    }
                } catch (Exception e) {
                    log.error("市值接口报错：", e);
                }
            }
        } else {
            String url = ScreenerUrl + "solana/" + ca;
            try {
                String responseStr = http.GET(url);
                if (!responseStr.equals("[]")) {
                    List<String> list = JSONArray.parseArray(responseStr,String.class);
                    JSONObject object = JSONObject.parseObject(list.get(0));
                    BigDecimal mc = object.getBigDecimal("marketCap");
                    String chainId = object.getString("chainId");
                    String baseToken = object.getString("baseToken");
                    String symbol = JSONObject.parseObject(baseToken).getString("symbol");
                    String name = JSONObject.parseObject(baseToken).getString("name");
                    res.setSymbol(symbol + " (" + name + ")");
                    res.setChain(chainId);
                    res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                    String date = DateUtil.getStringDateByUnix(object.getInteger("pairCreatedAt"));
                    res.setCreatedAt(date);
                    return res;
                }
            } catch (Exception e) {
                log.error("市值接口报错：", e);
            }
        }
        return res;
    }

    @Override
    public CaDto calcCa_fake(String ca) {
        CaDto res = new CaDto();
        res.setSymbol("null");
        res.setChain("null");
        res.setMarketCap(BigDecimal.valueOf(888));
        res.setCreatedAt(DateUtil.currentStringDate());
        return res;
    }
}
