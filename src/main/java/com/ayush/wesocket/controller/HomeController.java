package com.ayush.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ayush.websocket.model.UserDtls;
import com.ayush.websocket.repository.UserRepository;
import com.ayush.websocket.service.UserService;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@ModelAttribute
	private void userDetails(Model m,Principal p) {
		if(p!=null) { //avoid null exception
			String email = p.getName();
			UserDtls user = userRepo.findByEmail(email);
			m.addAttribute("user",user);
		}
	}
	
	@GetMapping("/")
    public String redirectToSignin() {
        return "redirect:/signin";
    }
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "Login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/createUser")
	public String createuser(@ModelAttribute UserDtls user,HttpSession session,HttpServletRequest request) {
		System.out.println(user);
		
		String url = request.getRequestURL().toString();
		//http://localhost:8080/createUser
		
		url=url.replace(request.getServletPath(),"");
		//http://localhost:8080/
		
		boolean r = userService.checkregNo(user.getRegNo());
		
		if(r) {
			session.setAttribute("msg", "Registration Number Already Exists");
		}
		else {
			UserDtls userDtls= userService.createUser(user,url);
			if(userDtls!= null) {
				session.setAttribute("msg", "Registered Successfully");
			}else {
				session.setAttribute("msg", "ERROR");
			}
		}
		return "redirect:/register";
	}
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code) {
		if(userService.verifyAccount(code)) {
			return "verify_success";
		}
		else {
			return "failed";
		}
	}
	
	
	@GetMapping("/loadForgotPass")
	public String loadForgotPassword() {
		return "forgotPassword";
	}
	
	@GetMapping("/loadResetPass/{regNo}")
	public String loadResetPassword(@PathVariable int regNo,Model m) {
		m.addAttribute("regNo",regNo);
		return "resetPassword";
	}
	
	@PostMapping("/forgotP")
	public String forgotPassword(@RequestParam String email,@RequestParam int regNo,HttpSession session) {
		
		UserDtls user = userRepo.findByEmailAndRegNo(email, regNo);
		
		if(user!=null) {
			/* return "resetPassword"; */
			return "redirect:/loadResetPass/" + user.getRegNo();
		}else {
			session.setAttribute("msg", "invalid email and registration number");
			return "forgotPassword";
		}
	}
	
	@PostMapping("/changePassword")
	public String resetPassword(@RequestParam String newPass,@RequestParam Integer regNo,HttpSession session) {
		
		UserDtls user = userRepo.findById(regNo).get();
		String encryptPassword = passwordEncode.encode(newPass);
		
		user.setPassword(encryptPassword);
		
		UserDtls updateUser = userRepo.save(user);
		
		if(updateUser!=null) {
			session.setAttribute("msg", "password changed successfully");
		}
		
		return "redirect:/signin";
	}
	
}
