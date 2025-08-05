package com.shaohao.scaffold.service.ai.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shaohao.scaffold.dto.ds.ChatCompletionRequest;
import com.shaohao.scaffold.dto.ds.ChatCompletionResponse;
import com.shaohao.scaffold.service.ai.DeepSeekAService;
import com.shaohao.scaffold.util.DateUtil;
import com.shaohao.scaffold.util.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekAServiceImpl implements DeepSeekAService {


    @Autowired
    private HTTP http;

    @Value("${deepseek.url}")
    private String url;

    @Value("${deepseek.bearer}")
    private String dsToken;
    // https://api-docs.deepseek.com/zh-cn/


    @Override
    public List<String> simpleChat(String msg) {
        return sendChat(Collections.singletonList(msg), false);
    }

    public List<String> sendChat(List<String> contents, boolean memory) {
        ChatCompletionRequest req = new ChatCompletionRequest();
        req.setModel("deepseek-reasoner"); //r1
        req.setStream(memory); //记忆
        if (contents.isEmpty()){
            return null;
        }
        List<ChatCompletionRequest.Message> messageList = new ArrayList<>();
        for (String msg : contents){
            ChatCompletionRequest.Message message = new ChatCompletionRequest.Message();
            message.setRole("user").setContent(msg);
            messageList.add(message);
        }
        req.setMessages(messageList);
        int on = DateUtil.currentSecond();
        Map<String,String> header = Map.of("Authorization",dsToken);
        String res = http.postJson(url, JSON.toJSONString(req),header);
        int off = DateUtil.currentSecond();
        JSONObject resJson = JSONObject.parseObject(res);
        ChatCompletionResponse responseDto  = resJson.toJavaObject(ChatCompletionResponse.class);
        List<ChatCompletionResponse.Choice> choices = responseDto.getChoices();
        if (choices.isEmpty()){
            return null;
        }

        List<String> result =  new ArrayList<>();
        for (ChatCompletionResponse.Choice choice : choices){
            if (choice.getMessage().getReasoningContent() != null){
                result.add(choice.getMessage().getReasoningContent()); // 仅r1，推理内容
            }
            result.add(choice.getMessage().getContent());
        }
        result.add("本次响应耗时:"+ (off-on)+"s");
        return result;
    }
}
