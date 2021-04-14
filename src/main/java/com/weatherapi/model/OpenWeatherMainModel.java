package com.weatherapi.model;

public class OpenWeatherMainModel {

	private Float temp;
	private Float temp_min;
	private Float temp_max;
	
	public Float getTemp() {
		return temp;
	}
	public void setTemp(Float temp) {
		this.temp = temp;
	}
	public Float getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(Float temp_min) {
		this.temp_min = temp_min;
	}
	public Float getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(Float temp_max) {
		this.temp_max = temp_max;
	}
	@Override
	public String toString() {
		return "OpenWeatherMainModel [temp=" + temp + ", temp_min=" + temp_min + ", temp_max=" + temp_max + "]";
	}
	
	
}
