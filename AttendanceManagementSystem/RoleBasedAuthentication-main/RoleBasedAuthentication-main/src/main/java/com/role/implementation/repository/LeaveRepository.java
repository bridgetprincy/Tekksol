package com.role.implementation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.role.implementation.model.LeaveRequest;
import com.role.implementation.model.User;


public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

	 List<LeaveRequest> findByUser(User user);
	    List<LeaveRequest> findByStatus(String status);
		
}
