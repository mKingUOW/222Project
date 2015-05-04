/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReportingSystemManagerRole extends Role{
	private String[] choices = null;
	
	public ReportingSystemManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			/*
			case "Edit Routes":
				break;
			*/
			default:
				super.executeChoice();
				break;
		}
	}
}
