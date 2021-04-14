package com.weatherapi.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_search_history")
public class UserSearchHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long searchId;
	
	@Column(name="city_name")
	private String cityName;
	
	@Column(name="current_temperature",precision = 4,scale=2)
	private Float currentTemperature;
	
	@Column(name="min_temperature",precision = 4,scale=2)
	private Float minTemperature;
	
	@Column(name="max_temperature",precision = 4,scale=2)
	private Float maxTemperature;
	
	@Column(name="sunrise")
	private Long sunrise;

	@Column(name="sunset")
	private Long sunset;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="userSearchHistory", cascade = CascadeType.ALL)
	Set<Weather> weather=new HashSet<>();	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id")
	private User user;

	public long getSearchId() {
		return searchId;
	}

	public void setSearchId(long searchId) {
		this.searchId = searchId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Float getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(Float currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public Float getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Float minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Float getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Float maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public Long getSunrise() {
		return sunrise;
	}

	public void setSunrise(Long sunrise) {
		this.sunrise = sunrise;
	}

	public Long getSunset() {
		return sunset;
	}

	public void setSunset(Long sunset) {
		this.sunset = sunset;
	}

	public Set<Weather> getWeather() {
		return weather;
	}

	public void setWeather(Set<Weather> weather) {
		this.weather = weather;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
