/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.BookingController;

/**
 *
 * @author Michael Y.M. Kong
 */
public class NormalStaffRole extends Role{
	private String[] choices = {"Cancel Customer Booking", "Make Booking for Customer", "Edit Services fo Customer"};
	private BookingController bc = new BookingController();
	
	public NormalStaffRole() {
		super();
		addChoices(choices);
	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Cancel Customer Booking":
				bc.cancelBooking();
				break;
			case "Make Booking for Customer":
				bc.makeBooking();
				break;
			case "Edit Services fo Customer":
				bc.editServices();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
