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
	 * An enumeration denoting the report types available
	 */
	private static enum ReportType {
		FlightHistoryReport, FlightStatisticsReport
	}
	
	/**
	 * Points to the type of report
	 */
	private static ReportType type = null;
	
	/**
	 * Private default constructor disallows instantiation
	 */
	private ReportBuilder(){
	}
	
	public static void setFlightHistoryReport(){
		type = ReportType.FlightHistoryReport;
	}
	
	public static void setFlightStatisticsReport(){
		type = ReportType.FlightStatisticsReport;
	}
	
	public static void displayReport(){
		switch(type){
			case FlightHistoryReport:
				break;
			case FlightStatisticsReport:
				break;
			default:
				System.out.println("Report type has not been set.\n");
				break;
		}
	}
	
	private static void displayFlightHistoryReport(){
		System.out.println("---------------FLIGHT HISTORY REPORT---------------");
		System.out.println(getCurrentDateString());
	}
	
	private static void displayFlightStatisticsReport(){
		System.out.println("---------------FLIGHT STATISTICS REPORT---------------");
		System.out.println(getCurrentDateString());
	}
	
	private static String getCurrentDateString(){
		Date current_datetime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ssa dd/MMM/yyyy (zzz)");
		
		return fmt.format(current_datetime);
	} 
}
