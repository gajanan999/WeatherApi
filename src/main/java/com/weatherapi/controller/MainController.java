package com.weatherapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.entity.User;
import com.weatherapi.model.AuthenticationRequest;
import com.weatherapi.model.AuthenticationResponse;
import com.weatherapi.model.MyUserDetails;
import com.weatherapi.model.RestResponse;
import com.weatherapi.security.JwtUtils;
import com.weatherapi.service.UserService;

/**
 * This Rest Controller class is used to authenticate and signup the user 
 * @author Gajanan Gaikwad
 *
 */
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class MainController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtTokenUtil;
	@Autowired
	private UserService userService;

	/**
	 * This API is used to login the uses using credentials and generate a JWT token
	 * @param authenticationRequest
	 * @return authenticationResponse
	 * @throws Exception
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
	HttpHeaders responseHeaders = new HttpHeaders();
		
	Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
		);
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		//if authentication was successful else throw an exception
		final MyUserDetails userDetails = (MyUserDetails) userService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateJwtToken(authenticate);
		AuthenticationResponse response = new AuthenticationResponse(jwt);

		response.setId(userDetails.getId());
		response.setUsername(userDetails.getUsername());
		List<String> roles = new ArrayList<String>();
		
		userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
		response.setRoles(roles);

		return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	}
	

	/**
	 * This method is used to signup the user 
	 * @param user
	 * @return RestResponse
	 */
	@PostMapping(value = "/signup")
	public ResponseEntity<?> createuser(@RequestBody User user){
		try {
			User savedUser = userService.createUser(user);
			RestResponse respose = new RestResponse();
			respose.setCode(HttpStatus.OK);
			respose.setMessage("User Created!");
			respose.setData(Arrays.asList(savedUser));
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
