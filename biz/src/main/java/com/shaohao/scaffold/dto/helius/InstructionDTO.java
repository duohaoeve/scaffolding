package com.shaohao.scaffold.dto.helius;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class InstructionDTO {
    private List<String> accounts; // 指令涉及的账户列表
    private String data; // 指令数据
    private String programId; // 程序 ID
    private List<JSONObject> innerInstructions; // 内部指令列表
}
