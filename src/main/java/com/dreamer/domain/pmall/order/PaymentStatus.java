package com.dreamer.domain.pmall.order;

public enum PaymentStatus {
	UNPAID("未支付"), PAID("已支付"),PAIDERROR("已收钱,异常"),REFUND("已退款");

	private String name;

	public String getName() {
		return name;
	}

	PaymentStatus(String name) {
		this.name = name;
	}
}
