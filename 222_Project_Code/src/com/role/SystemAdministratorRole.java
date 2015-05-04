/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class SystemAdministratorRole extends Role{
	private String[] choices = null;
	
	public SystemAdministratorRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			/**/
			default:
				super.executeChoice();
				break;
		}
	}
}
