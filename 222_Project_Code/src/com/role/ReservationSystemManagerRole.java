/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReservationSystemManagerRole extends Role{
	private String[] choices = {"Set Ticket Prices", "Move Passengers Between Flights", "Change Passenger Seating", "Edit Watch and No Fly List"};
	
	public ReservationSystemManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Set Ticket Prices":
				break;
			case "Move Passengers Between Flights":
				break;
			case "Change Passenger Seating":
				break;	
			case "Edit Watch and No Fly List":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
