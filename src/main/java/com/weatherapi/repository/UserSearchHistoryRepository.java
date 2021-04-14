package com.weatherapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.weatherapi.entity.UserSearchHistory;

public interface UserSearchHistoryRepository extends JpaRepository<UserSearchHistory, Long>{

	@Query("select h from UserSearchHistory h join h.user u where u.id=?1")
	Page<UserSearchHistory> findByUserId(int id,Pageable pageable);
}
