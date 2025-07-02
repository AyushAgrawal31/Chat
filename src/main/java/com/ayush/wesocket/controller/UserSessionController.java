package com.ayush.websocket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.websocket.repository.UserSessionRepository;
import com.ayush.websocket.model.UserSession;

@RestController
@RequestMapping("/api/logs")
public class UserSessionController {

    private final UserSessionRepository userSessionRepository;

    public UserSessionController(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @GetMapping
    public List<UserSession> getSessions(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            return userSessionRepository.findByEmailContainingIgnoreCase(email);
        }
        return userSessionRepository.findAll();
    }

}
