package com.interaction;

import java.util.*;
import java.io.*;
import com.helpers.Customer;

/**
 * This class is displays the first menu of the system.
 * Allows the user to login, signup or quit the program.
 * @author Michael Y. M. Kong
 */
public class UserInterface{
	/**
	 * UserInterface requires the UserController class for the
	 * functionality and processing.
	 */
	private UserController uc;
	
	/**
	 * Console object to use the standard input from the console.
	 */
	private Console consl;
	
	/**
	 * Scanner object to use the standard input from the console.
	 */
	private Scanner in;

        /**
         * Default constructor
         */
        public UserInterface() {
            uc = new UserController();
            consl = System.console();
            in = new Scanner(System.in);
        }
        
	/**
	 * Displays the initial flight system interface allowing the 
	 * user to login, signup or quit the program.
	 */
	private void displayLoginChoices(){		//Display all the choices for the user;
		System.out.println("\nFLIGHT MANAGEMENT SYSTEM");
		System.out.println("Please select your choice: ");
		System.out.println("1: Login");
		System.out.println("2: SignUp");
		System.out.println("q: Quit");
		System.out.print("Your Choice: ");
	}
	
	/**
	 * The login function that is called to enter the login credentials and
	 * process the login.
	 */
	public void login(){
		String myUserName;
		char [] myPasswd;
		boolean isLoginOkay;
		
		try{
			do{
				System.out.print("\nUsername: ");
				myUserName = consl.readLine();					//Read in the username;

				System.out.print("Password: ");
				myPasswd = consl.readPassword();				//Read in the password using the console.readPassword();
				
				isLoginOkay = uc.login(myUserName, myPasswd);

				if (!isLoginOkay) {
					System.out.println("Invalid credentials! Please input your username and password again!");
				}
			} while(!isLoginOkay);
			
			uc.start();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Signup function that is called to create a new profile.
	 */
	public void signUp(){
		boolean isSignUpOkay;
		String title;
		String firstName;
		String lastName;
		String username;
		char[] password;
		char [] checkPasswd;
		String gender;
		String DOB;
		String phoneNumber;
		String email;
		String street;
		String state;
		String city;
		String country;
		String creditCardType;
		String creditCardNumber;
		String hasPassport;
		String role;
		
		try{
			do{
				System.out.print("\nTitle: ");
				title = consl.readLine();
				
				System.out.print("First Name: ");
				firstName = consl.readLine();
				
				System.out.print("Last Name: ");
				lastName = consl.readLine();
				
				System.out.print("Username: ");
				username = consl.readLine();					//Read in the username;

				System.out.print("Password: ");
				password = consl.readPassword();				//Read in the password using the console.readPassword();

				System.out.print("Confirm your password: ");
				checkPasswd = consl.readPassword();				//Read in the password again and check whether they are the same; If not the same, then invalid;
				
				System.out.print("Gender (Male/Female): ");
				gender = consl.readLine();
				
				System.out.print("Date of Birth: ");
				DOB = consl.readLine();
				
				System.out.print("Phone Number: ");
				phoneNumber = consl.readLine();
				
				System.out.print("Email: ");
				email = consl.readLine();
				
				System.out.print("Street: ");
				street = consl.readLine();
				
				System.out.print("State: ");
				state = consl.readLine();
				
				System.out.print("City: ");
				city = consl.readLine();
				
				System.out.print("Country: ");
				country = consl.readLine();
				
				System.out.print("Credit Card Type: ");
				creditCardType = consl.readLine();
				
				System.out.print("Credit Card Number: ");
				creditCardNumber = consl.readLine();
				
				System.out.print("Do you have a passport? (Y/N): ");
				hasPassport = consl.readLine();
				
				System.out.print("Role (Customer/Travel Agency): ");
				role = consl.readLine();
				
				Customer uld = new Customer(title, firstName, lastName, username, password,
						gender, DOB, phoneNumber, email,
						street, state, city, country,
						creditCardType, creditCardNumber, hasPassport, role);
				
				isSignUpOkay = uc.signUp(uld, checkPasswd);
				
			} while(!isSignUpOkay);

			System.out.println("Signup is successful!\n");
			
			//go back to main menu
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the process of this class after instantiation.
	 */
	public void start(){	
		char signChoice = '0';			//Used for storing the choice of a user;
		String signChoiceString = "";
		boolean shouldContinue;
		
		do{
			shouldContinue = true;
			displayLoginChoices();							//Display the choices;
		
			try{
				signChoiceString = in.nextLine();				//Treat the input as a string;
				signChoice = signChoiceString.charAt(0);	//Get the first element of the string;
			}catch(Exception e){
				
			}

			switch(signChoice){
				case '1':
					login();
					shouldContinue = false;
					break;
				case '2':
					signUp();
					break;
				case 'q': //If entering 'q', the system terminates;
					System.out.println("\nGoodbye!");
					shouldContinue = false;
					break;
				default:
					System.out.println("\nInvalid choice! Please input your choice again or enter 'q' to quit!\n");
					break;
			}
		} while(shouldContinue);
		
	}
}