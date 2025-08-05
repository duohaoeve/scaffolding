package com.shaohao.scaffold.dto.helius;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MonitorTransfers {

    /**
     * 时间
     */
    private String timestamp;
    /**
     * 交易动作
     */
    private String description;
    /**
     * 交易hash
     */
    private String signature;
    /**
     * tokenAddress
     */
    private String token;


}
