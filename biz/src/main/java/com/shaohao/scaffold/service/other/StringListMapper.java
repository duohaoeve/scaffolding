package com.shaohao.scaffold.service.other;

import java.util.Set;

public interface StringListMapper {
    // 查询所有字符串
    Set<String> findAll(String filePath);

    // 添加字符串
    void insert(String str, String filePath);

    // 删除字符串
    void delete(String str, String filePath);

    // 检查字符串是否存在
    boolean exists(String str, String filePath);

    // 清空所有字符串
    void clear(String filePath);
}
