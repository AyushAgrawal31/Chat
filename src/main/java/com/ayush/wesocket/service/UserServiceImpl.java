package com.ayush.websocket.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayush.websocket.model.UserDtls;
import com.ayush.websocket.repository.UserRepository;

import net.bytebuddy.utility.RandomString;


@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@Override
	public boolean checkregNo(int regNo) {
		return userRepo.existsByRegNo(regNo);
	}
	
	@Override
	public UserDtls createUser(UserDtls user,String url) {
		user.setPassword(passwordEncode.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		
		user.setEnabled(false);
		RandomString rn = new RandomString();
		user.setVerificationCode(rn.make(64));
		
		UserDtls us = userRepo.save(user);
		
		sendVerificationMail(user, url);
		
		return us;
	}
	
	
	public void sendVerificationMail(UserDtls user,String url) {
		String from="communitychat.verifys@gmail.com";
		String to=user.getEmail();
		
		String subject = "Account Verification";
		String content = "Dear [[name]],<br><br>" 
				+ "Click the link below to verify your account: <br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3> <br>"
				+ "Thank you, <br>"
				+ "Support Team";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			helper.setFrom(from,"Support");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content= content.replace("[[name]]", user.getFullName());
			
			String siteUrl = url+"/verify?code="+user.getVerificationCode();
			
			content= content.replace("[[URL]]",siteUrl);
			
			helper.setText(content,true);
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean verifyAccount(String code) {
		
		UserDtls user = userRepo.findByVerificationCode(code);
		
		if(user!=null) {
			user.setEnabled(true);
			user.setVerificationCode(null);
			userRepo.save(user);
			
			return true;
		}
		return false;
	}
	
	@Override
    public boolean blockUser(int regNo) {
        UserDtls user = userRepo.findByRegNo(regNo);
        if (user != null) {
            user.setAccountNonLocked(false);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean unblockUser(int regNo) {
        UserDtls user = userRepo.findByRegNo(regNo);
        if (user != null) {
            user.setAccountNonLocked(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
