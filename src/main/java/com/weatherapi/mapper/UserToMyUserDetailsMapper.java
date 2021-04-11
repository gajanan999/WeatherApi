package com.weatherapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.weatherapi.entity.User;
import com.weatherapi.model.MyUserDetails;

@Component
public class UserToMyUserDetailsMapper {

	public User getUser(MyUserDetails userDetails) {
		User user = new User();
		user.setId(0);
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		user.setActive(userDetails.isEnabled());
		List<String> roles = new ArrayList<>();
		for(GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.add(authority.getAuthority());
		}
		
		String[] rol = roles.toArray(new String[0]);
		user.setRoles(String.join(",",rol));
		
		
		return user;
	}

}
