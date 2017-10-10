package com.dreamer.domain.mall.goods;

import com.dreamer.domain.user.enums.Enum;

public enum GoodsTransferStatus implements Enum{

	NEW("新申请"),BACK("退货待处理"),CONFIRM("已完成/已成交"),DISAGREE("不同意");
	
	GoodsTransferStatus(String desc){
		this.desc=desc;
	}	
	public String getDesc(){
		return desc;
	}
	
	private String desc;
}
