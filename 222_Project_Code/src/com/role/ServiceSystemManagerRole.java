/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceSystemManagerRole extends Role{
	private String[] choices = {"Add Services", "Remove Services", "Edit Service Price", "Edit Service Availability"};
	
	public ServiceSystemManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Add Services":
				break;
			case "Remove Services":
				break;
			case "Edit Service Price":
				break;	
			case "Edit Service Availability":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
