package com.role;

import com.booking.BookingController;
import com.profile.ProfileController;
import com.report.ReportBuilder;

/**
 * An abstract class that defines the common behavior that the customers
 * (customers and travel agencies) can do.
 * @author Michael Y.M. Kong
 */
public abstract class AbstractCustomerRole extends Role{
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account", "View My Bookings"};
	
	/**
	 * Used to access the booking functionality.
	 */
	private BookingController bc = new BookingController();
	
	/**
	 * Used to access the profile functionality.
	 */
	private ProfileController pc = new ProfileController();
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable = {"Flight History Report"};
	
	/**
	 * Default constructor
	 */
	public AbstractCustomerRole(){
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
		bc.setUsername(getUsername());
		
		switch(choice){
			case "Edit Services":
				bc.editServices(getUsername());
				break;
			case "Close Account":
				if (pc.closeAccount(getUsername())) {
					setUserLoggedOut();
				}
				break;
			case "Cancel Booking":
				bc.cancelBooking();
				break;
			case "Edit Account":
				pc.editAccount(getUsername());
				break;
			case "View My Bookings":
				System.out.println("\nVIEW BOOKINGS");
				bc.viewBookings();
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
		String choice = allReportsAvailable.get(userReportChoice);
		
		switch(choice){
			case "Flight History Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.FlightHistoryReport, getUsername());
				break;
			default:
				super.executeReportChoice();
				break;
		}
	}
}