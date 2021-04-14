package com.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weatherapi.entity.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long>{

}
