package com.role;

public class TravelAgencyRole extends AbstractCustomerRole{
	private String[] choices = null;
	
	public TravelAgencyRole(){
		super();
		addChoices(choices);
	}

	public void addCustomers(){
		
	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			/*
			case "Make Booking":
				break;
			*/
			default:
				super.executeChoice();
				break;
		}
	}
}