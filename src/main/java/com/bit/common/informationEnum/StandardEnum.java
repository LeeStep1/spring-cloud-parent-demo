package com.bit.common.informationEnum;

public enum StandardEnum {
	/**
	 * 标准
	 */
	STANDARD_ONE(1,"标准"),

	/**
	 * 默认值,当版本报价下为order为空时，projectPrice表中的的值
	 */
	STANDARD_DEFAULT(-1,"正常"),


	/**
	 * 非标
	 */
	//NON_STANDARD(0,"非标");


	/**
	 * 非标
	 */
	STANDARD_ZERO(0,"非标");





	/**
	 * 操作码
	 */
	private int code;

	/**
	 * 操作信息
	 */
	private String info;

	/**
	 * @param code  状态码
	 * @param info  状态信息
	 */
	StandardEnum(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}


	public String getInfo() {
		return info;
	}

	/**
	 * 根据code得到info
	 * @param code
	 * @return
	 */
	public static String getValueByCode(int code){
		for(StandardEnum typeEnum : StandardEnum.values()){
			if(code==typeEnum.getCode()){
				return typeEnum.getInfo();
			}
		}
		return  null;
	}
}
