package com.bit.common.informationEnum;

public enum ElevatorTypeEnum {
	/**
	 * 客梯
	 */
	ELEVATOR_TYPE_KT(1,"客梯"),
	/**
	 * 别墅梯
	 */
	ELEVATOR_TYPE_BST(2,"别墅梯"),
	/**
	 * 货梯
	 */
	ELEVATOR_TYPE_HT(3,"货梯"),
	/**
	 * 扶梯
	 */
	ELEVATOR_TYPE_FT(5,"扶梯");





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
	ElevatorTypeEnum(int code, String info) {
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
		for(ElevatorTypeEnum typeEnum : ElevatorTypeEnum.values()){
			if(code==typeEnum.getCode()){
				return typeEnum.getInfo();
			}
		}
		return  null;
	}
}
