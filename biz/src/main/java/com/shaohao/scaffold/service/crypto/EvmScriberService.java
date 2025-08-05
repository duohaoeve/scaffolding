package com.shaohao.scaffold.service.crypto;


import com.shaohao.scaffold.util.EVMUntil;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class EvmScriberService {


    private static String empty = "-1";

    /**
     *  批量铭文
     *
     * @return BigInteger decimals
     */
    public void batchScriber1(String nodeUrl ,  String inputData, String privateKey, String toAddress) throws IOException {

        Web3j web3j = Web3j.build(new HttpService(nodeUrl));

        // 设置发送交易的账户凭据
        Credentials credentials = Credentials.create(privateKey);
        // 获取发送方地址
        String owner =  credentials.getAddress();
        if (empty.equals(toAddress)){
            toAddress = owner;
        }
//        // 获取发送方地址的 nonce 值
        BigInteger nonce = EVMUntil.getNonce(web3j,owner);
        System.out.println("nonce: " + nonce);

                BigInteger gasPrice = EVMUntil.getGasPrice(web3j);
//        BigInteger gasPrice = new BigInteger("120000000");



        BigDecimal gasPriceDecimal = new BigDecimal(gasPrice);
        BigDecimal multiplied = gasPriceDecimal.multiply(new BigDecimal("1.03"));
        gasPrice = multiplied.setScale(0, RoundingMode.UP).toBigInteger();




        int chainId = EVMUntil.getChainId(web3j);

        for (int i =0;i<1;i++) {
            sleep(16);
            try {
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,  gasPrice, //BigInteger.valueOf(143727930358L),
                    BigInteger.valueOf(21000), toAddress,
                    inputData);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(
                    Numeric.toHexString(signedMessage)).send();
            String transactionHash = ethSendTransaction.getTransactionHash();
                System.out.println("----------getResult: " + ethSendTransaction.getResult());
            while (transactionHash == null){
                ethSendTransaction = web3j.ethSendRawTransaction(
                        Numeric.toHexString(signedMessage)).send();
                transactionHash = ethSendTransaction.getTransactionHash();
            }
//            if (transactionHash != null){
                System.out.println("Transaction sent successfully. Transaction hash: " + transactionHash);
            System.out.println("Tnonce: " + nonce);
            if (ethSendTransaction.getError() != null){System.out.println("--------ethSendTransaction.getError" );
                if (ethSendTransaction.getError().getCode() == 13){System.out.println("---------getErrorgetCode13");
                    nonce = nonce.add(BigInteger.valueOf(1));
                    continue;

                }
            }
//            }
//            Transaction result = Web3Until.getTransaction(web3j,transactionHash);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            nonce = nonce.add(BigInteger.valueOf(1));

        }
    }

    public void sleep(int second) {
        try {
            Thread.sleep(100*second); // 休眠十秒（10000毫秒）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  批量铭文
     *
     * @return BigInteger decimals
     */
    public void batchScriber(String nodeUrl ,  String inputData, String privateKey, String toAddress) throws IOException {

        Web3j web3j = Web3j.build(new HttpService(nodeUrl));

        // 设置发送交易的账户凭据
        Credentials credentials = Credentials.create(privateKey);
        // 获取发送方地址
        String owner =  credentials.getAddress();
        if (empty.equals(toAddress)){
            toAddress = owner;
        }
//        // 获取发送方地址的 nonce 值
        final BigInteger[] nonce = {EVMUntil.getNonce(web3j, owner)};
        System.out.println("nonce: " + nonce[0]);

        BigInteger gasPrice = EVMUntil.getGasPrice(web3j);



        BigDecimal gasPriceDecimal = new BigDecimal(gasPrice);
        BigDecimal multiplied = gasPriceDecimal.multiply(new BigDecimal("1.09"));
        gasPrice = multiplied.setScale(0, RoundingMode.UP).toBigInteger();




        int chainId = EVMUntil.getChainId(web3j);

        for (int i =0;i<1;i++) {
            sleep(3);
                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        nonce[0],  gasPrice, //BigInteger.valueOf(118468116189L),
                        BigInteger.valueOf(Long.parseLong("10240000")), toAddress,
                        inputData);
                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
//                EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(
//                        Numeric.toHexString(signedMessage)).send();

//            sleep(20);
                Request<?, EthSendTransaction> request = web3j.ethSendRawTransaction(Numeric.toHexString(signedMessage));
                request.sendAsync().thenAccept(ethSendTransaction -> {
//                    if (ethSendTransaction.hasError()) {
//                        // 出现错误
//                        Response.Error error = ethSendTransaction.getError();
//                        System.out.println("Error: " + error.getCode() + " - " + error.getMessage());
//                        if ("known transaction".equals(error.getMessage())){
//                            nonce[0] = nonce[0].add(BigInteger.valueOf(1));
//                        } else if ("nonce too high".equals(error.getMessage())){
//                            nonce[0] = nonce[0].subtract(BigInteger.valueOf(1));
//                        }
//                    } else {
                        // 没有错误，操作成功
                        String txHash = ethSendTransaction.getTransactionHash();
                        System.out.println("Transaction sent successfully. Transaction Hash: " + txHash);
                        System.out.println("Tnonce: " + nonce[0] );
                        nonce[0] = nonce[0].add(BigInteger.valueOf(1));
//                    }
                }).exceptionally(throwable -> {
                    // 处理异常情况
                    throwable.printStackTrace();
                    return null;
                });

//
//            nonce = nonce.add(BigInteger.valueOf(1));

        }
    }

}
