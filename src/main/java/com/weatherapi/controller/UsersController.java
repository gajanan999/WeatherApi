package com.weatherapi.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.config.Message;
import com.weatherapi.constants.Constants;
import com.weatherapi.entity.User;
import com.weatherapi.model.RestResponse;
import com.weatherapi.service.UserService;

/**
 * This is Rest controller class to Get,Update,Delete the user entity
 * @author Gajanan Gaikwad
 *
 */

@RestController
@RequestMapping(value = "/weatherapi/users")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	Message message;

	@GetMapping(value = "/")
	public ResponseEntity<?> getAllUsers(){
		try {
			List<User> users = userService.getAllUsers();
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.OK);
			respose.setMessage(message.getMessage(Constants.USERS_FETCHED_SUCCESSFULLY));
			respose.setData(users);
			return new ResponseEntity<>(respose, HttpStatus.OK);
		} catch (Exception e) {
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			respose.setMessage(e.getMessage());
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id){
		try {
			User user = userService.getUserById(id);
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.OK);
			respose.setMessage(message.getMessage(Constants.USER_FETCHED_SUCCESSFULLY));
			respose.setData(Arrays.asList(user));
			return new ResponseEntity<>(respose, HttpStatus.OK);
		} catch (Exception e) {
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			respose.setMessage(e.getMessage());
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id,@RequestBody User user){
		try {
			user.setId(id);
			User savedUser = userService.updateUser(user);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			respose.setMessage(e.getMessage());
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
		try {
			
			userService.deleteUser(id);
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.OK);
			respose.setMessage(message.getMessage(Constants.DELETED_USER));
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.OK);
		} catch (Exception e) {
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			respose.setMessage(e.getMessage());
			respose.setData(Collections.emptyList());
			return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
