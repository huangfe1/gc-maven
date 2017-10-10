package com.wxjssdk.util;

import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by huangfei on 20/06/2017.
 */
public class XmlUtil {

    public static <T> T getObjectFromXML(String xml, Class t) {
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", t);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return (T)xStreamForResponseData.fromXML(xml);
    }

    public static Map<String, String> xmlToMap(String xml){
        Map<String, String> map = new HashMap<>();
        xmlToMap(map,xml);
        return map;
    }


    public static JSONObject xmlToJson(String xml){
        JSONObject jsonObject = new JSONObject();
        xmlToMap(jsonObject,xml);
        return jsonObject;
    }


    private static void xmlToMap(Map map,String xml){
        SAXReader reader = new SAXReader();
        try {
            InputStream ins =  new ByteArrayInputStream(xml.getBytes());

            Document doc = reader.read(ins);
            Element root = doc.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *map to xml
     * @param map
     * @return
     */
    public static String mapToXml(Map map){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXml(map,sb);
        sb.append("</xml>");
        return sb.toString();
    }

    private static void mapToXml(Map<String,Object> map,StringBuffer sb){

        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value) value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXml(hm, sb);
                }
                sb.append("</" + key + ">");
            } else {

                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXml((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + ">" + value + "</" + key + ">");
                }

            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Date().toString());
        Map<String,Object> map = new HashedMap();
        map.put("sb","sb");

        Map<String,Object> map1 = new HashedMap();
        map1.put("name","asd");
        map1.put("data",map);
        JSONObject jsonObject = JSONObject.fromObject(map1);
        System.out.println(jsonObject.toString());
//        String xml = "   <xml>\n" +
//                "           <ToUserName><![CDATA[gh_7f083739789a]]></ToUserName>\n" +
//                "           <FromUserName><![CDATA[oia2TjuEGTNoeX76QEjQNrcURxG8]]></FromUserName>\n" +
//                "           <CreateTime>1395658984</CreateTime>\n" +
//                "           <MsgType><![CDATA[event]]></MsgType>\n" +
//                "           <Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>\n" +
//                "           <MsgID>200163840</MsgID>\n" +
//                "           <Status><![CDATA[failed: system failed]]></Status>\n" +
//                "           </xml>";
//        System.out.println( XmlUtil.xmlToJson(xml));
    }

}
