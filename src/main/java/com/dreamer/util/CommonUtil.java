package com.dreamer.util;

import com.dreamer.domain.user.Agent;
import com.wxjssdk.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import ps.mx.otter.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by huangfei on 03/07/2017.
 */
public class CommonUtil {

    public static Object getFirst(List list) {
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    //创建单号
    public static String createNo() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //创建单号
    public static String createNoByTime() {
        Date date = new Date();
        String str = DateUtil.formatDate(date,"yyyyMMddHHmmssSSS");
        Random random = new Random();
        for(int i = 0;i<5;i++){
            str+= random.nextInt(10);
        }
//        System.out.println(str.length());
        return str;
    }

    public static void main(String[] args) {
//        System.out.println(createNoByTime());
        String str = createNoByTime()+"_zmz123456";
        System.out.println(str.substring(str.indexOf("_")+1,str.length()));
        Double v = 9.9;

    }

    //创建文件名
    public static String generateFileName(String imgType) {
        String s = UUID.randomUUID().toString();
        StringBuilder sbd = new StringBuilder();
        sbd.append(s.substring(0, 8)).append(s.substring(9, 13)).append(s.substring(14, 18)).append(s.substring(19, 23)).append(s.substring(24)).append(imgType);
        return sbd.toString();
    }

    //将一个map 加入另外一个map
    public static void putAll(Map<Agent,Double> mapAll, Map<Agent,Double> mapTemp){
        for(Agent key : mapTemp.keySet() ){
            Double nowTem = mapTemp.get(key);
            Double sum;
            if(mapAll.containsKey(key)){//包含
                Double tem = mapAll.get(key);
                sum = PreciseComputeUtil.add(tem,nowTem);
            }else {//不包含
                sum =nowTem;
            }
            mapAll.put(key,sum);
        }
    }



    public static String saveFile(MultipartFile file,String path) {
        String imgType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String fileName = generateFileName(imgType);
        try {
            writeImgFile(file.getBytes(), fileName,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }




    //写入文件
    public static void writeImgFile(byte[] imgBytes, String fileName,String pathStr) {
        try {
            Path path = Paths.get(pathStr);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path imgFile = path.resolve(fileName);
            if (!Files.exists(imgFile)) {
                Files.createFile(imgFile);
            }
            Files.write(imgFile, imgBytes, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException iop) {
            throw new ApplicationException(iop);
        }
    }


}
