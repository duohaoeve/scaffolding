package com.shaohao.scaffold.service.crypto.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shaohao.scaffold.entity.WebShot;
import com.shaohao.scaffold.mapper.WebShotMapper;
import com.shaohao.scaffold.service.crypto.WebShotService;
import com.shaohao.scaffold.util.HTTP;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2024-11-07
 */
@Service
public class WebShotServiceImpl extends ServiceImpl<WebShotMapper, WebShot>  implements WebShotService {


    @Autowired
    private HTTP http;

    @Override
    public List<WebShot> getOnList(){

        QueryWrapper<WebShot> wrapper = new QueryWrapper();
        wrapper.eq("i_status", 1);
        List<WebShot> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public String postMsg(String url) {
        Request request = new Request.Builder()
                .url(url).get()
                .build();
        String result = http.ReqExecute(request);
        return result;

    }

    @Override
    public void initShot(long chatId) {
        List<WebShot> list = getOnList();
        if (list == null || list.size() == 0) {
            return;
        }
        for (WebShot webShot : list) {
            webShot.setChatId(chatId);
            baseMapper.updateById(webShot);
        }
    }

}
