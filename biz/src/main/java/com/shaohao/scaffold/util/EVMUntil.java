package com.shaohao.scaffold.util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Web3工具类
 *
 * @version
 * @author shaohao  2023年12月08日 下午2:28:00
 *
 */
public class EVMUntil {

    /**
     * 根据私钥获取地址
     *
     * @return address
     */
    public static String getAddress(String privateKey) {
        ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        Credentials credentials = Credentials.create(keyPair);

        String address = credentials.getAddress();
        return address;

    }

    /**
     * 根据地址获取序号
     *
     * @return nonce
     */
    public static BigInteger getNonce(Web3j web3j, String accountAddress) throws IOException {

        // 获取发送方地址的 nonce 值
        BigInteger nonce = web3j.ethGetTransactionCount(
                accountAddress,
                DefaultBlockParameterName.LATEST).send().getTransactionCount();
        return nonce;


    }

    /**
     * 根据哈希获取交易
     *
     * @return transaction
     */
    public static Transaction getTransaction(Web3j web3j, String transactionHash) throws IOException {

        EthTransaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash)
                .send(); // 发送请求并等待响应

        Transaction transaction = ethTransaction.getTransaction().orElse(null);
        if (transaction != null) {
            // 通过交易对象获取所需的信息
            try {
                System.out.println("Transaction Block Number: " + transaction.getBlockNumber().toString());
            }catch (Exception e){
                System.out.println("Transaction暂未出块");
            }
            String blockHash = transaction.getBlockHash();
            String blockNumber = transaction.getBlockNumber().toString();
            String from = transaction.getFrom();
            String to = transaction.getTo();
            BigInteger gas = transaction.getGas();
            BigInteger gasPrice = transaction.getGasPrice();
            BigInteger value = transaction.getValue();
            String input = transaction.getInput();

            // 输出交易信息
            System.out.println("getNonce: " + transaction.getNonce());
            System.out.println("Block Hash: " + blockHash);
            System.out.println("Block Number: " + blockNumber);
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println("Gas: " + gas);
            System.out.println("Gas Price: " + gasPrice);
            System.out.println("Value: " + value);
            System.out.println("Input: " + input);
        } else {
            System.out.println("Transaction not found");
        }
        return transaction;


    }


    /**
     * 将字符串转换为十六进制表示 带0x
     *
     * @return hex
     */
    public static String strToHex(String string) {
        // 将字符串转换为十六进制表示
        String hexString = Numeric.toHexString(string.getBytes());
        return hexString;

    }

    /**
     * 将字符串转换为十六进制表示 不带0x
     *
     * @return address
     */
    public static String strTo0xHex(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();

    }

    /**
     * 将十六进制字符串转换回原始字符串
     * 十六进制转十进制用Numeric.toBigInt(data)
     * @return string
     */
    public static String hexToStr(String hexString) {
        // 将十六进制字符串转换回原始字符串
        byte[] byteArray = Numeric.hexStringToByteArray(hexString);
        String originalString = new String(byteArray);
        return originalString;

    }

    /**
     * 获取当前gas价格
     *
     * @return BigInteger gasPrice
     */
    public static BigInteger getGasPrice(Web3j web3j) throws IOException {

        // 调用eth_gasPrice获取当前gas价格
        EthGasPrice gasPrice = web3j.ethGasPrice().send();
        BigInteger gasPriceWei = gasPrice.getGasPrice();
        System.out.println("当前gas价格（以wei为单位）：" + gasPriceWei);
        return gasPriceWei;

    }

    /**
     * 获取当前ChainId
     *
     * @return BigInteger chainId
     */
    public static int getChainId(Web3j web3j) throws IOException {

        NetVersion netVersion = web3j.netVersion().send();
        String chainId = netVersion.getResult();
        return Integer.parseInt(chainId);

    }

}
