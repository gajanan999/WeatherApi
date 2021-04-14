package com.weatherapi.model;

import javax.persistence.Column;

public class OpenWeatherModel {

	
	private String main;
	
	private String description;

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "OpenWeatherModel [main=" + main + ", description=" + description + "]";
	}
	
	
}
