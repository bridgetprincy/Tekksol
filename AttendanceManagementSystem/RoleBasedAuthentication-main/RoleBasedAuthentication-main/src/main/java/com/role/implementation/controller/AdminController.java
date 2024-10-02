package com.role.implementation.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.LeaveRequest;
import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;
import com.role.implementation.service.DefaultUserService;
import com.role.implementation.service.LeaveService;



@Controller
//@RequestMapping("/adminDashboard")
public class AdminController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private DefaultUserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private LeaveService leaveService;
	
//	@GetMapping
//    public String displayDashboard(Model model){
//		String user= returnUsername();
//        model.addAttribute("userDetails", user);
//        return "adminDashboard";
//    }
	
	@GetMapping("/adminDashboard")
    public String showAdminDashboard() {
        return "adminDashboard";
    }

	
//	private String returnUsername() {
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
//		User users = userRepository.findByEmail(user.getUsername());
//		return users.getName();
//	}
	
	 @GetMapping("/admin-home")
	    public String showAdminHome(Model model) {
		 model.addAttribute("users", userService.getAllUser());
	        return "adminHome";
	    }
	 
	 @GetMapping("/users/edit/{id}")
		public String editStudentForm(@PathVariable Long id, Model model) {
			model.addAttribute("user", userService.getUserById(id));
			return "editUser";
		}

	 @PostMapping("/users/{id}")
	 public String updateUser(@PathVariable Long id,
	                          @ModelAttribute("user") UserRegisteredDTO userDto,
	                          Model model) {
	   
	     User existingUser = userService.getUserById(id);
	     
	     existingUser.setName(userDto.getName());
	     existingUser.setEmail(userDto.getEmail_id());
	 
	     if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
	         existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
	     }
	    
	     userService.updateUser(existingUser);
	     return "redirect:/admin-home";
	 }
	 
	 @GetMapping("/users/{id}")
		public String deleteUser(@PathVariable Long id) {
			userService.deleteUserById(id);
			return "redirect:/admin-home";
		}
	 
	 
	 //LeaveController
	 
	 @GetMapping("/view-leave-requests")
	 public String viewLeaveRequests(Model model) {
	     List<LeaveRequest> leaveRequests = leaveService.getPendingLeaveRequests();
	     leaveRequests.forEach(leaveRequest -> {
	         if (leaveRequest.getUser() == null) {
	             System.out.println("User is not initialized for leaveRequest ID: " + leaveRequest.getId());
	         } else {
	             System.out.println("User is initialized: " + leaveRequest.getUser().getName());
	         }
	     });
	     model.addAttribute("leaveRequests", leaveRequests);
	     return "viewLeaveRequests";
	 }

	    @PostMapping("/leave-requests/{id}/approve")
	    public String approveLeave(@PathVariable Long id) {
	        leaveService.updateLeaveStatus(id, "APPROVED");
	        return "redirect:/admin/view-leave-requests";
	    }

	    @PostMapping("/leave-requests/{id}/reject")
	    public String rejectLeave(@PathVariable Long id) {
	        leaveService.updateLeaveStatus(id, "REJECTED");
	        return "redirect:/admin/view-leave-requests";
	    }
}
