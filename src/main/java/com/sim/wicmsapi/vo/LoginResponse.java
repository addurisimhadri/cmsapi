package com.sim.wicmsapi.vo;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private String token;
	private long userId;

	public LoginResponse() {
	}
	
	public LoginResponse(String token) {
		this.token = token;
	}

	
	public LoginResponse(String token, long userId) {
		super();
		this.token = token;
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	

	
}