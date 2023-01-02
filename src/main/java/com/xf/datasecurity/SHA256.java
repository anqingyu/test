package com.xf.datasecurity;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256摘要算法：
 *      发送方和接收方可以约定使用相同的摘要算法对原文进行摘要运算，双方得出的消息摘要若一致，说明数据在传输过程中没有被篡改。
 */
public class SHA256 {
    /**
     * 实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    // 把字节数组转换成16进制字符串
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 测试SHA256摘要算法
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        String content = "传智播客";
        System.out.println(content + ": 第一次摘要后的字符串为:" + SHA256.getSHA256(content));
        System.out.println(content + ": 第二次摘要后的字符串为:" + SHA256.getSHA256(content));
    }
}
