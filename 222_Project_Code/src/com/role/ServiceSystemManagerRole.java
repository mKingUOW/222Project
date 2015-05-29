/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.ServiceController;
import com.report.ReportBuilder;

/**
 * A class that defines what a Service System Manager actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class ServiceSystemManagerRole extends NormalStaffRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Add Services", "Remove Services", "Edit Service Price", "Edit Service Availability"};
	
	/**
	 * Used to access the service functionality.
	 */
	private ServiceController sc = new ServiceController();
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable = {"Top 5 Popular Services Report"};
	
	/**
	 * Default constructor
	 */
	public ServiceSystemManagerRole(){
		super();
		addChoices(choices);
		addReportChoices(reportsAvailable);
	}

	/**
	 * Overriding method that executes the choice that was selected.
	 */
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Add Services":
				sc.addServices();
				break;
			case "Remove Services":
				sc.removeServices();
				break;
			case "Edit Service Price":
				sc.editServicePrice();
				break;	
			case "Edit Service Availability":
				sc.editServiceAvailability();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public void executeReportChoice() {
		String choice = allReportsAvailable.get(userReportChoice);
		
		switch(choice){
			case "Top 5 Popular Services Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.Top5PopularServicesReport, getUsername());
				break;
			default:
				super.executeReportChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Service System Manager";
	}
}
