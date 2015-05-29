package com.role;

import com.booking.BookingController;
import com.profile.ProfileController;
import com.report.ReportBuilder;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account", "View My Bookings"};
	private BookingController bc = new BookingController();
	private ProfileController pc = new ProfileController();
	
	private String[] reportsAvailable = {"Flight History Report"};
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
		addReportChoices(reportsAvailable);
	}

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