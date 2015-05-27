/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.report;

import com.booking.BookingController;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReportBuilder {
	/**
	 * An enumeration denoting the report types available.
	 */
	private static enum ReportType {
		FlightHistoryReport, FlightStatisticsReport,
		AllTimeFinancialReport
	}
	
	/**
	 * Points to the type of report.
	 */
	private static ReportType type = null;
	
	/**
	 * Private default constructor disallows instantiation.
	 */
	private ReportBuilder(){
	}
	
	/**
	 * Sets the builder to generate a flight history report.
	 */
	public static void setFlightHistoryReport(){
		type = ReportType.FlightHistoryReport;
	}
	
	/**
	 * Sets the builder to generate a flight statistics report.
	 */
	public static void setFlightStatisticsReport(){
		type = ReportType.FlightStatisticsReport;
	}
	
	/**
	 * Sets the builder to generate an all time financial report.
	 */
	public static void setAllTimeFinancialReport(){
		type = ReportType.AllTimeFinancialReport;
	}
	
	/**
	 * Builds and displays the report.
	 */
	public static void displayReport(String username){
		switch(type){
			case FlightHistoryReport:
				displayFlightHistoryReport(username);
				break;
			case FlightStatisticsReport:
				displayFlightStatisticsReport();
				break;
			case AllTimeFinancialReport:
				displayAllTimeFinancialReport();
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
	 * Displays the all time financial report.
	 */
	private static void displayAllTimeFinancialReport(){
		displayHeader("ALL TIME FINANCIAL REPORT");
	}
	
	/**
	 * Displays the header.
	 * @param header_title The header title to display.
	 */
	private static void displayHeader(String header_title){
		System.out.println("---------------" + header_title + "---------------");
		System.out.println(getCurrentDateString());
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
