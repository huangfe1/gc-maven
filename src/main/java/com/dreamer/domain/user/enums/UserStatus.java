package com.dreamer.domain.user.enums;


import java.io.Serializable;

public enum UserStatus implements Enum ,Serializable{


	NEW("新申请",0),NORMAL("正常",1),STOP("停用",2),LOCKED("锁定",3),DELETE("删除",4),TRANSFER("已转让",5);
	
	UserStatus(String desc,Integer index){
		this.desc=desc;
		this.index=index;
	}

	private static final long serialVersionUID = -2336150649364845388L;

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}
	
	private String desc;

	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
