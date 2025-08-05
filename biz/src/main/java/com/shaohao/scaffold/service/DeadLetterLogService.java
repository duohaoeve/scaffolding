package com.shaohao.scaffold.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaohao.scaffold.entity.DeadLetterLog;
import com.shaohao.scaffold.mapper.DeadLetterLogMapper;
import org.springframework.stereotype.Service;

@Service
public class DeadLetterLogService extends ServiceImpl<DeadLetterLogMapper, DeadLetterLog> {


    public long checkCount() {
        long conut = baseMapper.countByErrorCodeNotOptimisticLockFailed();
        if (conut > 10) {
            sendMsg();
        }
        return conut;
    }

    public void sendMsg() {

    }

}
