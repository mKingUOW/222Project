package UserInterface;

import java.util.*;

public class UserInterface{
	
	
	public void displayLoginChoices(){		//Display all the choices for the user;
		System.out.println("Please select your choice: ");
		System.out.println("1: SignIn");
		System.out.println("2: SignUP");
		System.out.println("q: Quit.");
		System.out.print("Your Choice: ");
	}
	
	public void displayBookingChoices(){		//Display all the choices for the user;
		System.out.println("\nPlease select your Service: ");
		System.out.println("1: Book a flight");
		System.out.println("2: Search for a flight");
		System.out.println("3: Cancel a flight");
		System.out.println("q: Quit.");
		System.out.print("Your Choice: ");
	}
	
	public void signIn(){
		System.out.println("Please Enter your ");
		
	}
	
	public void signUP(){
		
		
	}
	
	public void processServices(char choice){ }
	public void processBookingFlight(){	}
	public void processSearchingFlight(){ }
	public void processCancelFlight(){ }
	
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
			System.out.println("\nThe system is existed... ");
			System.exit(1);									//If entering 'q', the system terminates;
		}else if(signChoice == '1'){
			signIn();
			displayBookingChoices();
			try{
				bookChoiceString = in.next();				//Treat the input as a string;
				bookChoice = signChoiceString.charAt(0);	//Get the first element of the string;
			}catch(Exception e){
				e.printStackTrace();
			}
			processServices(bookChoice);
		}else if(signChoice == '2'){			
			signUP();
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
				System.out.println("\nThe system is existed... ");
				System.exit(1);						//If entering 'q', the system terminates;
			}else if(signChoice == '1'){				
				displayBookingChoices();
				processBookingChoice();
			}else if(signChoice == '2'){			
				displayBookingChoices();
				processBookingChoice();
			}
		}
	}
}