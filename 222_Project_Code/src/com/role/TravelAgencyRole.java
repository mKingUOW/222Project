package com.role;

public class TravelAgencyRole extends AbstractCustomerRole{

	public TravelAgencyRole(){
		super();
		addChoices(null);
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