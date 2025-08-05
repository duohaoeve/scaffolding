package com.shaohao.scaffold.dto.dbot.caInfo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PoolSafetyInfoDto {
    // 流动池类型
    private String type;
    // 代币总供应量
    private String totalSupply;
    // 代币总供应量（UI 显示格式）
    private String totalSupplyUI;
    // 是否可 mint（true 表示代币可 mint）
    private Boolean canMint;
    // 是否可冻结（true 表示代币可冻结）
    private Boolean canFrozen;
    // 代币转移手续费比例
    private String transferFeePercent;
    // 是否已委托（true 表示代币已委托）
    private String isDelegated;
    // 流动池内的代币数量
    private String tokenReserve;
    // 流动池内的代币数量（UI 显示格式）
    private String tokenReserveUI;
    // 流动池内的 SOL / WETH 数量
    private String currencyReserve;
    // 流动池内的 SOL / WETH 数量（UI 显示格式）
    private String currencyReserveUI;
    // 流动池的 LP 储备量
    private String lpReserve;
    // 流动池的 LP 储备量（UI 显示格式）
    private String lpReserveUI;
    // 已销毁或锁定的 LP 数量
    private String burnedOrLockedLp;
    // 已销毁或锁定的 LP 数量（UI 显示格式）
    private String burnedOrLockedLpUI;
    // 池子已销毁比例
    private Double burnedOrLockedLpPercent;
    // 前十持仓比例
    private Double top10Percent;
}
