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
	private String[] choices = {"Act as Customer", "Act as Travel Agency",
		"Act as Flight Manager", "Act as Profile System Manager",
		"Act as Service System Manager", "Act as Reservation System Manager",
		"Act as Reporting System Manager", "Edit Staff Profile"};
	
	private CustomerRole cr = new CustomerRole();
	private TravelAgencyRole tar = new TravelAgencyRole();
	private FlightManagerRole fmr = new FlightManagerRole();
	private ProfileSystemManagerRole psmr = new ProfileSystemManagerRole();
	private ServiceSystemManagerRole ssmr = new ServiceSystemManagerRole();
	private ReservationSystemManagerRole rvsmr = new ReservationSystemManagerRole();
	private ReportingSystemManagerRole rpsmr = new ReportingSystemManagerRole();
	
	public SystemAdministratorRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		Role role = null;
		
		switch(choice){
			case "Act as Customer":
				role = cr;
				break;
			case "Act as Travel Agency":
				role = tar;
				break;
			case "Act as Flight Manager":
				role = fmr;
				break;
			case "Act as Profile System Manager":
				role = psmr;
				break;
			case "Act as Service System Manager":
				role = ssmr;
				break;
			case "Act as Reservation System Manager":
				role = rvsmr;
				break;
			case "Act as Reporting System Manager":
				role = rpsmr;
				break;
			case "Edit Staff Profile":
				break;
			default:
				super.executeChoice();
				break;
		}
		
		if (role != null) {
			while(role.isUserLoggedIn()){
				role.displayChoices();
				role.executeChoice();
			}
		}
		
	}
}
