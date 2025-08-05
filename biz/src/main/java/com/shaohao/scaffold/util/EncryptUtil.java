package com.shaohao.scaffold.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 加密工具类
 *
 * @version
 * @author shaohao  2023年12月08日 下午2:28:00
 *
 */
public class EncryptUtil {

    private static final String ALGORITHM = "AES";



    // 生成 AES 秘钥
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // 使用 128 位 AES
        return keyGen.generateKey();
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData); // 编码为 Base64
    }

    // 解密
    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    //私钥
    public static SecretKey convertToSecretKey(String base64Key) {
        // 步骤 1: Base64 解码
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);

        // 步骤 2: 创建 SecretKeySpec
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return secretKey;
    }


    public static void main(String[] args) {
        try {
            // 生成密钥
            SecretKey secretKey = generateKey();
            // 打印密钥
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Generated Secret Key (Base64): " + encodedKey);

            String originalData = "Hello, World!";

            // 加密
            String encryptedData = encrypt(originalData, secretKey);
            System.out.println("Encrypted: " + encryptedData);
           // eptQaV+8WqQV0dUuNPnbYQ==
            // 解密
            SecretKey dsecretKey = convertToSecretKey(encodedKey);
            String decryptedData = decrypt(encryptedData, dsecretKey);
            System.out.println("Decrypted: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
