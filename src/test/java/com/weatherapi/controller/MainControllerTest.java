package com.weatherapi.controller;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.weatherapi.entity.User;
import com.weatherapi.model.AuthenticationRequest;
import com.weatherapi.model.AuthenticationResponse;

public class MainControllerTest extends AbstractControllerTest{

	@Test
	public void loginTest() throws Exception {
		String uri = "/login";
		AuthenticationRequest request = new AuthenticationRequest();
		request.setUsername("admin");
		request.setPassword("admin");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
								 .contentType(MediaType.APPLICATION_JSON_VALUE)
								 .content(inputJson))
				                 .andReturn();
		int status = mvcResult.getResponse().getStatus();
		//String content = mvcResult.getResponse().getContentAsString();
		//AuthenticationResponse response = super.mapFromJson(content, AuthenticationResponse.class);
		assertEquals(200, status);
	}
	
	@Test
	public void signUpTest() throws Exception {
		String uri = "/signup";
		User request = new User();
		request.setUsername("user1");
		request.setPassword("user1");
		request.setActive(true);
		request.setBirthdate(Date.valueOf("1994-09-29"));
		request.setRoles("ROLE_USER");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
								 .contentType(MediaType.APPLICATION_JSON_VALUE)
								 .content(inputJson))
				                 .andReturn();
		int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
}
