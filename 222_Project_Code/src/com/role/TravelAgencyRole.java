package com.role;

import com.booking.BookingController;

/**
 * A class that defines what a Travel Agency actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class TravelAgencyRole extends AbstractCustomerRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Make Booking"};
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc = new BookingController();
	
	/**
	 * Default constructor.
	 */
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