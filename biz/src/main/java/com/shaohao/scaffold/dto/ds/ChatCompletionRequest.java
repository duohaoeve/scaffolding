package com.shaohao.scaffold.dto.ds;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ChatCompletionRequest {

    private String model;  //model='deepseek-chat' DeepSeek-V3-0324
                           //model='deepseek-reasoner' DeepSeek-R1-0528
    private List<Message> messages;
    private boolean stream;

    @Data
    @Accessors(chain = true)
    public static class Message {
        private String role;
        private String content;
    }
}
