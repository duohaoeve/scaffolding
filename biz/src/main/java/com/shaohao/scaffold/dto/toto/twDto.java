package com.shaohao.scaffold.dto.toto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class twDto {

    private String username;
    private String last_checked;
}
