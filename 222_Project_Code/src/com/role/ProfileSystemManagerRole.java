/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.profile.ProfileController;

/**
 * A class that defines what a Profile System Manager actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class ProfileSystemManagerRole extends NormalStaffRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices;
	
	/**
	 * Used to access the profile editing functionality.
	 */
	private ProfileController pc;
	
	/**
	 * Default constructor
	 */
	public ProfileSystemManagerRole(){
		super();
                choices = new String[]{"Edit Watch and No Fly List", "Edit Travel Agency and Customer Profile", "Close User Account"};
                pc = new ProfileController();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Edit Watch and No Fly List":
				pc.editWatchAndNoFlyList();
				break;
			case "Edit Travel Agency and Customer Profile":
				pc.editAccount();
				break;
			case "Close User Account":
				pc.closeAccount();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Profile System Manager";
	}
}
