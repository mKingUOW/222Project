/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.profile.ProfileController;
import com.report.ReportBuilder;

/**
 * A class that defines what a System Administrator actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class SystemAdministratorRole extends Role{
	/**
	 * The choices available for this role.
	 */
	private String[] choices;
	
	/**
	 * Used to access the profile functionality.
	 */
	private ProfileController pc;
	
	/**
	 * Used to access the system as a Customer actor.
	 */
	private CustomerRole cr;
	
	/**
	 * Used to access the system as a Travel Agency actor.
	 */
	private TravelAgencyRole tar;
	
	/**
	 * Used to access the system as a Normal Staff actor.
	 */
	private NormalStaffRole nor;
	
	/**
	 * Used to access the system as a Flight Manager actor.
	 */
	private FlightManagerRole fmr;
	
	/**
	 * Used to access the system as a Profile System Manager actor.
	 */
	private ProfileSystemManagerRole psmr;
	
	/**
	 * Used to access the system as a Service System Manager actor.
	 */
	private ServiceSystemManagerRole ssmr;
	
	/**
	 * Used to access the system as a Reservation System Manager actor.
	 */
	private ReservationSystemManagerRole rvsmr;
	
	/**
	 * Default constructor.
	 */
	public SystemAdministratorRole(){
		super();
                choices = new String[]{"Act as Customer", "Act as Travel Agency",
                    "Act as Normal Staff", "Act as Flight Manager",
                    "Act as Profile System Manager", "Act as Service System Manager",
                    "Act as Reservation System Manager", "Act as Reporting System Manager",
                    "Edit Staff Profile", "Create Staff Profile"};
                pc = new ProfileController();
                cr = new CustomerRole();
                tar = new TravelAgencyRole();
                nor = new NormalStaffRole();
                fmr = new FlightManagerRole();
                psmr = new ProfileSystemManagerRole();
                ssmr = new ServiceSystemManagerRole();
                rvsmr = new ReservationSystemManagerRole();
		addChoices(choices);
	}

	/**
	 * Overriding method that executes the choice that was selected.
	 */
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
			case "Act as Normal Staff":
				role = nor;
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
			case "Edit Staff Profile":
				pc.editStaffProfile();
				break;
			case "Create Staff Profile":
				pc.createStaffProfile();
				break;
			default:
				super.executeChoice();
				break;
		}
		
		if (role != null) {
			while(role.isUserLoggedIn()){
				role.setUsername(getUsername());
				role.displayChoices();
				role.executeChoice();
			}
		}
	}
	
	@Override
	public String getRoleString() {
		return "System Administrator";
	}
}
