package com.dreamer.domain.user.enums;

import java.io.Serializable;

public enum AgentStatus implements Enum ,Serializable{

	ACTIVE("激活",0),NO_ACTIVE("未激活",1),REORGANIZE("整顿",2);
	
	AgentStatus(String desc,Integer state){
		this.desc=desc;
		this.state=state;
	}	
	public String getDesc(){
		return desc;

	}

	public static AgentStatus stateOf(Integer state){
	   for(AgentStatus agentStatus :  values()){
	       if(agentStatus.getState().equals(state)){
	           return agentStatus;
           }
       }
       return null;
    }
	
	private String desc;

	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
