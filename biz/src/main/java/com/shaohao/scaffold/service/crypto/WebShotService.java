package com.shaohao.scaffold.service.crypto;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaohao.scaffold.entity.WebShot;

import java.util.List;

public interface WebShotService extends IService<WebShot> {
    List<WebShot> getOnList();
    String postMsg(String url);
    void initShot(long chatId);
}
