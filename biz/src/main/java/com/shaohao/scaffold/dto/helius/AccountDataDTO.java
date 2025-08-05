package com.shaohao.scaffold.dto.helius;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class AccountDataDTO {
    private String account; // 账户地址
    private Long nativeBalanceChange; // 原生余额变化
    private List<JSONObject> tokenBalanceChanges; // 代币余额变化列表
}
