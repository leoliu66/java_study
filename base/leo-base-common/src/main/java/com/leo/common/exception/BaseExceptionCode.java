package com.leo.common.exception;


/**
 * 
 * @author leo
 * @date 2020-09-11
 */
public enum BaseExceptionCode {

	PARAM_INVALID("601", "参数非法"),
	PARAM_BIND_ERROR("602", "参数绑定错误:{0}"),
	ILLEGAL("603", "非法请求"),
	UNKNOWN("699", "未知错误"),
	;

	private  String code;
	private  String msg;

	BaseExceptionCode(String code, String msg){
		this.code=code;
		this.msg=msg;
	}

	public String getCode(){
		return code;
	}

	public String getMsg(){
		return msg;
	}
}
