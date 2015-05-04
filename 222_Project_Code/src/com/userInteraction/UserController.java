package com.userInteraction;

import java.util.*;
import java.io.*;
import com.role.*;

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
	
	public boolean signUp(){
		
		Arrays.equals(myPasswd,checkPasswd)
		
		if(){

		}else{	
			isSignUpOkay = false;		
			System.out.println("The passwords you entered are not the same! Please enter again.\n");
		}
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
