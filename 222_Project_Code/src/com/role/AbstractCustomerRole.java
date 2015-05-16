package com.role;

import com.booking.BookingController;
import com.profile.ProfileController;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account"};
	private BookingController bc = new BookingController();
	private ProfileController pc = new ProfileController();
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Edit Services":
				break;
			case "Close Account":
				pc.closeAccount(getUsername());
				setUserLoggedOut();
				break;
			case "Cancel Booking":
				bc.setUsername(getUsername());
				bc.cancelBooking();
				break;
			case "Edit Account":
				pc.editAccount(getUsername());
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}