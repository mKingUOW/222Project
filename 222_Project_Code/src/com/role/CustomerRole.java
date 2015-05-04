package com.role;

public class CustomerRole extends AbstractCustomerRole{
	private String[] choices = null;
			
	public CustomerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			default:
				super.executeChoice();
				break;
		}
	}
}