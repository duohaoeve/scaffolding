package com.shaohao.scaffold.dto.dbot.caInfo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDto {
    // 流动池所属的 DEX
    private String type;
    // 交易对地址
    private String pair;
    // 流动性大小（美元）
    private Long liquidityUsd;
    // 流动池内的代币数量
    private String tokenReserve;
    // 流动池内的 SOL / WETH 数量
    private String currencyReserve;
    // 代币市值（美元）
    private Long tokenMcUsd;
    // 代币创建时间
    private Long tokenCreateAt;
    // Pump 内盘进度
    private Double progress;
    // 流动池创建者
    private String poolCreator;
    // 流动池创建时间
    private Long poolCreateAt;
    // Dev 持仓比例
    private Double devHoldPercent;
    // 价格影响
    private Double priceImpact;
    // 代币安全信息
    private PoolSafetyInfoDto poolSafetyInfo;
    // 主币信息
    private CurrencyInfoDto currencyInfo;
    // 代币信息
    private TokenInfoDto tokenInfo;
    // 链接信息
    private LinksDto links;
}
