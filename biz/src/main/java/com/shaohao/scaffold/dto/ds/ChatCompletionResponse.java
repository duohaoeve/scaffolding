package com.shaohao.scaffold.dto.ds;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ChatCompletionResponse {

    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;

    @Data
    @Accessors(chain = true)
    public static class Choice {
        private Integer index;
        private Message message;
        private String logprobs;
        private String finishReason;

        @Data
        @Accessors(chain = true)
        public static class Message {
            private String role;
            private String content;
            private String reasoningContent;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Usage {
        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;
        private TokenDetails promptTokensDetails;
        private TokenDetails completionTokensDetails;
        private Integer promptCacheHitTokens;
        private Integer promptCacheMissTokens;

        @Data
        @Accessors(chain = true)
        public static class TokenDetails {
            private Integer cachedTokens;
            private Integer reasoningTokens;
        }
    }
}
