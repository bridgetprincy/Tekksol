package com.role.implementation.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.role.implementation.model.LeaveRequest;
import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;
import com.role.implementation.service.LeaveService;

@Controller
@RequestMapping("/userPage")
public class DashboardController {
	
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private LeaveService leaveService;
	
	@GetMapping
    public String displayDashboard(Model model){
		String user= returnUsername();
        model.addAttribute("userDetails", user);
        return "userPage";
    }
	
	private String returnUsername() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
		User users = userRepository.findByEmail(user.getUsername());
		return users.getName();
	}
		
	
	@GetMapping("/user-details")
    public String userDetails(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();  
        User userDetails = userRepository.findByEmail(userEmail);
        model.addAttribute("user", userDetails);
      //  List<User> userDetails = userRepository.findAll();
        model.addAttribute("userDetails", userDetails);
        
        return "userDetails";
    }

    

    @GetMapping("/apply-leave")
    public String applyLeaveForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "applyLeaveForm";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@ModelAttribute LeaveRequest leaveRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        leaveService.applyLeave(user, leaveRequest);
        return "redirect:/userPage/view-leave-status";
    }

    @GetMapping("/view-leave-status")
    public String viewLeaveStatus(Principal principal, Model model) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("leaveRequests", leaveService.getLeaveRequestsByUser(user));
        return "viewLeaveStatus";
    }
}
