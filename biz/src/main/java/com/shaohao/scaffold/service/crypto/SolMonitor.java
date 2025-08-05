package com.shaohao.scaffold.service.crypto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaohao.scaffold.dto.helius.MonitorTransfers;
import com.shaohao.scaffold.dto.helius.MyUser;
import com.shaohao.scaffold.util.DateUtil;
import com.shaohao.scaffold.util.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * sol钱包监控
 * </p>
 *
 * @author shaohao
 * @since 2023-06-17
 */
@Service
public class SolMonitor {

    private static String nodeUrl = "https://api.helius.xyz/v0/addresses/";
    private static String token = "55bf84b3-47c5-40aa-bc41-ff683addc106";


    private static String USDT = "Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB";
    private static String USDC = "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v";
    private static String SOL = "So11111111111111111111111111111111111111112";
    private static Long day = 86400L;

    @Autowired
    private HTTP http;

    public void solMonitor(String address) {
        init(address);

        String url = nodeUrl + address + "/transactions";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("api-key", token);

        List<MonitorTransfers> resultList = new ArrayList<>();
        Long startTime = MyUser.getStartTime();
        try {
            String result = http.GET(url, queryParams);
            List<String> list = JSONArray.parseArray(result, String.class);
            for (String str : list) {
                MonitorTransfers vo = new MonitorTransfers();
                JSONObject jsonObject = JSONObject.parseObject(str);
                String timestamp = jsonObject.getString("timestamp");
                Long tamp = Long.parseLong(timestamp);
                if (tamp <= startTime) {
                    continue;
                }
                String description = jsonObject.getString("description");
                String signature = jsonObject.getString("signature");
                String tokenTransfers = jsonObject.getString("tokenTransfers");
                String token = "";
                List<String> tokenList = JSONArray.parseArray(tokenTransfers, String.class);
                for (String tra : tokenList) {
                    JSONObject trans = JSONObject.parseObject(tra);
                    String mint = trans.getString("mint");
                    if (!SOL.equals(mint) && !USDT.equals(mint) && !USDC.equals(mint)) {
                        token = mint;
                        break;
                    }
                }
                vo.setDescription(description);
                vo.setSignature(signature);
                timestamp = DateUtil.getStringDateByUnix(tamp.intValue());
                vo.setTimestamp(timestamp);
                vo.setToken(token);
                resultList.add(vo);

            }

            // 使用Stream和Comparator按timestamp属性排序
            List<MonitorTransfers> sortedTransfers = resultList.stream()
                    .sorted(Comparator.comparing(MonitorTransfers::getTimestamp)).collect(Collectors.toList());

            // 打印排序后的结果
            for (MonitorTransfers transfer : sortedTransfers) {
                System.out.println(transfer.getTimestamp() +" -- " + transfer.getDescription() + " --CA:" + transfer.getToken());
            }

            JSONObject jsonObject = JSONObject.parseObject(list.get(0));
            String timestamp = jsonObject.getString("timestamp");
            MyUser.setStartTime(Long.parseLong(timestamp));
//            System.out.println(" --timestamp:" + timestamp);

        } catch (Exception e) {
        }


    }

    public void init(String address) {
        if (MyUser.getStartTime() == null) {
            MyUser.User user = new MyUser.User();
            user.setAddress(address);
            Long startTime = DateUtil.currentSecond() - day;
            user.setStartTime(startTime);
            MyUser.put(user);
        }

    }

}
