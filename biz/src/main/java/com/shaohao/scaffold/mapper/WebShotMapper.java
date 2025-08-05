package com.shaohao.scaffold.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.shaohao.scaffold.entity.WebShot;

/**
 * <p>
 * sys_user Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2024-11-07
 */
@Repository
@Mapper
public interface WebShotMapper extends BaseMapper<WebShot> {

}
