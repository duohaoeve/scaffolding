package com.shaohao.scaffold.dto.dbot.buy;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

//dbot 正式
@Data
@Accessors(chain = true)
public class BuyReq {

    private String chain;
    private String pair;
    private String walletId ="";
    private double amountOrPercent =0.5;
    private String type ="buy";
    private Boolean customFeeAndTip = false;
    private String priorityFee="";
    private Integer gasFeeDelta=1;
    private Integer maxFeePerGas=100;
    private Boolean jitoEnabled=false;
    private double jitoTip=0.001;
    private double maxSlippage =0.1;
    private Integer concurrentNodes =2;
    private Integer retries =1;
    private double migrateSellPercent =0;
    private double minDevSellPercent =0.9;
    private double devSellPercent =0;
    private double stopEarnPercent =1;
    private double stopLossPercent ;
    private List<EarnGroup> stopEarnGroup;
    private List<EarnGroup> stopLossGroup;
    private List<EarnGroup> trailingStopGroup ;
    private Integer pnlOrderExpireDelta =86400000 ;
    private Boolean pnlOrderExpireExecute =false ;
    private Boolean pnlOrderUseMidPrice =false ;
    private Boolean pnlCustomConfigEnabled =false ;
    private JSONObject pnlCustomConfig;





}
