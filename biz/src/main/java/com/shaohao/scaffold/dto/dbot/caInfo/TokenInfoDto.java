package com.shaohao.scaffold.dto.dbot.caInfo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class TokenInfoDto {
    // 代币合约地址
    private String contract;
    // 代币名称
    private String name;
    // 代币符号
    private String symbol;
    // 代币小数位数
    private Integer decimals;
    // 代币价格（美元）
    private Double priceUsd;
    // 代币图标 URL
    private String icon;
}
