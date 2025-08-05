package com.shaohao.scaffold.service.marketApi;

import com.shaohao.scaffold.dto.CaDto;

public interface ScreenerService {

    CaDto calcCa(String ca);
    CaDto calcCa_fake(String ca);
}
