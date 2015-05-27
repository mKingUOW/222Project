package com.role;

import com.booking.BookingController;
import com.profile.ProfileController;
import com.report.ReportBuilder;

public abstract class AbstractCustomerRole extends Role{

	private String[] choices = {"Edit Services", "Close Account", "Cancel Booking", "Edit Account", "View My Bookings"};
	private BookingController bc = new BookingController();
	private ProfileController pc = new ProfileController();
	
	public AbstractCustomerRole(){
		super();
		addChoices(choices);
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
				bc.viewBookings();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public void displayReportMenu() {
		System.out.println("1. Flight History Report");
	}
	
	@Override
	public void displayReport(int choice) {
		switch(choice){
			case 1:
				ReportBuilder.setFlightHistoryReport();
				ReportBuilder.displayReport(getUsername());
				break;
		}
	}
}