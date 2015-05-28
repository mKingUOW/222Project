/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.report;

import com.booking.BookingController;
import com.helpers.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReportBuilder {
	/**
	 * An enumeration denoting the report types available.
	 */
	public static enum ReportType {
		FlightHistoryReport, FlightStatisticsReport,
		MonthlyRevenueReport, Top5PopularServicesReport,
		BookingsForMonthReport
	}
	
	/**
	 * Private default constructor disallows instantiation.
	 */
	private ReportBuilder(){
	}
	
	/**
	 * Builds and displays the report.
	 */
	public static void displayReport(ReportType report_type, String username){
		switch(report_type){
			case FlightHistoryReport:
				displayFlightHistoryReport(username);
				break;
			case FlightStatisticsReport:
				displayFlightStatisticsReport();
				break;
			case MonthlyRevenueReport:
				displayMonthlyRevenueReport();
				break;
			case Top5PopularServicesReport:
				displayTop5PopularServicesReport();
				break;
			case BookingsForMonthReport:
				displayBookingsForMonthReport();
				break;
			default:
				System.out.println("Report type has not been set.\n");
				break;
		}
	}
	
	/**
	 * Displays the flight history report.
	 */
	private static void displayFlightHistoryReport(String username){
		BookingController bc = new BookingController();
		bc.setUsername(username);
		
		displayHeader("FLIGHT HISTORY REPORT");
		
		bc.viewBookings();
	}
	
	/**
	 * Displays the flight statistics report.
	 */
	private static void displayFlightStatisticsReport(){
		displayHeader("FLIGHT STATISTICS REPORT");
	}
	
	/**
	 * Displays the monthly financial report.
	 */
	private static void displayMonthlyRevenueReport(){
		displayHeader("MONTHLY REVENUE REPORT");
	}
	
	/**
	 * Displays the all time popular services report.
	 */
	private static void displayTop5PopularServicesReport(){
		BookingController bc = new BookingController();
		
		List<Map.Entry<Service, Integer>> popular_service
				= bc.getTop5ServicesBooked();
		
		displayHeader("TOP 5 POPULAR SERVICES REPORT");
		
		int i = 1;
		
		System.out.printf("%-4s%-12s%-20s%-14s%-15s\n", "#", "Service ID", "Name", "Cost (AUD)", "Availability");
		for (Map.Entry<Service, Integer> entry: popular_service) {
			System.out.printf("%-4s", i + ". ");
			System.out.println(entry.getKey().getServiceString());
			System.out.println("Number of times this service has been booked: " + entry.getValue());
			System.out.println();
			i++;
		}
	}
	
	/**
	 * Displays the bookings for month report.
	 */
	private static void displayBookingsForMonthReport(){
		BookingController bc = new BookingController();
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		
		displayHeader("BOOKINGS FOR THE MONTH OF " + 3 + " REPORT");
	}
	
	/**
	 * Displays the header.
	 * @param header_title The header title to display.
	 */
	private static void displayHeader(String header_title){
		System.out.println("---------------" + header_title + "---------------");
		System.out.println("Report generated at: " + getCurrentDateString());
	}
	
	/**
	 * Gets the current date for the report.
	 * @return The current date in a hh:mm:ssa dd/MMM/yyyy (zzz) format.
	 */
	private static String getCurrentDateString(){
		Date current_datetime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss a dd/MMM/yyyy (zzz)");
		
		return fmt.format(current_datetime);
	} 
}
