package com.role;

import com.booking.BookingController;

public class TravelAgencyRole extends AbstractCustomerRole{
	private String[] choices = {"Make Booking"};
	private BookingController bc = new BookingController();
	
	public TravelAgencyRole(){
		super();
		addChoices(choices);
	}

	public void addCustomers(){
		
	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Make Booking":
				bc.makeBooking("TA");
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}