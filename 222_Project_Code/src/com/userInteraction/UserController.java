package com.userInteraction;

import java.util.*;
import java.io.*;
import com.role.*;
import com.helpers.UserLoginDetails;

public class UserController{
	private Role role;
	private UserEntity ue = new UserEntity();

	public UserController(){ //default constructor
		
	}

	public boolean login(String username, char[] password){
		String roleName = ue.login(username, password);
		
		if ("loginFail".equals(roleName)) {
			return false;
		}

		setRole(roleName);

		return true;
	}
	
	public boolean signUp(UserLoginDetails uld, char[] confirmPassword){
		
		boolean isLoginOkay = true;
		boolean isPasswordOkay = Arrays.equals(uld.getPassword(), confirmPassword);
		
		if(isPasswordOkay){
			isLoginOkay = true;
			
			if (!isLoginOkay) {
				System.out.println("The username already exists! Please try another username.\n");
			}
		}else{	
			isPasswordOkay = false;		
			System.out.println("The passwords you entered are not the same! Please try again.\n");
		}
		
		return isPasswordOkay && isLoginOkay;
	}

	public void start(){
		do {			
			role.displayChoices();
			role.executeChoice();
		} while (role.isUserLoggedIn());
	}

	private void setRole(String roleName){
		role = RoleFactory.getRole(roleName);
	}
	
}
