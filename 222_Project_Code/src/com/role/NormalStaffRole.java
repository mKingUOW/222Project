/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class NormalStaffRole extends Role{
	private String[] choices = {"Cancel Customer Booking", "Make Booking for Customer"};
	
	public NormalStaffRole() {
		super();
		addChoices(choices);
	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Cancel Customer Booking":
				break;
			case "Make Booking for Customer":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
