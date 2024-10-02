package com.role.implementation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.role.implementation.model.LeaveRequest;
import com.role.implementation.model.User;
import com.role.implementation.repository.LeaveRepository;


@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public LeaveRequest applyLeave(User user, LeaveRequest leaveRequest) {
        leaveRequest.setUser(user);
        leaveRequest.setStatus("PENDING");
        return leaveRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getLeaveRequestsByUser(User user) {
        return leaveRepository.findByUser(user);
    }

    @Transactional
    public List<LeaveRequest> getPendingLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRepository.findByStatus("PENDING");
        leaveRequests.forEach(leaveRequest -> {
            
            leaveRequest.getUser().getName(); 
        });
        return leaveRequests;
    }

    public LeaveRequest updateLeaveStatus(Long id, String status) {
        LeaveRequest leaveRequest = leaveRepository.findById(id).orElseThrow(() -> new RuntimeException("Leave not found"));
        leaveRequest.setStatus(status);
        return leaveRepository.save(leaveRequest);
    }
}