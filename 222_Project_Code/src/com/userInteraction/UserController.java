package com.userInteraction;

import java.util.*;
import com.role.*;
import com.helpers.Customer;

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
		role.setUsername(username);

		return true;
	}
	
	public boolean signUp(Customer uld, char[] confirmPassword){
		boolean isLoginOkay = true;
		boolean isPasswordOkay = Arrays.equals(uld.getPassword(), confirmPassword);
		
		if(isPasswordOkay){
			switch (uld.getRole()) {
				case "Travel Agency":
					uld.setRole("TA");
					break;
				case "Customer": 
				default: //if user enters something unexpected, system will default them to customer
					uld.setRole("CUS");
					break;
			}
			
			isLoginOkay = ue.signUp(uld);
			
			if (!isLoginOkay) {
				System.out.println("The username already exists! Please try another username.\n");
			}
		}else{	
			System.out.println("The passwords you entered are not the same! Please try again.\n");
		}
		
		return isPasswordOkay && isLoginOkay;
	}

	public void start(){
		do {			
			role.displayChoices();
			role.executeChoice();
		} while (role.isUserLoggedIn());
		System.out.println("Goodbye!\n");
	}

	private void setRole(String roleName){
		role = RoleFactory.getRole(roleName);
	}
	
}
