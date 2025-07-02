package com.ayush.websocket.service;

import com.ayush.websocket.model.UserDtls;

public interface UserService {
	
	public boolean blockUser(int regNo);
	public boolean unblockUser(int regNo);

	public UserDtls createUser(UserDtls user,String url);
	
	public boolean checkregNo(int regNo);
	
	public boolean verifyAccount(String code);
	
	
}
