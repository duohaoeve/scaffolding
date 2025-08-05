package com.shaohao.scaffold.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * web_shot
 * </p>
 *
 * @author shaohao
 * @since 2024-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_shot")
@Accessors(chain = true)
public class WebShot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 监控网址
     */
    private String targetUrl;

    /**
     * 状态
     */
    private Integer iStatus;

    /**
     * chatId
     */
    private Long chatId;

    /**
     * 通知地址
     */
    private String postUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    private Integer updateDate;


}
