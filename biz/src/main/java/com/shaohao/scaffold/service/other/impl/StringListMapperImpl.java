package com.shaohao.scaffold.service.other.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.shaohao.scaffold.service.other.StringListMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class StringListMapperImpl implements StringListMapper {

    // 从文件加载数据到内存
    private Set<String> loadFromFile(String filePath) {
        Set<String> stringSet = new HashSet<>();
        File file = new File(filePath);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                StringBuilder jsonString = new StringBuilder();
                int ch;
                while ((ch = reader.read()) != -1) {
                    jsonString.append((char) ch);
                }
                // 使用 Fastjson 解析 JSON 字符串为 Set
                stringSet = JSON.parseObject(jsonString.toString(), new TypeReference<HashSet<String>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringSet;
    }

    // 将内存中的数据保存到文件
    private void saveToFile(Set<String> stringSet, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            String jsonString = JSON.toJSONString(stringSet);
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> findAll(String filePath) {
        return loadFromFile(filePath); // 从指定文件加载数据
    }

    @Override
    public void insert(String str, String filePath) {
        if (str != null && !str.trim().isEmpty()) {
            Set<String> stringSet = loadFromFile(filePath); // 加载现有数据
            stringSet.add(str); // 添加新字符串
            saveToFile(stringSet, filePath); // 保存到文件
        }
    }

    @Override
    public void delete(String str, String filePath) {
        if (str != null) {
            Set<String> stringSet = loadFromFile(filePath); // 加载现有数据
            stringSet.remove(str); // 删除字符串
            saveToFile(stringSet, filePath); // 保存到文件
        }
    }

    @Override
    public boolean exists(String str, String filePath) {
        Set<String> stringSet = loadFromFile(filePath); // 加载现有数据
        return stringSet.contains(str); // 检查字符串是否存在
    }

    @Override
    public void clear(String filePath) {
        saveToFile(new HashSet<>(), filePath); // 清空文件内容
    }
}
