package com.weatherapi.controller;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.weatherapi.config.Message;
import com.weatherapi.constants.Constants;
import com.weatherapi.entity.UserSearchHistory;
import com.weatherapi.model.RestResponse;
import com.weatherapi.security.JwtUtils;
import com.weatherapi.service.UserSearchHistoryService;

/**
 * This Rest Controller class is used to Get Weather Details by City name
 * @author Gajanan Gaikwad
 *
 */
@RestController
@RequestMapping(value = "/weatherapi")
@CrossOrigin
public class SearchController {

	private static Logger logger =  LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	UserSearchHistoryService userSearchHistoryService;
	
	@Autowired
	private JwtUtils jwtTokenUtil;
	
	@Autowired
	Message message;
	
	/**
	 * This method is used to find the weather details by city name using rest request
	 * @param request
	 * @param cityName
	 * @return UserSearchHistory
	 */
	@GetMapping(value = "/search/{cityName}")
	public ResponseEntity<?> getWeatherDetailsByCityName(HttpServletRequest request, @PathVariable String cityName){
		try {
			String username = jwtTokenUtil.getUsernameFromHttpRequest(request);
			if(null == username)
				throw new Exception(Constants.INVALID_USER);
			UserSearchHistory userSearchHistory = userSearchHistoryService.getWeatherDetails(username,cityName);
		    RestResponse respose = new RestResponse(); respose.setCode(HttpStatus.OK);
		    respose.setMessage(message.getMessage("WEATHER_DETAILS_SUCCESS"));
		    respose.setData(Arrays.asList(userSearchHistory));
			 
			return new ResponseEntity<>(respose, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			if( e instanceof WebClientResponseException) {
				if(((WebClientResponseException)e).getStatusCode() == HttpStatus.NOT_FOUND) {
					respose.setMessage(message.getMessage(Constants.CITY_NAME_IS_NOT_VALID));
				}else {
					respose.setMessage(e.getMessage());
				}
			}else {
				respose.setMessage(e.getMessage());
			}
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
