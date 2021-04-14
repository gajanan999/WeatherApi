package com.weatherapi.model;

import java.util.List;

public class DeleteUserSearchHistoryRequest {

	List<Long> searchIds;

	public List<Long> getSearchIds() {
		return searchIds;
	}

	public void setSearchIds(List<Long> searchIds) {
		this.searchIds = searchIds;
	}
	
}
