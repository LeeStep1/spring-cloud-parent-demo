package com.bit.base.exception;

/**
 * @Description:
 * @Author: mifei
 * @Date: 2018-10-25
 **/
public class BusinessException extends RuntimeException {


	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message,int code) {
		super(message);
		this.code = code;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
