package com.weatherapi.mapper;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weatherapi.entity.UserSearchHistory;
import com.weatherapi.entity.Weather;
import com.weatherapi.model.OpenWeatherApiResponse;
import com.weatherapi.model.OpenWeatherModel;
import com.weatherapi.repository.UserSearchHistoryRepository;
import com.weatherapi.repository.WeatherRepository;

@Component
public class OpenWeatherToUserSearchHistoryMapper {
	
	@Autowired
	WeatherRepository weatherRepository;
	
	@Autowired
    UserSearchHistoryRepository userSearchHistoryRepository;
	 
	public UserSearchHistory getUserSearchHistory(OpenWeatherApiResponse openWeatherApiResponse) {
		
		UserSearchHistory userSearchHistory = new UserSearchHistory();
		userSearchHistory.setCityName(openWeatherApiResponse.getName());
		userSearchHistory.setCurrentTemperature(openWeatherApiResponse.getMain().getTemp());
		userSearchHistory.setMinTemperature(openWeatherApiResponse.getMain().getTemp_min());
		userSearchHistory.setMaxTemperature(openWeatherApiResponse.getMain().getTemp_max());
		userSearchHistory.setSunrise(openWeatherApiResponse.getSys().getSunrise());
		userSearchHistory.setSunset(openWeatherApiResponse.getSys().getSunset());
		
		Set<Weather> list = new HashSet<>();
		for(OpenWeatherModel model : openWeatherApiResponse.getWeather()) {
			Weather weather = new Weather();
			weather.setMain(model.getMain());
			weather.setDescription(model.getDescription());
			weather.setUserSearchHistory(userSearchHistory);
			list.add(weather);
		}
		userSearchHistory.setWeather(list);
		
		return userSearchHistory;
	}

}
