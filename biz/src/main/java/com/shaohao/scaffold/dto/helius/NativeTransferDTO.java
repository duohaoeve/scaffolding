package com.shaohao.scaffold.dto.helius;

import lombok.Data;

@Data
public class NativeTransferDTO {
    private String fromUserAccount; // 转出账户
    private String toUserAccount; // 转入账户
    private Long amount; // 转账金额
}
