package com.shaohao.scaffold.service.marketApi;


import com.shaohao.scaffold.dto.CaDto;
import com.shaohao.scaffold.dto.dbot.buy.BuyReq;
import com.shaohao.scaffold.dto.dbot.buy.DbsReq;

public interface DbotService {

    CaDto calcCa(String ca);

    void autoBuy(BuyReq req);

    void simSwap(DbsReq req);
}
