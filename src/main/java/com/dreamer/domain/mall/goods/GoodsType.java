package com.dreamer.domain.mall.goods;

import com.dreamer.domain.user.enums.Enum;
public enum  GoodsType implements Enum{
	MALL("代理产品"),TEQ("特权产品");

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}
	
	private String desc;
	
	GoodsType(String desc){
		this.desc=desc;
	}

}
