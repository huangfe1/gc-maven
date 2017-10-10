package com.hfpay.util;

import com.thoughtworks.xstream.XStream;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by huangfei on 12/05/2017.
 * 微信支付需要用到的相关方法
 */
public class WxUtil {

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getNoncestr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }

    /**
     * 生成32位的随机字符串 唯一
     *
     * @return
     */
    public static String getNoncestr32() {
        return UUID.randomUUID().toString();
    }


    /**
     * 统一下单需要的签名
     *
     * @param object 参与签名的对象
     * @param secret 秘钥
     * @return
     */
    public static String getSign(Object o, String secret) throws IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        Class<?> o = object.getClass();
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> strings = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);//获取private
            if (field.get(o) != null && !field.get(o).equals("")) {
                String str = field.getName() + "=" + field.get(o);
                strings.add(str);
            }
            //字典排序
            Collections.sort(strings);
        }
        StringBuilder sb = new StringBuilder();
        //组装
        for (String str : strings) {
            sb.append(str).append("&");
        }
        String result = sb.toString() + "key=" + secret;
        result = Encrypt.Encode(result, Encrypt.MD5);//MD5 加密
        return result.toUpperCase();
    }


    /**
     * 获取
     *
     * @param map
     * @return
     */
    public static String getPaySign(Map<String, String> map, String secret) {
        StringBuilder sb = new StringBuilder();
        List<String> stringList = new ArrayList<>();
        map.forEach((key, value) -> stringList.add(key));
        Collections.sort(stringList);
        for (String key : stringList) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        sb.append("key=").append(secret);
        String result = Encrypt.Encode(sb.toString(), Encrypt.MD5);//MD5加密
        return result.toUpperCase();
    }


    /**
     * 将Object转换成xml
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static String getXml(Object o) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);//获取private
            if (field.get(o) != null && !field.get(o).equals("")) {
                sb.append("<").append(field.getName()).append(">");
                sb.append(field.get(o));
                sb.append("</").append(field.getName()).append(">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 从xml获取对象
     *
     * @param xml
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromXML(String xml, Class t) {
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", t);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return (T) xStreamForResponseData.fromXML(xml);
    }

    public static String md5Encode(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("Encrypt");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8"))).toUpperCase();
    }

}
