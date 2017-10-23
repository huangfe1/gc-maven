package com.dreamer.domain.user;

public enum AgentLevelName {

//    分公司,业务员,药店;
    分公司,业务员;

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
