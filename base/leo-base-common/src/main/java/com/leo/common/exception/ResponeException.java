package com.leo.common.exception;

import com.leo.common.constant.BaseConstants;
import org.slf4j.MDC;

import java.text.MessageFormat;

/**
 * 逻辑异常通用返回，一般不使用，异常成本高，适合层级太深时候直接抛出
 * @author huangdegnhui
 *
 */
public class ResponeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 响应码
	 */
	private String responeCode;
	/**
	 * 响应信息
	 */
	private String responeMsg;

	/**
	 * requestId
	 */
	private String requestId;
	
	public ResponeException(String responeCode) {
		super();
		this.responeCode = responeCode;
		this.requestId = MDC.get(BaseConstants.LOG_TRACE_ID);
	}

	public ResponeException(String responeCode, String responeMsg) {
		super(responeMsg);
		this.responeCode = responeCode;
		this.responeMsg = responeMsg;
		this.requestId = MDC.get(BaseConstants.LOG_TRACE_ID);
	}

	public ResponeException(String responeCode, String responeMsg, Object... params) {
		super();
		this.requestId = MDC.get(BaseConstants.LOG_TRACE_ID);
		this.responeCode = responeCode;
		if (null != params) {
			MessageFormat mf = new MessageFormat(responeMsg);
			this.responeMsg = mf.format(params);
		} else {
			this.responeMsg = responeMsg;
		}
	}

	public String getResponeCode() {
		return responeCode;
	}

	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
