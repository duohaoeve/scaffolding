package com.shaohao.scaffold.dto.helius;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class hlReq {

    private List<String> transactions;
}
