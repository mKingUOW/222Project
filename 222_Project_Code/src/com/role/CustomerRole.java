package com.role;

import com.booking.BookingController;

public class CustomerRole extends AbstractCustomerRole{
	private String[] choices = {"Make Booking"};
	private BookingController bc = new BookingController();
			
	public CustomerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Make Booking":
				bc.setUsername(getUsername());
				bc.makeBooking("CUS");
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}