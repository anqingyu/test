package com.xf.datasecurity;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA算法生成公私钥对、公钥加密、私钥解密
 */
public class RSAEncrypt {
    final static Base64.Decoder decoder = Base64.getDecoder();
    final static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * RSA公钥加密
     * @param str 加密字符串
     * @param publicKey 公钥
     * @throws Exception 加密过程中的异常信息
     * @return 密文
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = decoder.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = encoder.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     * @param str 加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //base64解码后的字符串
        byte[] inputByte = decoder.decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = decoder.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 生成密钥对
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        //可以理解为：加密后的密文长度，实际原文要小些 越大 加密解密越慢
        keyGen.initialize(512);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }

    public static Map<Integer, String> genKeyPair() throws Exception {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        //生成公私钥对
        KeyPair keyPair = getKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);
        //0表示公钥
        keyMap.put(1, privateKeyString);
        //1表示私钥
        return keyMap;
    }

    /**
     * 测试RSA加解密
     */
    @Test
    public void test1()throws Exception{
        Map<Integer, String> keyMap = RSAEncrypt.genKeyPair();
        String content = "传智播客";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = RSAEncrypt.encrypt(content, keyMap.get(0));
        System.out.println("加密后的字符串为:" + messageEn);
        String messageDe = RSAEncrypt.decrypt(messageEn, keyMap.get(1));
        System.out.println("解密后的字符串为:" + messageDe);
    }

    /********************** 使用SHA1作为摘要算法，使用RSA作为签名加密算法的签名及验签_start ***********************/
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * RSA签名
     * @param content 待签名数据
     * @param privateKey 私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decoder.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));
            byte[] signed = signature.sign();
            return encoder.encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param public_key 公钥
     * @param input_charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String public_key, String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = decoder.decode(public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));
            boolean bverify = signature.verify(decoder.decode(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 测试SHA1WithRSA签名、验证签名
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        // a方密钥对
        Map<Integer, String> akeyMap = RSAEncrypt.genKeyPair();
        System.out.println("随机生成的a方公钥为:" + akeyMap.get(0));
        System.out.println("随机生成的a方私钥为:" + akeyMap.get(1));
        String content = "传智播客";
        System.out.println("------------a向b发送数据，使用a的私钥生成签名-----------");
        String signature = RSAEncrypt.sign(content, akeyMap.get(1), "utf-8");
        System.out.println("原文:'" + content + "'生成签名：" + signature);
        System.out.println("----------b接收到a发的数据，使用a的公钥验证签名-----------");
        if (RSAEncrypt.verify(content, signature, akeyMap.get(0), "utf-8")) {
            System.out.println("验证签名成功：" + signature);
        } else {
            System.out.println("验证签名失败！");
        }
    }
    /********************** 使用SHA1作为摘要算法，使用RSA作为签名加密算法的签名及验签_end ***********************/
}
