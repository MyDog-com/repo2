package com.xsw.logistics.model;

public class MsgObject {
	private Integer code;
	private String msg;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public MsgObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MsgObject(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "MsgObject [code=" + code + ", msg=" + msg + "]";
	}
	
}
