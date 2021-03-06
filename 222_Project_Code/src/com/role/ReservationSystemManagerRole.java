/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.BookingController;
import com.booking.FlightController;
import com.profile.ProfileController;
import com.report.ReportBuilder;

/**
 * A class that defines what a Reservation System Manager actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class ReservationSystemManagerRole extends NormalStaffRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices;
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable;
	
	/**
	 * Used to access the profile functionality.
	 */
	private ProfileController pc;
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc;
	
	/**
	 * Used to access the flight functionality.
	 */
	private FlightController fc;
	
	/**
	 * Default constructor
	 */
	public ReservationSystemManagerRole(){
		super();
                choices = new String[]{"Set Ticket Prices", "Move Passengers Between Flights",
                    "Change Passenger Seating", "Edit Watch and No Fly List",
                    "Set Frequent Flier Point Discount Ratio", "Set Cancellation Fee"};
                reportsAvailable = new String[]{"Monthly Revenue Report", "Bookings For Month Report"};
                pc = new ProfileController();
                bc = new BookingController();
                fc = new FlightController();
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
			case "Set Ticket Prices":
				fc.setSeatPrices(null);
				break;
			case "Move Passengers Between Flights":
				bc.movePassengers();
				break;
			case "Change Passenger Seating":
				bc.changePassengerSeating();
				break;	
			case "Edit Watch and No Fly List":
				pc.editWatchAndNoFlyList();
				break;
			case "Set Frequent Flier Point Discount Ratio":
				bc.setDiscountRatio();
				break;
			case "Set Cancellation Fee":
				bc.setCancellationFee();
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
			case "Monthly Revenue Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.MonthlyRevenueReport, getUsername());
				break;
			case "Bookings For Month Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.BookingsForMonthReport, getUsername());
				break;
			default:
				super.executeReportChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Reservation System Manager";
	}
}
