/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.ServiceController;
import com.report.ReportBuilder;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceSystemManagerRole extends NormalStaffRole{
	private String[] choices = {"Add Services", "Remove Services", "Edit Service Price", "Edit Service Availability"};
	private ServiceController sc = new ServiceController();
	
	private String[] reportsAvailable = {"Top 5 Popular Services Report"};
	
	public ServiceSystemManagerRole(){
		super();
		addChoices(choices);
		addReportChoices(reportsAvailable);
	}

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
	public void displayReportMenu() {
		String choice = allReportsAvailable.get(userReportChoice);
		
		switch(choice){
			case "Top 5 Popular Services Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.Top5PopularServicesReport, getUsername());
				break;
			default:
				super.displayReportMenu();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Service System Manager";
	}
}
