package com.dreamer.domain.user;

public enum AgentLevelName {

    钻石大咖,铂金大咖,黄金大咖,品牌代言;

    public static boolean contains(String name){
        for(AgentLevelName s : values()){
            if(s.toString().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(AgentLevelName.contains("分2公司"));
    }
}
