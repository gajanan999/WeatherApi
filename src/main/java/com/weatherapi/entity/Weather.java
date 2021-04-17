package com.weatherapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "weather", schema = "weatherapidb")
public class Weather {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long weatherId;
	
	@Column(name="main")
	private String main;
	
	@Column(name="description")
	private String description;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "searchId", referencedColumnName = "searchId")
	private UserSearchHistory userSearchHistory;

	public Long getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Long weatherId) {
		this.weatherId = weatherId;
	}

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

	public UserSearchHistory getUserSearchHistory() {
		return userSearchHistory;
	}

	public void setUserSearchHistory(UserSearchHistory userSearchHistory) {
		this.userSearchHistory = userSearchHistory;
	}
	
	
}
