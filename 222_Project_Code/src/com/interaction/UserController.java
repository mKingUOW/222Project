package com.interaction;

import java.util.*;
import com.helpers.Customer;
import com.profile.ProfileController;
import com.role.Role;
import com.role.RoleFactory;

/**
 * This class is handles all processing at the beginning of the system for the
 * UserInterface class.
 * @author Michael Y. M. Kong
 */
public class UserController{
	/**
	 * The Role of the current user.
	 */
	private Role role;
	
	/**
	 * Used to access the profile functionality.
	 */
	private ProfileController pc;

	/**
	 * Default constructor
	 */
	public UserController(){ //default constructor
            pc = new ProfileController();
	}

	/**
	 * Processes the login with the given username and password.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return True if login is successful, false otherwise.
	 */
	public boolean login(String username, char[] password){
		String roleName = pc.login(username, password);
		
		if ("loginFail".equals(roleName)) {
			return false;
		}

		setRole(roleName);
		role.setUsername(username);

		return true;
	}
	
	/**
	 * Processes the signup with the given Customer details.
	 * @param uld The Customer details to save to database.
	 * @param confirmPassword The confirmation password to check whether the
	 * inputted password is the same as this one.
	 * @return True if signup is successful, false otherwise.
	 */
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
			uld.setFrequentFlierPoints(0);
			uld.setWatchOrNoFly("");
                        String passport_availability = uld.hasPassport();
                        
                        switch(passport_availability){
                            case "Y":
                            case "y":
                                uld.setHasPassport("Yes");
                                break;
                            case "N":
                            case "n":
                                uld.setHasPassport("No");
                                break;
                        }
                        
			isLoginOkay = pc.signUp(uld);
			
			if (!isLoginOkay) {
				System.out.println("The username already exists! Please try another username.\n");
			}
		}else{	
			System.out.println("The passwords you entered are not the same! Please try again.\n");
		}
		
		return isPasswordOkay && isLoginOkay;
	}

	/**
	 * This method is called after a successful login to start the main
	 * application.
	 */
	public void start(){
		do {			
			role.displayChoices();
			role.executeChoice();
		} while (role.isUserLoggedIn());
		System.out.println("Goodbye!\n");
	}

	/**
	 * Sets the role of the current user. Based on the given roleName, the 
	 * corresponding Role object is returned.
	 * @param roleName The role of the current user.
	 */
	private void setRole(String roleName){
		role = RoleFactory.getRole(roleName);
	}
	
}
