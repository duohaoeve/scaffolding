package com.shaohao.scaffold.util;

import net.i2p.crypto.eddsa.Utils;
import org.p2p.solanaj.core.Account;

/**
 * sol工具类
 *
 * @version
 * @author shaohao  2023年12月08日 下午2:28:00
 *
 */
public class SVMUtil {



    // 创建 RPC 客户端
//        RpcClient client = new RpcClient("https://api.mainnet-beta.solana.com");


    public static void generateKey() throws Exception {
        // 生成新的钱包账户
        Account account = new Account(); // 生成账户地址和密钥

        // 打印账户的公钥和私钥
        System.out.println("Public Key: " + account.getPublicKey());
        System.out.println("Private Key: " + Utils.bytesToHex(account.getSecretKey()));
    }
}
