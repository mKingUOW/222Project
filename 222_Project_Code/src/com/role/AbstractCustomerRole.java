package com.role;

import java.util.*;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Make Booking", "Edit Services", "Close Account", "Cancel Booking", "Edit Account"};
	private Scanner in = new Scanner(System.in);
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
	}

	public void editServices(){

	}

	public void closeAccount(){

	}

	public void cancelBooking(){

	}

	public void editAccount(){

	}

	public void makeBooking(){

	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Make Booking":
				break;
			case "Edit Services":
				break;
			case "Close Account":
				break;
			case "Cancel Booking":
				break;
			case "Edit Account":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}