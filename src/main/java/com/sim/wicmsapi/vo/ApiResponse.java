package com.sim.wicmsapi.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApiResponse<T> {
	
	private int status;
	private String message;
	private Object result;

	    public ApiResponse(int status, String message, Object result) {
	        this.status = status;
	        this.message = message;
	        this.result = result;
	    }

}
