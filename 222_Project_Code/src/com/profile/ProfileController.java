/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
import com.helpers.Staff;
import java.io.Console;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ProfileController {
	private Scanner in = new Scanner(System.in);
	private Console console = System.console();
	private ProfileEntity pe = new ProfileEntity();
	
	public ProfileController() {
	}
	
	/**
	 * Login method.
	 * @param username The username of the user
	 * @param password The password of the user
	 * @return An abbreviation of the role for this user. If
	 * the login credentials are not valid, then "loginFail"
	 * will be returned.
	 */
	public String login(String username, char[] password){
		return pe.login(username, password);
	}
	
	/**
	 * Signup method.
	 * @param user Customer object that contains all the new customer data.
	 * @return A boolean value signifying whether the signup was successful or not.
	 */
	public boolean signUp(Customer user){
		return pe.signUp(user);
	}
	
	/**
	 * Charges the price to the user's account.
	 * @param username The username to charge the price to.
	 * @param price The price charged to the user's account
	 */
	public void chargeAccount(String username, double price){
		pe.chargeAccount(username, price);
	}
	
	/**
	 * An interface method that allows users to enter a single username with validation.
	 * @return The chosen username.
	 */
	public String enterUsername(){
		String[] customer_username = new String[1];
		boolean isUsernameOkay;
		
		do {
			System.out.print("Please enter username: ");
			customer_username[0] = in.nextLine();

			isUsernameOkay = pe.checkUsernames(customer_username);

			if (!isUsernameOkay) {
				System.out.println("The username that was entered is not valid!\nPlease try again!\n");
			}
			
		} while (!isUsernameOkay);
		
		return customer_username[0];
	}
	
	/**
	 * An interface method that allows users to enter multiple username with validation.
	 * @return A String array of the chosen usernames.
	 */
	public String[] enterUsernames(){
		boolean areUsernamesOkay;
		String[] customer_usernames;
		
		do {
			System.out.print("Please enter the usernames of existing customers separated by spaces: ");
			customer_usernames = in.nextLine().split(" ");

			areUsernamesOkay = checkUsernames(customer_usernames);

			if (!areUsernamesOkay) {
				System.out.println("A username that was entered is not valid!\nPlease try again!\n");
			}
		} while (!areUsernamesOkay);
		
		return customer_usernames;
	}
	
	/**
	 * A private method that sends the given String array to the PersonEntity
	 * class to check whether those usernames are valid or not.
	 * @param usernames String of usernames to validate.
	 * @return Boolean value signifying whether the usernames are valid or not.
	 * Will return false if one or more usernames in the array are invalid.
	 */
	private boolean checkUsernames(String usernames[]){
		return pe.checkUsernames(usernames);
	}
	
	/**
	 * An interface method that allows users to change their password.
	 * @param username The username of the current user.
	 */
	public void changePassword(String username){
		char[] current_pw = pe.getPassword(username);
		char[] entered_password;
		char[] new_password;
		boolean is_okay;
		
		do {			
			is_okay = true;
			
			System.out.print("Enter your current password: ");
			entered_password = console.readPassword();
			
			if (!Arrays.equals(current_pw, entered_password)) {
				System.out.println("Password is incorrect. Please try again!\n");
				is_okay = false;
			}
		} while (!is_okay);
		
		System.out.print("Enter your new password: ");
		new_password = console.readPassword();
		
		pe.savePassword(username, new_password);
		
		System.out.print("Your password had been changed!\n");
	}
	
	/**
	 * Gets the frequent flier points for a particular user.
	 * @param username The username of the user to get the frequent flier points.
	 * @return The number of frequent flier points.
	 */
	public int getFrequentFlierPoints(String username){
		return pe.getFrequentFlierPoints(username);
	}
	
	/**
	 * Sets the frequent flier points for a particular user.
	 * @param username The username of the user to set the frequent flier points.
	 * @param points The number of points that this user currently has.
	 */
	public void setFrequentFlierPoints(String username, int points){
		pe.setFrequentFlierPoints(username, points);
	}
	
	/**
	 * Method for the profile system manager to close a user's account.
	 */
	public void closeAccount(){
		closeAccount(enterUsername());
	}
	
	/**
	 * Actual method that closes the user's account.
	 * @param username The username to close the profile of.
	 * @return Returns true if the profile was closed. False otherwise. 
	 */
	public boolean closeAccount(String username){
		char choice;
		
		System.out.println("Are you sure you want to close this account?");
		System.out.println("You will be logged out of the system if you close your account.");
		System.out.print("This operation cannot be undone. (Y/N): ");
		choice = in.nextLine().charAt(0);
		
		if (choice == 'Y' || choice == 'y') {
			pe.closeAccount(username);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method for the profile system manager to edit a user's account.
	 */
	public void editAccount(){
		editAccount(enterUsername());
	}
	
	/**
	 * This method is accessed directly when the user wants to edit their own
	 * account. Otherwise the overriding editAccount() method will be called
	 * when the profile system manager wants to edit a user's profile.
	 * @param username 
	 */
	public void editAccount(String username){
		String[] options = {"Title", "First Name", "Last Name", "Gender",
			"Date of birth", "Phone number", "Email", "Street",
			"State", "City", "Country", "Credit Card", "Passport Availability"};
		Person customer = pe.getAccountDetails(username);
		String[] customer_details = customer.toArray();
		
		boolean is_okay;
		int choice = 0;
		
		do {
			is_okay = true;
			for (int i = 0; i < options.length; i++) {
				System.out.print((i + 1) + ". ");
				System.out.print(options[i]);
				System.out.println("(" + customer_details[i] + ")");
			}
			
			System.out.print("Please select an option from above: ");
			
			try {
				choice = in.nextInt();
				if (choice < 1 || choice > options.length) {
					System.out.println("That option is out of range. Please try again!\n");
					is_okay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please try again!\n");
				is_okay = false;
			}
			
		} while (!is_okay);
		
		if (choice != 12) { //special case for credit card, so we consider it in the switch
			System.out.print("Please enter the new value for " + options[choice - 1].toLowerCase() + ": ");
		} 
		
		switch (choice){
			case 1:
				customer.setTitle(in.nextLine());
				break;
			case 2:
				customer.setFirstName(in.nextLine());
				break;
			case 3:
				customer.setLastName(in.nextLine());
				break;
			case 4:
				customer.setGender(in.nextLine());
				break;
			case 5:
				customer.setDOB(in.nextLine());
				break;
			case 6:
				customer.setPhoneNumber(in.nextLine());
				break;
			case 7:
				customer.setEmail(in.nextLine());
				break;
			case 8:
				customer.setStreet(in.nextLine());
				break;
			case 9:
				customer.setState(in.nextLine());
				break;
			case 10:
				customer.setCity(in.nextLine());
				break;
			case 11:
				customer.setCountry(in.nextLine());
				break;
			case 12:
				System.out.print("Please enter the new value for credit card type: ");
				customer.setCreditCardType(in.nextLine());
				
				System.out.print("Please enter the new value for credit card number: ");
				customer.setCreditCardNumber(in.nextLine());
				break;
			case 13:
				customer.setHasPassport(in.nextLine());
				break;
		}
		
		pe.setAccountDetails(customer);
		
		System.out.println("Your details have been updated!\n");
	}
	
	/**
	 * Returns the user's fly status.
	 * @param username The username of the user to check for.
	 * @return The fly status of the given user.
	 */
	public String canUserFly(String username){
		return pe.canUserFly(username);
	}
	
	/**
	 * Allows the profile system manager and reservation system manager to
	 * edit the watch and no fly list.
	 */
	public void editWatchAndNoFlyList(){
		boolean isOkay;
		int choice = 0;
		String username = enterUsername();
		String[][] status_constants = {
			{"Can Fly", ""},
			{"Watch", "watch"},
			{"No Fly", "no fly"}
		};
		
		for (int i = 0; i < status_constants.length; i++) {
			System.out.print((i + 1) +". ");
			System.out.println(status_constants[i][0]);
		}
		
		do {
			isOkay = true;
			System.out.print("Please select a flying status for customer \"" + username + "\": ");
			
			try {
				choice = in.nextInt();
				if (choice < 0 || choice > status_constants.length) {
					System.out.println("That option is out of range. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		pe.editWatchAndNoFlyList(username, status_constants[choice - 1][1]);
	}
	
	/**
	 * Allows the System Administrator to create a new staff profile.
	 */
	public void createStaffProfile(){
		Staff new_staff = new Staff();
		boolean isOkay;
		
		do {
			//enter username
			System.out.print("Please enter the username: ");
			new_staff.setUsername(in.nextLine());

			//enter password
			System.out.print("Enter a password: ");
			new_staff.setPassword(console.readPassword());

			//enter role
			new_staff.setRole(enterRole());

			isOkay = pe.createStaffProfile(new_staff);
			
			if (!isOkay) {
				System.out.println("The username \"" + new_staff.getUsername() + "\" already exists. Please try again!\n");
			}
		} while (!isOkay);
		
		System.out.println("New staff profile under username \"" + new_staff.getUsername() + "\" has been saved.\n");
	}
	
	/**
	 * Allows the System Administrator to edit a staff profile.
	 */
	public void editStaffProfile(){
		Staff staff = pe.getStaff(enterUsername());
		int choice = 0;
		boolean isOkay;
		
		System.out.println("Editing profile of " + staff.getUsername());
		System.out.println("1. Change password");
		System.out.println("2. Change role");
		
		do {
			isOkay = true;
			
			System.out.print("Enter your choice: ");
			
			try {
				choice = in.nextInt();
				
				if (choice < 1 || choice > 2) {
					isOkay = false;
					System.out.println("The selected option doesn't exist. Please try again!\n");
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid input detected. Please try again!\n");
			}
		} while (!isOkay);
		
		switch(choice){
			case 1:
				//enter password
				System.out.print("Enter the new password: ");
				staff.setPassword(console.readPassword());
				break;
			case 2:
				//enter role
				staff.setRole(enterRole());
				break;
		}
		
		pe.editStaffProfile(staff);
		
		System.out.println("Staff profile under username \"" + staff.getUsername() + "\" has been saved.\n");
	}
	
	/**
	 * UI method to allow the user to enter a role.
	 * @return Returns the chosen role.
	 */
	private String enterRole(){
		int choice = 0;
		boolean isOkay;
		String role = "";
		
		String[] roles = {"Normal Staff", "Flight Manager", "Profile System Manager",
				"Service System Manager", "Reservation System Manager", "Reporting System Manager"};
		
		for (int i = 0; i < roles.length; i++) {
			System.out.println((i + 1) + ". " + roles[i]);
		}
		do {
			isOkay = true;
			
			System.out.print("Please choose a role for this profile: ");

			try {
				choice = in.nextInt();
				
				if (choice < 1 || choice > roles.length) {
					isOkay = false;
					System.out.println("The selected option does not exist. Please try again!\n");
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid input detected. Please try again!\n");
			}
		} while (!isOkay);
		
		switch(choice){
			case 1:
				role = "NOR";
				break;
			case 2:
				role = "FM";
				break;
			case 3:
				role = "PSM";
				break;
			case 4:
				role = "SSM";
				break;
			case 5:
				role = "RSVM";
				break;
			case 6:
				role = "RPSM";
				break;
		}
		
		return role;
	}
}
