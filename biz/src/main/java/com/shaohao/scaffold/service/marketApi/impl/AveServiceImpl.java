package com.shaohao.scaffold.service.marketApi.impl;

import com.alibaba.fastjson.JSONObject;
import com.shaohao.scaffold.dto.CaDto;
import com.shaohao.scaffold.service.marketApi.AveService;
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
public class AveServiceImpl implements AveService {

    private static final String aveUrl = "https://dev.ave-api.com/v2/wechat_bot/detail/";

    @Autowired
    private HTTP http;

    @Override
    public CaDto calcCa(String ca) {
        CaDto res = new CaDto();
        String url = aveUrl + ca;
        List<String> evmList = new ArrayList();
        evmList.add("-bsc");
        evmList.add("-base");
        evmList.add("-eth");
        if (ca.startsWith("0x")) {
            for (String evm : evmList) {
                String evmUrl = url + evm;
                try {
                    String responseStr = http.GET(evmUrl);
                    if (JSONObject.parseObject(responseStr).getString("msg").equals("SUCCESS")) {
                        JSONObject jsonString = JSONObject.parseObject(responseStr);
                        JSONObject data = JSONObject.parseObject(jsonString.getString("data"));
                        res.setSymbol(data.getString("symbol") + " (" + data.getString("name") + ")");
                        res.setChain(data.getString("chain"));
                        BigDecimal mc = data.getBigDecimal("market_cap");
                        res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                        String date = DateUtil.getStringDateByUnix(data.getInteger("created_at"));
                        res.setCreatedAt(date);
                        return res;
                    }
                } catch (Exception e) {
                    log.error("市值接口报错：", e);
                }
            }
        } else {
            url = url + "-solana";
            try {
                String responseStr = http.GET(url);
                if (!JSONObject.parseObject(responseStr).getString("msg").equals("SUCCESS")) {
                    log.error("市值接口报错！-URL：{}", url);
                } else {
                    JSONObject jsonString = JSONObject.parseObject(responseStr);
                    JSONObject data = JSONObject.parseObject(jsonString.getString("data"));
                    res.setSymbol(data.getString("symbol") + " (" + data.getString("name") + ")");
                    res.setChain(data.getString("chain"));
                    BigDecimal mc = data.getBigDecimal("market_cap");
                    res.setMarketCap(mc.divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                    String date = DateUtil.getStringDateByUnix(data.getInteger("created_at"));
                    res.setCreatedAt(date);
                    return res;
                }
            } catch (Exception e) {
                log.error("市值接口报错：", e);
            }
        }
        return res;
    }
}
