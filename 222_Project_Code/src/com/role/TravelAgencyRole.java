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
	private String[] choices;
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc;
	
	/**
	 * Default constructor.
	 */
	public TravelAgencyRole(){
		super();
                choices = new String[]{"Make Booking"};
                bc = new BookingController();
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