package com.weatherapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.constant.TestConstants;
import com.weatherapi.security.JwtUtils;

@SpringBootTest
//@WebAppConfiguration
@AutoConfigureMockMvc
public class AbstractControllerTest {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtTokenUtil;

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	protected String getJwtToken() {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(TestConstants.ADMIN_PASSWORD, TestConstants.ADMIN_PASSWORD));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		final String jwt = jwtTokenUtil.generateJwtToken(authenticate);
		return jwt;
	}
}
