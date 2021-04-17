package com.weatherapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.weatherapi.constant.TestConstants;
import com.weatherapi.model.RestResponse;


public class SearchControllerTest extends AbstractControllerTest{
	
	@Test
	public void getWeatherDetailsByCityNameTest() throws Exception {
		String jwt = super.getJwtToken();
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(TestConstants.GET_WEATHER_DETAILS_BY_CITY_NAME).header(TestConstants.AUTHORIZATION, "Bearer "+jwt)
								 .contentType(MediaType.APPLICATION_JSON_VALUE)
								 .accept(MediaType.APPLICATION_JSON))
				                 .andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		RestResponse response = super.mapFromJson(content, RestResponse.class);
		assertEquals(HttpStatus.OK, response.getCode());
	}
}
