package com.telosws.broking.util;


/**
 * @author vvhegde
 *
 */
public class GenericResponse {

	private String status;
	private String msg;
	private Object responseObject;

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object user) {
		this.responseObject = user;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}