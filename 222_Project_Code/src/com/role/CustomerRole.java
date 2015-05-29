package com.role;

import com.booking.BookingController;

/**
 * A class that defines what a Customer actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class CustomerRole extends AbstractCustomerRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Make Booking"};
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc = new BookingController();
			
	/**
	 * Default constructor
	 */
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

	@Override
	public String getRoleString() {
		return "Customer";
	}
}