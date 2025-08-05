package com.shaohao.scaffold.dto.dbot.buy;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EarnGroup {

    private double pricePercent;
    private double amountPercent;
}
