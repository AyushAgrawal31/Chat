package com.ayush.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ayush.websocket.model.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
	
    List<UserSession> findByEmailOrderByLoginTimeDesc(@Param("email") String email);
    List<UserSession> findByEmailContainingIgnoreCase(@Param("email") String email);
}