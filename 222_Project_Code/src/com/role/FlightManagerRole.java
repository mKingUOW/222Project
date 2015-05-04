/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FlightManagerRole extends Role{
	private String[] choices = {"Edit Routes", "Edit Fleet", "Edit Flight Schedule", "Edit Airports"};
	
	public FlightManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Edit Routes":
				break;
			case "Edit Fleet":
				break;
			case "Edit Flight Schedule":
				break;	
			case "Edit Airports":
				break;
			default:
				super.executeChoice();
				break;
		}
	}
}
