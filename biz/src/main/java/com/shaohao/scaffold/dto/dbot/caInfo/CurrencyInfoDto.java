package com.shaohao.scaffold.dto.dbot.caInfo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class CurrencyInfoDto {
    // 主币合约地址
    private String contract;
    // 主币名称
    private String name;
    // 主币符号
    private String symbol;
    // 主币小数位数
    private Integer decimals;
    // 主币总供应量
    private String totalSupply;
    // 主币图标 URL
    private String icon;
    // 主币创建时间
    private Long createAt;
}
