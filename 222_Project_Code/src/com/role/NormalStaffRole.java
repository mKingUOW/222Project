/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.BookingController;
import com.report.ReportBuilder;

/**
 * A class that defines what a Normal Staff actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class NormalStaffRole extends Role{
	/**
	 * The choices available for this role.
	 */
	private String[] choices;
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc;
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable;
	
	/**
	 * Default constructor
	 */
	public NormalStaffRole() {
		super();
                choices = new String[]{"Cancel Customer Booking", "Make Booking for Customer", "Edit Services for Customer"};
                bc = new BookingController();
                reportsAvailable = new String[]{};
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
	
	/**
	 * Overriding method that executes the report choice that was selected.
	 */
	@Override
	public void executeReportChoice() {
                String choice = "";
            
                if (allReportsAvailable.isEmpty()) {
                    return;
                } else{
                    choice = allReportsAvailable.get(userReportChoice);
                }
                
		switch(choice){
			default:
				super.executeReportChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Normal Staff";
	}
}
