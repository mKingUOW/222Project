/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.ServiceController;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceSystemManagerRole extends Role{
	private String[] choices = {"Add Services", "Remove Services", "Edit Service Price", "Edit Service Availability"};
	private ServiceController sc = new ServiceController();
	
	public ServiceSystemManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Add Services":
				sc.addServices();
				break;
			case "Remove Services":
				sc.removeServices();
				break;
			case "Edit Service Price":
				sc.editServicePrice();
				break;	
			case "Edit Service Availability":
				sc.editServiceAvailability();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
