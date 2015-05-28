/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.BookingController;
import com.report.ReportBuilder;

/**
 *
 * @author Michael Y.M. Kong
 */
public class NormalStaffRole extends Role{
	private String[] choices = {"Cancel Customer Booking", "Make Booking for Customer", "Edit Services for Customer"};
	private BookingController bc = new BookingController();
	
	private String[] reportsAvailable = {"Flight Statistics Report"};
	
	public NormalStaffRole() {
		super();
		addChoices(choices);
		addReportChoices(reportsAvailable);
	}
	
	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Cancel Customer Booking":
				bc.cancelBooking();
				break;
			case "Make Booking for Customer":
				bc.makeBooking();
				break;
			case "Edit Services for Customer":
				bc.editServices();
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
			case "Flight Statistics Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.MonthlyFlightStatisticsReport, getUsername());
				break;
			default:
				super.displayReportMenu();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Normal Staff";
	}
}
