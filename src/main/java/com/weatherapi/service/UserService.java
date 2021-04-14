package com.weatherapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.weatherapi.entity.User;
import com.weatherapi.exception.ResourceNotFoundException;
import com.weatherapi.mapper.UserToMyUserDetailsMapper;
import com.weatherapi.model.MyUserDetails;
import com.weatherapi.repository.UserRepository;

@Service
public class UserService  implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	

	@Autowired
	UserToMyUserDetailsMapper userToMyUserDetailsMapper;

	/**
	 * This method is used to load a user from DB using username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> users = userRepository.findByUsername(username);
		MyUserDetails userDetails = null;
		if(null == users || users.size()==0) {
			throw new UsernameNotFoundException("Not found: " + username);
		}
		for(User user:users) {
			if(username.equals(user.getUsername())){
				userDetails = new MyUserDetails(user);
			}
		}
		return userDetails;
	}
	
	/**
	 * This Method is used to create a User entity into database using UserRepository interface
	 * @param user
	 * @return user
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
		
		List<User> usersExists = userRepository.findByUsername(user.getUsername());
		if(null != usersExists && usersExists.size()>0) {
			throw new Exception("User already Exists");
		}else {
			user.setId(0);
			user = userRepository.save(user);
			return user;
		}
	}

	/**
	 * This Method is used to update a User entity into database using UserRepository interface
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User updateUser(User user)  throws Exception{
		
		Optional< User > userdb = userRepository.findById(user.getId());
		if(userdb.isPresent()) {
			User modUser = userdb.get();
			modUser.setUsername(user.getUsername());
			modUser.setPassword(user.getPassword());
			modUser.setActive(user.isActive());
			modUser.setRoles(user.getRoles());
			return userRepository.save(modUser);
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + user.getId());
		}
	}

	/**
	 * This Method is used to delete the User entity from database using UserRepository interface
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(Integer id) throws Exception{
		
		Optional< User > userdb = userRepository.findById(id);
		if(userdb.isPresent()) {
			userRepository.delete(userdb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
	}

	/**
	 * This method is used to fetch all the users from the database
	 * @return List<User>
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * This method is used to fetch the user which has id = givenid
	 * here givenid = parameter passed to method argument
	 * @param id
	 * @return User
	 */
	public User getUserById(Integer id) {
		Optional< User > userdb = userRepository.findById(id);
		if(userdb.isPresent()) {
			return userdb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
	}
}
