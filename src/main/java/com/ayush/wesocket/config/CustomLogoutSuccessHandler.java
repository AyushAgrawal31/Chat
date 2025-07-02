package com.ayush.websocket.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.ayush.websocket.model.UserSession;
import com.ayush.websocket.repository.UserSessionRepository;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserSessionRepository sessionRepo;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            String email = authentication.getName();

            List<UserSession> sessions = sessionRepo.findByEmailOrderByLoginTimeDesc(email);
            if (!sessions.isEmpty()) {
                UserSession lastSession = sessions.get(0);
                lastSession.setLogoutTime(LocalDateTime.now());
                sessionRepo.save(lastSession);
            }
        }

        response.sendRedirect("/signin?logout");
    }
}
