/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
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
	
	public String login(String username, char[] password){
		return pe.login(username, password);
	}

	public boolean signUp(Customer user){
		return pe.signUp(user);
	}
	
	public boolean checkUsernames(String usernames[]){
		return pe.checkUsernames(usernames);
	}
	
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
	
	public int getFrequentFlierPoints(String username){
		return pe.getFrequentFlierPoints(username);
	}
	
	public void setFrequentFlierPoints(String username, int points){
		pe.setFrequentFlierPoints(username, points);
	}
	
	public void closeAccount(){
		closeAccount(selectUser());
	}
	
	public void closeAccount(String username){
		char choice;
		
		System.out.println("Are you sure you want to close this account?");
		System.out.print("This operation cannot be undone. (Y/N): ");
		choice = in.nextLine().charAt(0);
		
		if (choice == 'Y' || choice == 'y') {
			pe.closeAccount(username);
		}
	}
	
	public void editAccount(){
		editAccount(selectUser());
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
				System.out.print("(" + customer_details[i] + ")");
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
	
	public String canUserFly(String username){
		return pe.canUserFly(username);
	}
	
	public void editWatchAndNoFlyList(){
		boolean isOkay;
		int choice = 0;
		String username = selectUser();
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
			System.out.println("Please select a flying status for customer \"" + username + "\": ");
			
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
	
	private String selectUser(){
		String[] customer_username = new String[1];
		boolean isUsernameOkay;
		
		do {
			System.out.print("Please enter the username of an existing customer: ");
			customer_username[0] = in.nextLine();

			isUsernameOkay = pe.checkUsernames(customer_username);

			if (!isUsernameOkay) {
				System.out.println("The username that was entered is not valid!\nPlease try again!\n");
			}
			
		} while (!isUsernameOkay);
		
		return customer_username[0];
	}
	
}
