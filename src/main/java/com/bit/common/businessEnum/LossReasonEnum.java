package com.bit.common.businessEnum;

/**
 * 项目流失原因
 */
public enum LossReasonEnum {

	PRICE_NOT_MATCH(1,"价格未达成一致"),

	NEED_NOT_MATCH(2,"无法满足客户定制要求"),

	CAN_NOT_REACH_CUSTOMER(3,"无法联系到客户"),

	CHOOSE_OTHER_BRAND(4,"客户选择其他品牌产品"),

	PROJECT_CHANGED_OR_CANCELED(5,"项目变动或取消"),

	OTHER_REASON(6,"其他原因");

	/**
	 * 操作码
	 */
	private int code;

	/**
	 * 操作信息
	 */
	private String info;

	/**
	 * @param code  设备状态
	 * @param info  描述
	 */
	LossReasonEnum(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}


	public String getInfo() {
		return info;
	}
}
