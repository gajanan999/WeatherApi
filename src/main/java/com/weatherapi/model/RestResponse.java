package com.weatherapi.model;

import java.util.List;

import org.springframework.http.HttpStatus;

public class RestResponse {

	private HttpStatus code;
	private String message;
	List<?> data;
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
	
	
}
