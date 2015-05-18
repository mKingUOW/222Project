/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import java.io.Console;
import java.util.Arrays;
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
	}
	
	public void closeAccount(String username){
		char choice;
		
		System.out.println("Are you sure you want to close your account?");
		System.out.print("This operation cannot be undone. (Y/N): ");
		choice = in.nextLine().charAt(0);
		
		if (choice == 'Y' || choice == 'y') {
			pe.closeAccount(username);
		}
	}
	
	public void editAccount(String username){
		
	}
}
