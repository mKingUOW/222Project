/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.profile.ProfileController;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ProfileSystemManagerRole extends Role{
	private String[] choices = {"Edit Watch and No Fly List", "Edit Travel Agency and Customer Profile", "Close User Account"};
	private ProfileController pc = new ProfileController();
	
	public ProfileSystemManagerRole(){
		super();
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
}
