package com.shaohao.scaffold.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaohao.scaffold.entity.DeadLetterLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeadLetterLogMapper extends BaseMapper<DeadLetterLog> {
    @Select("SELECT COUNT(*) FROM dead_letter_log WHERE error_code != 'OPTIMISTIC_LOCK_FAILED'")
    long countByErrorCodeNotOptimisticLockFailed();
}
