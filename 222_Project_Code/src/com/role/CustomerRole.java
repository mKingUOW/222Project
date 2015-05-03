package com.role;

import java.util.*;

public class CustomerRole extends AbstractCustomerRole{

	private Scanner in = new Scanner(System.in);

	public CustomerRole(){
		super();
		addChoices(null);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			/*
			case "Make Booking":
				break;
			*/
			default:
				super.executeChoice();
				break;
		}
	}
}