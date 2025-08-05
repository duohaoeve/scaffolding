package com.shaohao.scaffold.dto.dbot.caInfo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class LinksDto {
    // 官网链接
    private String website;
    // Twitter 链接
    private String twitter;
    // Telegram 链接
    private String telegram;
}
