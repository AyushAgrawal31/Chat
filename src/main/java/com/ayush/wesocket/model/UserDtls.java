package com.ayush.websocket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserDtls {
	
	private String fullName;
	
	private String email;
	
	@Id
	private int regNo;
	
	private String password;
	
	private String role;
	
	private boolean accountNonLock;
	
	private boolean enabled;
	
	private String verificationCode;
	@Column(name = "profile_image")
	private String profileImage;

	// Getter and Setter
	public String getProfileImage() {
	    return profileImage;
	}

	public void setProfileImage(String profileImage) {
	    this.profileImage = profileImage;
	}

	public boolean isAccountNonLocked() {
        return accountNonLock;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLock = accountNonLocked;
    }
}
