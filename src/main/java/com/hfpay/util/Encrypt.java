package com.hfpay.util;

import java.security.MessageDigest;
import java.util.Formatter;

public class Encrypt {

    public final static String MD5 = "MD5";

    public final static String SHA1 = "SHA-1";

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    /**
     *
     * @param origin 原始编码
     * @param name 加密编码名称 MD5 SHA1
     * @return
     */
    public static String Encode(String origin,String name) {
        String resultString = null;
        try {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            MessageDigest crypt = MessageDigest.getInstance(name);
            crypt.reset();
            crypt.update(origin.getBytes("UTF-8"));
            resultString = byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

}
