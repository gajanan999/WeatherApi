package com.weatherapi.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.config.Message;
import com.weatherapi.constants.Constants;
import com.weatherapi.entity.UserSearchHistory;
import com.weatherapi.model.DeleteUserSearchHistoryRequest;
import com.weatherapi.model.RestResponse;
import com.weatherapi.model.UserSearchHistoryResponse;
import com.weatherapi.security.JwtUtils;
import com.weatherapi.service.UserSearchHistoryService;

@RestController
@RequestMapping(value = "/weatherapi")
@CrossOrigin
public class SearchHistoryController {
	
	private static Logger logger =  LoggerFactory.getLogger(SearchHistoryController.class);
	
	@Autowired
	private JwtUtils jwtTokenUtil;
	
	@Autowired
	Message message;
	
	@Autowired
	UserSearchHistoryService userSearchHistoryService;

	@PutMapping(value = "/user-search-history")
	public ResponseEntity<?> updateWeatherDetails(HttpServletRequest request, @RequestBody UserSearchHistory userSearchHistory){
		try {
			String username = jwtTokenUtil.getUsernameFromHttpRequest(request);
			if(null == username)
				throw new Exception("Invalid User");
			userSearchHistory = userSearchHistoryService.updateWeatherDetails(username,userSearchHistory);
		    RestResponse response = new RestResponse(); response.setCode(HttpStatus.OK);
		    response.setMessage("updated and fetched");
		    response.setData(Arrays.asList(userSearchHistory));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			RestResponse response = new RestResponse();
			response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setData(Collections.emptyList());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/user-search-history")
	public ResponseEntity<?> getUserSearchHisotry(HttpServletRequest request, @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size){
		try {
			String username = jwtTokenUtil.getUsernameFromHttpRequest(request);
			if(null == username)
				throw new Exception("Invalid User");
			List<UserSearchHistory> userSearchHistory = userSearchHistoryService.getUserSearchHisotry(username, page,size);
			UserSearchHistoryResponse response = new UserSearchHistoryResponse();
			
		    //RestResponse respose = new RestResponse(); 
			response.setCode(HttpStatus.OK);
			response.setLength(userSearchHistoryService.getUserSerchHistoryCount(username));
			response.setPageIndex(page);
			response.setPageSize(size);
			response.setMessage(message.getMessage(Constants.USER_SEARCH_HISTORY_FETCHED_SUCCESSFULLY));
			response.setData(userSearchHistory);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			RestResponse response = new RestResponse();
			response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setData(Collections.emptyList());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/user-search-history/delete")
	public ResponseEntity<?> DeleteUserSearchHisotry(HttpServletRequest request, @RequestBody DeleteUserSearchHistoryRequest deleteUserSearchHistoryRequest){
		try {
			String username = jwtTokenUtil.getUsernameFromHttpRequest(request);
			if(null == username)
				throw new Exception("Invalid User");
			boolean flag  = userSearchHistoryService.deleteUserSearchHisotry(username, deleteUserSearchHistoryRequest);
		    RestResponse respose = new RestResponse(); respose.setCode(HttpStatus.OK);
		    respose.setMessage(message.getMessage(Constants.DELETED_USER_SEARCH_HISTORY_RECORD));
		    respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			respose.setMessage(e.getMessage());
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
