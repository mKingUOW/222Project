package com.role;

import com.booking.BookingController;

public class TravelAgencyRole extends AbstractCustomerRole{
	private String[] choices = {"Make Booking"};
	private BookingController bc = new BookingController();
	
	public TravelAgencyRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Make Booking":
				bc.setUsername(getUsername());
				bc.makeBooking("TA");
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Travel Agency";
	}
}