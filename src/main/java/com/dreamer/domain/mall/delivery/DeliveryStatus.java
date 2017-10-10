package com.dreamer.domain.mall.delivery;

import com.dreamer.domain.user.enums.Enum;

public enum DeliveryStatus implements Enum{

	NEW("新申请"),CONFIRM("已确认"),DELIVERY("已发货"),DISAGREE("不同意"),DELIVERING("配货中");
	
	DeliveryStatus(String desc){
		this.desc=desc;
	}	
	public String getDesc(){
		return desc;
	}
	
	private String desc;
}
