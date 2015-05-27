/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.profile.ProfileController;
import com.report.ReportBuilder;

/**
 *
 * @author Michael Y.M. Kong
 */
public class SystemAdministratorRole extends Role{
	private String[] choices = {"Act as Customer", "Act as Travel Agency",
		"Act as Normal Staff", "Act as Flight Manager",
		"Act as Profile System Manager", "Act as Service System Manager",
		"Act as Reservation System Manager", "Act as Reporting System Manager",
		"Edit Staff Profile", "Create Staff Profile"};
	
	private ProfileController pc = new ProfileController();
	
	private CustomerRole cr = new CustomerRole();
	private TravelAgencyRole tar = new TravelAgencyRole();
	private NormalStaffRole nor = new NormalStaffRole();
	private FlightManagerRole fmr = new FlightManagerRole();
	private ProfileSystemManagerRole psmr = new ProfileSystemManagerRole();
	private ServiceSystemManagerRole ssmr = new ServiceSystemManagerRole();
	private ReservationSystemManagerRole rvsmr = new ReservationSystemManagerRole();
	
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
				role.displayChoices();
				role.executeChoice();
			}
		}
	}
	
	@Override
	public void displayReportMenu() {
		System.out.println("No reports available yet.\n");
	}
	
	@Override
	public void displayReport(int choice) {
		switch(choice){
			default:
		}
	}
	
	@Override
	public String getRoleString() {
		return "System Administrator";
	}
}
