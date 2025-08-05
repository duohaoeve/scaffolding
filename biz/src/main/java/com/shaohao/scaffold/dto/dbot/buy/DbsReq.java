package com.shaohao.scaffold.dto.dbot.buy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

//dbot 模拟器
@Data
@Accessors(chain = true)
public class DbsReq {

    private String chain;
    private String pair;
    private String walletId ="";
    private String type ="buy";
    private double amountOrPercent =0.5;
    private String stopEarnPercent;
    private String stopLossPercent;
    private List<EarnGroup> stopEarnGroup;
    private List<EarnGroup> stopLossGroup;
    private String priorityFee="";
    private int gasFeeDelta =1;
    private int maxFeePerGas =100;
    private double slippage =0.1;

}
