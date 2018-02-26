package com.dreamer.domain.pmall.order;

public enum PaymentWay {


	WX("微信支付",0),VOUCHER("奖金",1),ZFB("支付宝",2),YL("银联",3),ADVANCE("预存款",4),COMPANY("公司确认",5),COD("货到付款",6),ADMIN("管理员确认",7),PAY_BY_OTHER("代付",8);

    private Integer type;

    private String name;


	public String getName() {
		return name;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	PaymentWay(String name,Integer type){
        this.name=name;
        this.type=type;
	}
}
