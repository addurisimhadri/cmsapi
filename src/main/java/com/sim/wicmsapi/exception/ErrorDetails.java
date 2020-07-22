package com.sim.wicmsapi.exception;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorDetails {	
	private String message;
	private String details;
	private Date timestamp;

}
