package com.shaohao.scaffold.util;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * 监控网站冷开
 *
 * @version
 * @author shaohao  2023年12月08日 下午2:28:00
 *
 */
public class WebSnapshotMonitor {
    private static String lastHash = null;
    @Getter
    @Setter
    private static Boolean onFlag = false;


    public static void checkSnapshotUpdate(String TARGET_URL) throws Exception {
        // 获取网页快照
        Document doc = Jsoup.connect(TARGET_URL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/91.0.4472.124")
                .timeout(10000)
                .get();

        // 提取页面所有可见文本
        String rawText = doc.text();

        // 规范化文本：移除时间戳、token 等动态内容
        String normalizedText = normalizeText(rawText);

        // 计算规范化文本的 MD5 哈希值
        String currentHash = calculateMD5(normalizedText);
        // 比较哈希值
        if (lastHash != null && !lastHash.equals(currentHash)) {
            System.out.println("检测到快照更新！时间: " + new java.util.Date());
            onFlag = true;
        }

        lastHash = currentHash;
    }

    private static String calculateMD5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    // 规范化文本，移除动态内容
    private static String normalizeText(String text) {
        // 移除时间戳（形如 2025-06-30, 01:32:45 等）
        text = Pattern.compile("\\d{4}-\\d{2}-\\d{2}|\\d{2}:\\d{2}(:\\d{2})?(\\s*(AM|PM))?", Pattern.CASE_INSENSITIVE).matcher(text).replaceAll("");
        // 移除常见 token 模式（形如 32 位或 64 位随机字符串）
        text = Pattern.compile("[0-9a-f]{32,64}", Pattern.CASE_INSENSITIVE).matcher(text).replaceAll("");
        // 移除多余空白
        text = text.replaceAll("\\s+", " ").trim();
        return text;
    }

    //测试
//    public static void main(String[] args) {
//        while (true) {
//            try {
//                checkSnapshotUpdate("https://jup.ag/studio");
//                TimeUnit.SECONDS.sleep(10);
//            } catch (Exception e) {
//                System.err.println("错误: " + e.getMessage());
//            }
//        }
//    }
}
