package com.ayush.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.websocket.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer>{
	
	public boolean existsByRegNo(int regNo);
	
	public UserDtls findByEmail(String email);
	
	public UserDtls findByEmailAndRegNo(String email,int regNo);
	
	public UserDtls findByVerificationCode(String code);
	
	public UserDtls findByRegNo(int regNo);
}
