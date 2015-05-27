/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.BookingController;
import com.booking.FlightController;
import com.profile.ProfileController;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReservationSystemManagerRole extends NormalStaffRole{
	private String[] choices = {"Set Ticket Prices", "Move Passengers Between Flights",
		"Change Passenger Seating", "Edit Watch and No Fly List",
		"Set Frequent Flier Point Discount Ratio", "Set Cancellation Fee"};
	private ProfileController pc = new ProfileController();
	private BookingController bc = new BookingController();
	private FlightController fc = new FlightController();
	
	public ReservationSystemManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Set Ticket Prices":
				fc.setSeatPrices(null);
				break;
			case "Move Passengers Between Flights":
				bc.movePassengers();
				break;
			case "Change Passenger Seating":
				bc.changePassengerSeating();
				break;	
			case "Edit Watch and No Fly List":
				pc.editWatchAndNoFlyList();
				break;
			case "Change Frequent Flier Point Discount Ratio":
				bc.setDiscountRatio();
				break;
			case "Set Cancellation Fee":
				bc.setCancellationFee();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Reservation System Manager";
	}
}
