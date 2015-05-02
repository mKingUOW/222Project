package userInteraction;

import role.*;

import java.util.*;
import java.io.*;

public class UserInterface{
	
	private void displayLoginChoices(){		//Display all the choices for the user;
		System.out.println("Please select your choice: ");
		System.out.println("1: Login");
		System.out.println("2: SignUP");
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
		UserController uc = new UserController();
		Console consl = System.console();
		String myUserName = "";
		char [] myPasswd = null;
		char [] checkPasswd = null;
		
		try{
			System.out.println("\nUsername: ");
			myUserName = consl.readLine();					//Read in the username;
			System.out.println("Password: ");
			myPasswd = consl.readPassword();				//Read in the password using the console.readPassword();
			
//			uc.login(myUserName,myPasswd);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void signUp(){
		UserController uc = new UserController();
		Console consl = System.console();
		String myUserName = "";
		char [] myPasswd = null;
		char [] checkPasswd = null;
		
		try{
			System.out.println("\nUsername: ");
			myUserName = consl.readLine();					//Read in the username;
			System.out.println("Password: ");
			myPasswd = consl.readPassword();				//Read in the password using the console.readPassword();
			System.out.println("Password again: ");
			checkPasswd = consl.readPassword();				//Read in the password again and check whether they are the same; If not the same, then invalid;
			
			if(Arrays.equals(myPasswd,checkPasswd)){
//				uc.signUp(myUserName,myPasswd);
			}else{			
				while(!Arrays.equals(myPasswd,checkPasswd)){
					System.out.println("The passwords you entered are not the same! Please enter again.\n");
					System.out.println("Enter your password: ");
					myPasswd = consl.readPassword();
					System.out.println("Enter password again: ");
					checkPasswd = consl.readPassword();
					if(Arrays.equals(myPasswd,checkPasswd)){
//						uc.signUp(myUserName,myPasswd);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		Scanner in = new Scanner(System.in);	
		char signChoice = '0',bookChoice = '0';			//Used for storing the choice of a user;
		String signChoiceString = "",bookChoiceString="";
		displayLoginChoices();							//Display the choices;
		try{
			signChoiceString = in.next();				//Treat the input as a string;
			signChoice = signChoiceString.charAt(0);	//Get the first element of the string;
		}catch(Exception e){
			e.printStackTrace();
		}
		if(signChoice == 'q'){					
			System.out.println("\nThe system's been shut down... ");
			System.exit(1);									//If entering 'q', the system terminates;
		}else if(signChoice == '1'){
			login();
		}else if(signChoice == '2'){			
			signUp();
		}else{
			while(signChoice != 'q' && signChoice != '1' && signChoice != '2'){	
				System.out.println("\nInvalid choice! Please input your choice again or enter 'q' to quit!\n");
				displayLoginChoices();
				try{
					signChoiceString = in.next();				//Treat the input as a string;	
					signChoice = signChoiceString.charAt(0);	//Get the first element of the string;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(signChoice == 'q'){					
				System.out.println("\nThe system's been shut down... ");
				System.exit(1);						//If entering 'q', the system terminates;
			}else if(signChoice == '1'){				
				login();
			}else if(signChoice == '2'){			
				signUp();
			}
		}
	}
};