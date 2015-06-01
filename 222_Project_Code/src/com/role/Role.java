package com.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.profile.ProfileController;

/**
 * An abstract class that defines the common behavior that all the Roles in the
 * system can do.
 * @author Michael Y.M. Kong
 */
public abstract class Role{
	/**
	 * The username of the current user.
	 */
	private String username;
	
	/**
	 * The choices available for this role.
	 */
	private String[] choices;
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable;
	
	/**
	 * Scanner object to use the standard input console.
	 */
	private Scanner in;
	
	/**
	 * Session variable to check whether the user is still logged in.
	 */
	private boolean isLoggedIn;
	
	/**
	 * Requires the ProfileController class to access services in that class.
	 */
	private ProfileController pc;
	
	/**
	 * Holds all the possible choices that will have been passed to itself from
	 * its subclasses.
	 */
	protected List<String> allChoices;
	
	/**
	 * Holds all the possible available reports that will have been passed to itself from
	 * its subclasses.
	 */
	protected List<String> allReportsAvailable;
	
	/**
	 * The choice of the user for the main menu.
	 */
	protected int userChoice;
	
	/**
	 * The choice of the user for the report menu.
	 */
	protected int userReportChoice;
	
	/**
	 * Default constructor.
	 * Puts the choices into allChoices attribute and sorts them.
	 * Also sets the session to active.
	 */
	public Role() {
                username = "";
                choices = new String[]{"Change Password", "Logout", "View Reports"};
                reportsAvailable = new String[]{};
                in = new Scanner(System.in);
                pc = new ProfileController();
                userChoice = -1;
                userReportChoice = -1;
		allChoices = new ArrayList<>(Arrays.asList(choices));
		allReportsAvailable = new ArrayList<>(Arrays.asList(reportsAvailable));
		Collections.sort(allChoices);
		Collections.sort(allReportsAvailable);
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
	 * Displays the available report options.
	 */
	public void displayReportMenu(){
		boolean isOkay;
		
		System.out.println("\nREPORTS");
		
                if (allReportsAvailable.isEmpty()) {
                    System.out.println("There are no reports available for you yet.\n");
                    return;
                }
                
		do {			
			isOkay = true;
			
			for (int i = 0; i < allReportsAvailable.size(); i++) {
				System.out.println((i + 1) + ": " + allReportsAvailable.get(i));
			}
			System.out.print("Enter your choice: ");
			
			try {
				userReportChoice = in.nextInt();
				
				if (userReportChoice < 1 || userReportChoice > allReportsAvailable.size()) {
					System.out.println("Please key in a number between 1 and " + allReportsAvailable.size() + "!\n");
					
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("That choice is not available. Please try again!\n");
			}
		} while (!isOkay);
		
		userReportChoice--;
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
	 * Allows subclasses to add the report choices unique to that class.
	 * @param additionalChoices The additional report choices to add.
	 */
	protected void addReportChoices(String[] additionalChoices){
		if (additionalChoices != null) {
			allReportsAvailable.addAll(Arrays.asList(additionalChoices));
			Collections.sort(allReportsAvailable);
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
				this.displayReportMenu();
				this.executeReportChoice();
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
	 * Executes the report choice that was selected.
	 */
	public void executeReportChoice(){
		
	}
	
	/**
	 * Gets the name of the role that is currently active.
	 * @return Gets the name of the role.
	 */
	public abstract String getRoleString();
}