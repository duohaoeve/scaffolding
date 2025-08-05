package com.shaohao.scaffold.service.social.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.shaohao.scaffold.dto.toto.twDto;
import com.shaohao.scaffold.dto.toto.twReq;
import com.shaohao.scaffold.service.social.TwService;
import com.shaohao.scaffold.util.HTTP;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  获取推特用户名历史 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2024-11-07
 */
@Service
public class TwServiceImpl implements TwService {

    @Autowired
    private HTTP http;

//    private static final Logger logger = LoggerFactory.getLogger("MyLogger");
//    private static final Logger  myLogger = LoggerFactory.getLogger(TwServiceImpl.class);

    @Value("${tg.twbot.url}")
    private String url;
    @Value("${tg.twbot.key}")
    private String key;


    @Override
    public boolean test(String token, String wallet) {
        return false;
    }


    @Override
    public String GetName(String userName,String language) {
        twReq req = new twReq();
        req.setUser(userName).setSince("");
        String body = JSONObject.toJSONString(req);
        String res = "";
        try {
            Map<String, String> headers = Map.of("Authorization", key);
            String result = http.postJson(url, body, headers);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String data = jsonObject.getString("data");

            List<twDto> twList = JSONArray.parseArray(data, twDto.class);
            String usernames = twList.stream()
                    .map(twDto::getUsername)
                    .collect(Collectors.joining(", "));
            if (usernames.equals("null")){
               return null;
            }
            res = usernames;
        } catch (Exception e) {
            return null;
        }
        return res;
    }



}
