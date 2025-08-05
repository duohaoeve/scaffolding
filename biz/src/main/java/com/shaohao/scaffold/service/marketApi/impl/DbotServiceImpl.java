package com.shaohao.scaffold.service.marketApi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shaohao.scaffold.dto.CaDto;
import com.shaohao.scaffold.dto.dbot.buy.BuyReq;
import com.shaohao.scaffold.dto.dbot.buy.DbsReq;
import com.shaohao.scaffold.dto.dbot.buy.EarnGroup;
import com.shaohao.scaffold.dto.dbot.caInfo.ResponseDto;
import com.shaohao.scaffold.enums.crypto.DbotEnum;
import com.shaohao.scaffold.service.marketApi.DbotService;
import com.shaohao.scaffold.util.DateUtil;
import com.shaohao.scaffold.util.HTTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DbotServiceImpl  implements DbotService {


    @Autowired
    private HTTP http;

    @Value("${dbot.api-key}")
    private static String dbotkey;

    @Override
    public CaDto calcCa(String ca) {
        CaDto res = new CaDto();
        List<String> evmList = new ArrayList();
        evmList.add("ethereum");
        evmList.add("bsc");
        evmList.add("base");

        Map<String,String> header = Map.of("X-API-KEY",dbotkey);
        if (ca.startsWith("0x")) {
            for (String evm : evmList) {
                String evmUrl = DbotEnum.DBOT_POOL_URL.getText() + evm + "&pair=" + ca;
                try {

                    String responseStr = http.GET(evmUrl,header);
                        JSONObject object = JSONObject.parseObject(responseStr);
                        Boolean err = object.getBoolean("err");
                        if (!err) {
                            JSONObject resJson = JSONObject.parseObject(object.getString("res"));
                            ResponseDto responseDto  = resJson.toJavaObject(ResponseDto.class);

                            BigDecimal mc = BigDecimal.valueOf(responseDto.getTokenMcUsd());
                            String chainId = responseDto.getPoolSafetyInfo().getType();
                            String symbol = responseDto.getTokenInfo().getSymbol();
                            String name = responseDto.getTokenInfo().getName();
                            res.setSymbol(symbol + " (" + name + ")");
                            res.setChain(chainId);
                            res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                            Integer createAt = (int) (responseDto.getPoolCreateAt() / 1000);
                            String date = DateUtil.getStringDateByUnix(createAt);
                            res.setCreatedAt(date);
                            res.setCa(responseDto.getTokenInfo().getContract());
                            return res;
                        }

                } catch (Exception e) {
                    log.error("dbot市值接口报错：", e);
                }
            }
        } else {
            String solUrl = DbotEnum.DBOT_POOL_URL.getText() + "solana" + "&pair=" + ca;
            try {
                String responseStr = http.GET(solUrl,header);
                JSONObject object = JSONObject.parseObject(responseStr);
                Boolean err = object.getBoolean("err");
                if (!err) {
                    JSONObject resJson = JSONObject.parseObject(object.getString("res"));
                    ResponseDto responseDto  = resJson.toJavaObject(ResponseDto.class);
                    BigDecimal mc = BigDecimal.valueOf(responseDto.getTokenMcUsd());
                    String chainId = responseDto.getPoolSafetyInfo().getType();
                    String symbol = responseDto.getTokenInfo().getSymbol();
                    String name = responseDto.getTokenInfo().getName();
                    res.setSymbol(symbol + " (" + name + ")");
                    res.setChain(chainId);
                    res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                    Integer createAt = (int) (responseDto.getPoolCreateAt() / 1000);
                    String date = DateUtil.getStringDateByUnix(createAt);
                    res.setCreatedAt(date);
                    res.setCa(responseDto.getTokenInfo().getContract());
                    return res;
                }
            } catch (Exception e) {
                log.error("市值接口报错：", e);
            }
        }
        return res;
    }

    @Override
    public void autoBuy(BuyReq req) {
        log.info("*****-：交易参数:"+ JSON.toJSONString(req));
        Map<String,String> header = Map.of("X-API-KEY",dbotkey);
        String res = http.postJson(DbotEnum.DBOT_SWAP_URL.getText(), JSON.toJSONString(req),header);
        System.out.println(res);
        log.info("*****-：交易结果:"+res);

    }


    @Override
    public void simSwap(DbsReq req) {

        //止盈策略
        setConfig1(req);
        Map<String,String> header = Map.of("X-API-KEY",dbotkey);
        http.postJson(DbotEnum.DBOT_SIM_URL.getText(), JSON.toJSONString(req),header);


    }

    public void setConfig1(DbsReq req) {
        EarnGroup g1 = new EarnGroup().setPricePercent(1).setAmountPercent(0.3);
        EarnGroup g2 = new EarnGroup().setPricePercent(2).setAmountPercent(0.2);
        EarnGroup g3 = new EarnGroup().setPricePercent(3).setAmountPercent(0.2);
        EarnGroup g4 = new EarnGroup().setPricePercent(4).setAmountPercent(0.3);
        EarnGroup g5 = new EarnGroup().setPricePercent(7).setAmountPercent(0.2);
        EarnGroup g6 = new EarnGroup().setPricePercent(9).setAmountPercent(0.5);
        List<EarnGroup> earnList = new ArrayList<>();
        earnList.add(g1);
        earnList.add(g2);
        earnList.add(g3);
        earnList.add(g4);
        earnList.add(g5);
        earnList.add(g6);
        earnList = earnList.stream()
                .sorted(Comparator.comparingDouble(EarnGroup::getPricePercent))
                .collect(Collectors.toList());
        req.setStopEarnGroup(earnList);
        EarnGroup l1 = new EarnGroup().setPricePercent(0.3).setAmountPercent(0.2);
        EarnGroup l2 = new EarnGroup().setPricePercent(0.4).setAmountPercent(0.3);
        EarnGroup l3 = new EarnGroup().setPricePercent(0.5).setAmountPercent(0.5);
        EarnGroup l4 = new EarnGroup().setPricePercent(0.6).setAmountPercent(1);
        List<EarnGroup> lossList = new ArrayList<>();
        lossList.add(l1);
        lossList.add(l2);
        lossList.add(l3);
        lossList.add(l4);
        lossList = lossList.stream()
                .sorted(Comparator.comparingDouble(EarnGroup::getPricePercent))
                .collect(Collectors.toList());
        req.setStopLossGroup(lossList);

    }
}
