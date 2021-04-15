package com.weatherapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.weatherapi.constants.Constants;
import com.weatherapi.entity.User;
import com.weatherapi.entity.UserSearchHistory;
import com.weatherapi.entity.Weather;
import com.weatherapi.exception.ResourceUpdateNotAllowedException;
import com.weatherapi.mapper.OpenWeatherToUserSearchHistoryMapper;
import com.weatherapi.model.DeleteUserSearchHistoryRequest;
import com.weatherapi.model.OpenWeatherApiResponse;
import com.weatherapi.repository.UserRepository;
import com.weatherapi.repository.UserSearchHistoryRepository;
import com.weatherapi.repository.WeatherRepository;

@Service
public class UserSearchHistoryService {
	
	private static Logger logger =  LoggerFactory.getLogger(UserSearchHistoryService.class);
	
	@Value("${OPEN_API_BASE_URL}")
	String OPEN_API_BASE_URL;
	
	@Value("${AND_APP_ID}")
	String AND_APP_ID;
	
	@Value("${API_KEY}")
	String API_KEY;
	
	@Value("${UNITS}")
	String UNITS;
	
	
    @Autowired
    WebClient.Builder webClientBuilder;
    
    @Autowired
    OpenWeatherToUserSearchHistoryMapper openWeatherToUserSearchHistoryMapper;
    
    @Autowired
    UserSearchHistoryRepository userSearchHistoryRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    WeatherRepository weatherRepository;

    /**
     * This method is used to call a Open Weather API and get the result, store it and then give a response back to frontend
     * @param username
     * @param cityName
     * @return
     */
    @Transactional
	public UserSearchHistory getWeatherDetails(String username, String cityName) {
		
		StringBuilder url = new StringBuilder();
		url.append(OPEN_API_BASE_URL);
		url.append(cityName);
		url.append(AND_APP_ID);
		url.append(API_KEY);
		url.append(UNITS);
		
		OpenWeatherApiResponse openWeatherApiResponse = webClientBuilder
        		.build()
        		.get()
        		.uri(url.toString())
        		.retrieve()
        		.bodyToMono(OpenWeatherApiResponse.class)
        		.block();
		
		logger.info(openWeatherApiResponse.toString());
		
		UserSearchHistory userSearchHistory  = openWeatherToUserSearchHistoryMapper.getUserSearchHistory(openWeatherApiResponse);
		
		   List<User> u = userRepository.findByUsername(username); 
		   if(null != u && u.size()>0) { 
			   userSearchHistory.setUser(u.get(0)); 
		   }
		 		
		userSearchHistory = userSearchHistoryRepository.save(userSearchHistory);
		return userSearchHistory;
	}

    /**
     * This method used for update of User search history
     * @param username
     * @param userSearchHistory
     * @return userSearchHistory
     */
	public UserSearchHistory updateWeatherDetails(String username, UserSearchHistory userSearchHistory) {
		List< User > userDb = userRepository.findByUsername(username);
		Optional < UserSearchHistory > userSearchHistoryDb = userSearchHistoryRepository.findById(userSearchHistory.getSearchId());
		boolean flag = false;
		if((null != userDb && userDb.size()>0) && userSearchHistoryDb.isPresent()) {
			User user = userDb.get(0);
			User recordOwner = userSearchHistoryDb.get().getUser();
			if(user.getRoles().contains(Constants.ROLE_ADMIN) || recordOwner.getUsername().equals(username)) {
				for(Weather weather: userSearchHistory.getWeather()) {
					weather.setUserSearchHistory(userSearchHistory);
				}
				userSearchHistory.setUser(recordOwner);
				userSearchHistory = userSearchHistoryRepository.save(userSearchHistory);
				flag = true;
			}
		}
		
		if(!flag)
			throw new ResourceUpdateNotAllowedException("You are not allowed to update this record");

		return userSearchHistory;
	}

	public List<UserSearchHistory> getUserSearchHisotry(String username, int page, int size) {
		List< User > userDb = userRepository.findByUsername(username);
		List<UserSearchHistory> userSearchHistoryList = new ArrayList<>();
		if(null != userDb && userDb.size()>0){
			User user = userDb.get(0);
			if(user.getRoles().contains(Constants.ROLE_ADMIN)){
				PageRequest pageRequest = PageRequest.of(page, size);
				Page<UserSearchHistory> pageResult = userSearchHistoryRepository.findAll(pageRequest);
				userSearchHistoryList = pageResult
					      .stream()
					      .collect(Collectors.toList());
			}else {
				PageRequest pageRequest = PageRequest.of(page, size);
				Page<UserSearchHistory> pageResult = userSearchHistoryRepository.findByUserId(user.getId(),pageRequest);
				userSearchHistoryList = pageResult
					      .stream()
					      .collect(Collectors.toList());
			}
		}
		
		return userSearchHistoryList;
	}

	public boolean deleteUserSearchHisotry(String username, DeleteUserSearchHistoryRequest deleteUserSearchHistoryRequest) {
		boolean deleted = false;
		if(null != deleteUserSearchHistoryRequest && null != deleteUserSearchHistoryRequest.getSearchIds() && deleteUserSearchHistoryRequest.getSearchIds().size()>0) {
			for(Long id: deleteUserSearchHistoryRequest.getSearchIds() ) {
				userSearchHistoryRepository.deleteById(id);
			}
			deleted =true;
		}
		
		return deleted;
	}

	public Long getUserSerchHistoryCount(String username) {
		List< User > userDb = userRepository.findByUsername(username);
		if(null != userDb && userDb.size()>0){
			User user = userDb.get(0);
			if(user.getRoles().contains(Constants.ROLE_ADMIN)){
				return Long.valueOf(userSearchHistoryRepository.count());
			}else {
				Long count = userSearchHistoryRepository.findCountByUserId(user.getId());
				return count;
			}
		}
		return 0L;
	}

}
