package com.role;

import com.booking.BookingController;
import com.profile.ProfileController;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account", "View My Bookings", "View Reports"};
	private BookingController bc = new BookingController();
	private ProfileController pc = new ProfileController();
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		bc.setUsername(getUsername());
		
		switch(choice){
			case "Edit Services":
				
				break;
			case "Close Account":
				if (pc.closeAccount(getUsername())) {
					setUserLoggedOut();
				}
				break;
			case "Cancel Booking":
				bc.cancelBooking();
				break;
			case "Edit Account":
				pc.editAccount(getUsername());
				break;
			case "View My Bookings":
				bc.viewBookings();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}