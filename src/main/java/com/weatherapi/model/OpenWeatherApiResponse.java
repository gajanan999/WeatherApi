package com.weatherapi.model;

import java.util.List;

public class OpenWeatherApiResponse {

	
	private Integer timezone;
	private Long id;
	private String name;
	private Integer cod;
	private String base;
	List<OpenWeatherModel> weather;
	private OpenWeatherSysModel sys;
	private OpenWeatherMainModel main;
	private Long dt;
	
	
	
	//getters, setters
	public Integer getTimezone() {
		return timezone;
	}
	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCod() {
		return cod;
	}
	public void setCod(Integer cod) {
		this.cod = cod;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public List<OpenWeatherModel> getWeather() {
		return weather;
	}
	public void setWeather(List<OpenWeatherModel> weather) {
		this.weather = weather;
	}
	
	public OpenWeatherSysModel getSys() {
		return sys;
	}
	public void setSys(OpenWeatherSysModel sys) {
		this.sys = sys;
	}
	public OpenWeatherMainModel getMain() {
		return main;
	}
	public void setMain(OpenWeatherMainModel main) {
		this.main = main;
	}
	
	public Long getDt() {
		return dt;
	}
	public void setDt(Long dt) {
		this.dt = dt;
	}
	//toString()
	@Override
	public String toString() {
		return "OpenWeatherApiResponse [timezone=" + timezone + ", id=" + id + ", name=" + name + ", cod=" + cod
				+ ", base=" + base + ", weather=" + weather + ", sys=" + sys + ", main=" + main + "]";
	}
	
}
