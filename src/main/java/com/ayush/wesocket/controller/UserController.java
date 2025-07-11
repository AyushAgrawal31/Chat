package com.ayush.websocket.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ayush.websocket.model.UserDtls;
import com.ayush.websocket.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepo.findByEmail(email);

		m.addAttribute("user", user);
	}

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@GetMapping("/changePass")
	public String loadChangePassword() {
		return "user/changePassword";
	}

	@PostMapping("/updatePass")
	public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, HttpSession session) {

		String email = p.getName();

		UserDtls loginUser = userRepo.findByEmail(email);

		boolean f = passwordEncode.matches(oldPass, loginUser.getPassword());

		if (f) {
			loginUser.setPassword(passwordEncode.encode(newPass));
			UserDtls updatePasswordUser = userRepo.save(loginUser);

			if (updatePasswordUser != null) {
				session.setAttribute("msg", "Password Change Succsessfully");
			} else {
				session.setAttribute("msg", "Something went wrong");
			}
		} else {
			session.setAttribute("msg", "Old password is wrong");
		}
		return "redirect:/user/changePass";
	}

	
	@GetMapping("/first")
	public String firstYearPage() {
	    return "user/firstYear";
	}

	@GetMapping("/second")
	public String secondYearPage() {
	    return "user/secondYear";
	}

	@GetMapping("/third")
	public String thirdYearPage() {
	    return "user/thirdYear";
	}

	@GetMapping("/fourth")
	public String fourthYearPage() {
	    return "user/fourthYear";
	}
	
	@PostMapping("/upload-profile-photo")
	public ResponseEntity<String> uploadProfilePhoto(@RequestParam("regNo") int regNo,
	                                                 @RequestParam("image") MultipartFile image) {
	    UserDtls user = userRepo.findByRegNo(regNo);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    if (image.isEmpty()) {
	        return ResponseEntity.badRequest().body("No image uploaded");
	    }

	    try {
	        String fileName = "profile_" + regNo + "_" + image.getOriginalFilename();
	        Path uploadPath = Paths.get("src/main/resources/static/uploads/" + fileName);
	        Files.write(uploadPath, image.getBytes());

	        user.setProfileImage("/uploads/" + fileName);
	        userRepo.save(user);

	        return ResponseEntity.ok("Profile photo uploaded successfully");

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
	    }
	}

	@GetMapping("/photo")
	public String showUploadPhotoPage(Model model, Principal principal) {
		System.out.println("Inside /photo controller");
		String email = principal.getName();
	    UserDtls user = userRepo.findByEmail(email);

	    model.addAttribute("user", user);
	    return "user/uploadPhoto"; 
	}
}
