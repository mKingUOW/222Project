package com.userInteraction;

import java.util.*;
import java.io.*;

public class UserInterface{

	private UserController uc = new UserController();
	private Console consl = System.console();
	private Scanner in = new Scanner(System.in);
	
	private void displayLoginChoices(){		//Display all the choices for the user;
		System.out.println("Please select your choice: ");
		System.out.println("1: Login");
		System.out.println("2: SignUp");
		System.out.println("q: Quit.");
		System.out.print("Your Choice: ");
	}
	
/*	private void displayBookingChoices(){		//Display all the choices for the user;
		System.out.println("\nPlease select your Service: ");
		System.out.println("1: Book a flight");
		System.out.println("2: Search for a flight");
		System.out.println("3: Cancel a flight");
		System.out.println("4: Logout");
		System.out.println("q: Quit.");
		System.out.print("Your Choice: ");
	}
*/	
	public void login(){
		String myUserName = "";
		char [] myPasswd = null;
		char [] checkPasswd = null;
		boolean isLoginOkay;
		
		try{
			do{
				isLoginOkay = true;

				System.out.println("\nUsername: ");
				myUserName = consl.readLine();					//Read in the username;

				System.out.println("Password: ");
				myPasswd = consl.readPassword();				//Read in the password using the console.readPassword();
				
				isLoginOkay = uc.login(myUserName, myPasswd);

				if (!isLoginOkay) {
					System.out.println("Invalid credentials! Please input your username and password again!\n");
				}
			} while(!isLoginOkay);
			
			uc.start();

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void signUp(){
		String myUserName = "";
		char [] myPasswd = null;
		char [] checkPasswd = null;
		boolean isSignUpOkay;
		
		try{
			do{
				isSignUpOkay = true;

				System.out.println("\nUsername: ");
				myUserName = consl.readLine();					//Read in the username;

				System.out.println("Password: ");
				myPasswd = consl.readPassword();				//Read in the password using the console.readPassword();

				System.out.println("Password again: ");
				checkPasswd = consl.readPassword();				//Read in the password again and check whether they are the same; If not the same, then invalid;
				
				if(Arrays.equals(myPasswd,checkPasswd)){
	//				uc.signUp(myUserName,myPasswd);
				}else{	
					isSignUpOkay = false;		
					System.out.println("The passwords you entered are not the same! Please enter again.\n");
				}
			} while(!isSignUpOkay);

			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(){	
		char signChoice = '0';			//Used for storing the choice of a user;
		String signChoiceString = "";
		boolean isInputValid;
		
		do{	
			isInputValid = true;
			displayLoginChoices();							//Display the choices;
		
			try{
				signChoiceString = in.nextLine();				//Treat the input as a string;
				signChoice = signChoiceString.charAt(0);	//Get the first element of the string;
			}catch(Exception e){
				e.printStackTrace();
			}

			switch(signChoice){
				case '1':
					login();
					break;
				case '2':
					signUp();
					break;
				case 'q': //If entering 'q', the system terminates;
					System.out.println("\nThe system's been shut down... ");
					System.exit(0);	
					break;
				default:
					isInputValid = false;
					System.out.println("\nInvalid choice! Please input your choice again or enter 'q' to quit!\n");
					break;
			}
		} while(!isInputValid);
		
	}
}