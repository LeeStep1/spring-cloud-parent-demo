package com.bit.common.informationEnum;

public enum StageEnum {
	/**
	 * 常规
	 */
	STAGE_ONE(1,"常规"),

	/**
	 * 实施
	 */
	STAGE_ZERO(0,"实施");





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
	StageEnum(int code, String info) {
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
		for(StageEnum typeEnum : StageEnum.values()){
			if(code==typeEnum.getCode()){
				return typeEnum.getInfo();
			}
		}
		return  null;
	}
}
