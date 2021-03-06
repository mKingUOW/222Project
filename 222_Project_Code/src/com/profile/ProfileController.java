/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
import com.helpers.Staff;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class provides methods pertaining to any required 
 * functionality and processing that is related to profiles and accounts.
 * @author Michael Y.M. Kong
 */
public class ProfileController {
	/**
	 * Scanner object to use the standard in from the console.
	 */
	private Scanner in;
	
	/**
	 * Console object to use the standard in from the console.
	 */
	private Console console;
	
	/**
	 * ProfileController requires the ProfileEntity class to write/read data
	 * to the database.
	 */
	private ProfileEntity pe;
	
	/**
	 * Default constructor
	 */
	public ProfileController() {
            in = new Scanner(System.in);
            console = System.console();
            pe = new ProfileEntity();
	}
	
	/**
	 * Adds a new Person who is external to the system to the database.
	 * @param person The Person object to add.
	 * @return The Person ID of the Person object
	 */
	public int addPerson(Person person){
		return pe.addPerson(person);
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
		
		System.out.println("\nCHANGE PASSWORD");
		
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
		System.out.println("\nCLOSE USER ACCOUNT");
		closeAccount(enterUsername());
	}
	
	/**
	 * Actual method that closes the user's account.
	 * @param username The username to close the profile of.
	 * @return Returns true if the profile was closed. False otherwise. 
	 */
	public boolean closeAccount(String username){
		char choice;
		
		System.out.println("\nCLOSE ACCOUNT");
		
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
		System.out.println("\nEDIT ACCOUNT FOR CUSTOMER AND TRAVEL AGENCY");
		editAccount(enterUsername(), false);
	}
	
	/**
	 * This method is accessed directly when the user wants to edit their own
	 * account. Otherwise the overriding editAccount() method will be called
	 * when the profile system manager wants to edit a user's profile.
	 * @param username 
	 */
	public void editAccount(String username, boolean isCustomer){
		String[] options = {"Title", "First Name", "Last Name", "Gender",
			"Date of birth", "Phone number", "Email", "Street",
			"State", "City", "Country", "Credit Card", "Passport Availability"};
		List<String> option_list = new ArrayList<>(Arrays.asList(options));
		Map.Entry<Person, Integer> customer_pair = pe.getAccountDetails(username);
		Person customer = customer_pair.getKey();
		Integer frequent_flier_points = customer_pair.getValue();
		
		List<String> customer_details = Arrays.asList(customer.toArray());
		
		//if this is not a customer or travel agency accessing the method
		if (!isCustomer) {
			option_list.add("Frequent Flier Points");
			customer_details.add(frequent_flier_points.toString());
		}
		
		boolean isOkay;
		int choice = 0;
		
		System.out.println("\nEDIT ACCOUNT");
		
		do {
			isOkay = true;
			for (int i = 0; i < option_list.size(); i++) {
				System.out.print((i + 1) + ". ");
				System.out.print(option_list.get(i));
				System.out.println(" (" + customer_details.get(i) + ")");
			}
			
			System.out.print("Please select an option from above: ");
			
			try {
				choice = in.nextInt();
				if (choice < 1 || choice > option_list.size()) {
					System.out.println("That option is out of range. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please try again!\n");
				isOkay = false;
			}
			
		} while (!isOkay);
		
		if (choice != 12) { //special case for credit card, so we consider it in the switch
			System.out.print("Please enter the new value for " + options[choice - 1].toLowerCase() + ": ");
		} 
		
		in.nextLine();
		
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
			case 14:
				frequent_flier_points = enterFrequentFlierPoints();
				break;
		}
		
		pe.setAccountDetails(username, customer, frequent_flier_points);
		
		System.out.println("Details have been updated!\n");
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
		
		System.out.println("\nEDIT WATCH AND NO FLY LIST");
		
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
                
                System.out.println("User " + username + "'s fly status has been set to \"" + status_constants[(choice - 1)][0] + "\"");
	}
	
	/**
	 * Allows the System Administrator to create a new staff profile.
	 */
	public void createStaffProfile(){
		Staff new_staff = new Staff();
		boolean isOkay;
		
		do {
			//enter username
			System.out.print("\nPlease enter the username: ");
			new_staff.setUsername(in.nextLine());
			
			//enter password
			System.out.print("Enter a password: ");
			new_staff.setPassword(console.readPassword());

			//enter role
			System.out.println("Choose a role:");
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
				"Service System Manager", "Reservation System Manager"};
		
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
				role = "RVSM";
				break;
		}
		
		return role;
	}
	
	/**
	 * UI method to allow the user to change the user's frequent flier points.
	 * @return The frequent flier points entered.
	 */
	private int enterFrequentFlierPoints(){
		boolean isOkay;
		int frequent_flier_points = 0;
		
		do {			
			isOkay = true;
			
			//System.out.print("Enter the frequent flier points: ");
			
			try {
				frequent_flier_points = in.nextInt();
				
				if (frequent_flier_points < 0) {
					isOkay = false;
					System.out.println("Frequent flier points cannot be a negative number. Please try again!\n");
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid input detected. Please try again!\n");
			}
		} while (!isOkay);
		
		return frequent_flier_points;
	}
        
        /**
	 * Gets the account details for a particular username.
	 * @param username
	 * @return A Person object because we only need to modify the
	 * basic details of this user
	 */
        public Map.Entry<Person, Integer> getAccountDetails(String username){
            return pe.getAccountDetails(username);
        }
}
