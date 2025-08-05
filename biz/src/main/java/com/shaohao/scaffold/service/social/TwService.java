package com.shaohao.scaffold.service.social;

/**
 * <p>
 *  获取推特用户名历史 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2024-11-07
 */
public interface TwService {

    boolean test(String token, String wallet);
    String GetName(String userName,String language);
}
