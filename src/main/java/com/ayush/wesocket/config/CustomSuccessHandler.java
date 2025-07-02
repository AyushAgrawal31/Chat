package com.ayush.websocket.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ayush.websocket.model.UserSession;
import com.ayush.websocket.repository.UserSessionRepository;



@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserSessionRepository sessionRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String email = authentication.getName();
        UserSession session = new UserSession();
        session.setEmail(email);
        session.setLoginTime(LocalDateTime.now());
        sessionRepo.save(session);
		
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/");
		} 
     	else if(roles.contains("ROLE_TEACHER")) {
			response.sendRedirect("/teacher/");
		}
		else {
			response.sendRedirect("/user/");
		}

	}
}
