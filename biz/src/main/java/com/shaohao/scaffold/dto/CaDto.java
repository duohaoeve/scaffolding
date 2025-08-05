package com.shaohao.scaffold.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CaDto {

    private String symbol;
    private String chain;
    private BigDecimal marketCap;
    private String createdAt;
    private String roomId;
    private String groupName;
    private String sender;
    private double amount;
    private String ca;
}
