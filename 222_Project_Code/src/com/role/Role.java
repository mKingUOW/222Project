package com.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class Role{
	private String[] choices = {"Change Password", "Logout"};
	private Scanner in = new Scanner(System.in);
	private boolean isLoggedIn;

	protected List<String> allChoices;
	protected int userChoice = -1;
	
	public Role() {
		allChoices = new ArrayList<>(Arrays.asList(choices));
		Collections.sort(allChoices);
		isLoggedIn = true;
	}

	public void displayChoices(){
		boolean isInputValid;
		
		System.out.println("\nFLIGHT MANAGEMENT SYSTEM");
		
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
	
	public void addChoices(String[] additionalChoices){
		if (additionalChoices != null) {
			allChoices.addAll(Arrays.asList(additionalChoices));
			Collections.sort(allChoices);
		}
	}
	
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Change Password":
				break;
			case "Logout":
				isLoggedIn = false;
				break;
		}
	}
	
	public boolean isUserLoggedIn(){
		return isLoggedIn;
	}
	
	public List<String> getChoices(){
		return allChoices;
	}
}