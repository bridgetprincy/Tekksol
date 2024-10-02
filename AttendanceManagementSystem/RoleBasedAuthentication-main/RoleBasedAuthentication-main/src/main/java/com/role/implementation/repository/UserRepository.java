package com.role.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String emailId);
	
	User save(UserRegisteredDTO registrationDto);
}
