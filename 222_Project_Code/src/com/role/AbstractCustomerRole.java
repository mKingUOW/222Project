package com.role;

import java.util.*;
import com.booking.BookingController;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account"};
	private Scanner in = new Scanner(System.in);
	private BookingController bc = new BookingController();
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
	}

	public void editServices(){

	}

	public void closeAccount(){

	}
	public void editAccount(){

	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Edit Services":
				break;
			case "Close Account":
				break;
			case "Cancel Booking":
				bc.cancelBooking();
				break;
			case "Edit Account":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}