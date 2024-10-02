package com.role.implementation.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.User;


public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);

	List<User> getAllUser();
	
	User getUserById(Long id);
	
	User updateUser(User user);
	
	void deleteUserById(Long id);
}
