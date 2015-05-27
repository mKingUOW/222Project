package com.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.profile.ProfileController;
import com.report.ReportController;

public abstract class Role{
	/**
	 * The username of the current user.
	 */
	private String username = "";
	
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Change Password", "Logout", "View Reports"};
	
	/**
	 * Scanner object to use the standard input console.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Session variable to check whether the user is still logged in.
	 */
	private boolean isLoggedIn;
	
	/**
	 * Requires the ProfileController class to access services in that class.
	 */
	private ProfileController pc = new ProfileController();
	
	/**
	 * Requires the ReportController class to access services in that class.
	 */
	private ReportController rc = new ReportController();
	
	/**
	 * Holds all the possible choices that will have been passed to itself from
	 * its subclasses.
	 */
	protected List<String> allChoices;
	
	/**
	 * The choice of the user for the main menu.
	 */
	protected int userChoice = -1;
	
	/**
	 * Default constructor.
	 * Puts the choices into allChoices attribute and sorts them.
	 * Also sets the session to active.
	 */
	public Role() {
		allChoices = new ArrayList<>(Arrays.asList(choices));
		Collections.sort(allChoices);
		isLoggedIn = true;
	}

	/**
	 * Displays all the choices available and handles the input choice.
	 */
	public void displayChoices(){
		boolean isInputValid;
		
		System.out.println("\nFLIGHT MANAGEMENT SYSTEM (" + getRoleString() + ")");
		
		do{	
			isInputValid = true;

			for (int i = 0; i < allChoices.size(); i++) {
				System.out.println((i + 1) + ": " + allChoices.get(i));
			}
			System.out.print("Your Choice: ");
			try{
				userChoice = in.nextInt();
				
				if (userChoice < 1 || userChoice > allChoices.size()) {
					System.out.println("Please key in a number between 1 and " + allChoices.size() + "!\n");
					
					isInputValid = false;
				}
			} catch(InputMismatchException ex){
				System.out.println("Please key in a number between 1 and " + allChoices.size() + "!\n");
				
				isInputValid = false;
				in.nextLine();
			}

		} while(!isInputValid);
		
		//since the main menu is offset by +1, decrement input by one
		userChoice--;
	}
	
	/**
	 * Allows subclasses to add the choices unique to that class.
	 * @param additionalChoices The additional choices to add.
	 */
	protected void addChoices(String[] additionalChoices){
		if (additionalChoices != null) {
			allChoices.addAll(Arrays.asList(additionalChoices));
			Collections.sort(allChoices);
		}
	}
	
	/**
	 * Checks whether user is currently logged in.
	 * @return True is the user is still logged in. False otherwise.
	 */
	public boolean isUserLoggedIn(){
		return isLoggedIn;
	}
	
	/**
	 * Sets the session to active.
	 */
	protected void setUserLoggedIn(){
		isLoggedIn = true;
	}
	
	/**
	 * Unsets the session from active.
	 */
	protected void setUserLoggedOut(){
		isLoggedIn = false; 
	}
	
	/**
	 * Gets the List of choices available.
	 * @return 
	 */
	protected List<String> getChoices(){
		return allChoices;
	}
	
	/**
	 * Sets the username for the role.
	 * @param username The username to set for this role.
	 */
	public void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * Gets the username.
	 * @return The username set for this role.
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Executes the choice that was selected.
	 */
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "View Reports":
				rc.init(this);
				break;
			case "Change Password":
				pc.changePassword(username);
				break;
			case "Logout":
				setUserLoggedOut();
				break;
		}
	}
	
	/**
	 * Displays the available report options.
	 */
	public abstract void displayReportMenu();
	
	/**
	 * Displays the chosen report.
	 * @param choice The selected report choice.
	 */
	public abstract void displayReport(int choice);
	
	/**
	 * Gets the name of the role that is currently active.
	 * @return Gets the name of the role.
	 */
	public abstract String getRoleString();
}