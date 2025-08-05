package com.shaohao.scaffold.dto.helius;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import java.util.List;

@Data
public class SolanaTransactionDTO {
    private String description; // 交易描述
    private String type; // 交易类型
    private String source; // 交易来源
    private Long fee; // 交易费用
    private String feePayer; // 费用支付者
    private String signature; // 交易签名
    private Long slot; // 交易槽位
    private Long timestamp; // 时间戳
    private List<JSONObject> tokenTransfers; // 代币转账列表
    private List<NativeTransferDTO> nativeTransfers; // 原生转账列表
    private List<AccountDataDTO> accountData; // 账户数据列表
    private Object transactionError; // 交易错误（可能为 null 或复杂对象）
    private List<InstructionDTO> instructions; // 指令列表
    private JSONObject events; // 事件对象
}
