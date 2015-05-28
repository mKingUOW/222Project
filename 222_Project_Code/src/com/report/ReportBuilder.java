/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.report;

import com.booking.BookingController;
import com.booking.ServiceController;
import com.helpers.Booking;
import com.helpers.Service;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides static methods to generate different types of reports.
 * @author Michael Y.M. Kong
 */
public class ReportBuilder {
	/**
	 * An enumeration denoting the report types available.
	 */
	public static enum ReportType {
		FlightHistoryReport, MonthlyFlightStatisticsReport,
		MonthlyRevenueReport, Top5PopularServicesReport,
		BookingsForMonthReport
	}
	
	/**
	 * A private class allowing the internal methods to conveniently pass data amongst each other.
	 */
	private static class FlightStats{
		/**
		 * Number of bookings.
		 */
		private int numberOfBookings;
		
		/**
		 * Number of tickets.
		 */
		private int numberOfTickets;
		
		/**
		 * Total revenue.
		 */
		private double totalRevenue;
		
		/**
		 * Total value of services.
		 */
		private double totalServices;

		/**
		 * Default constructor.
		 */
		public FlightStats() {
		}

		/**
		 * Gets number of bookings attribute.
		 * @return Number of bookings.
		 */
		public int getNumberOfBookings() {
			return numberOfBookings;
		}

		/**
		 * Sets number of bookings attribute.
		 * @param numberOfBookings Number of bookings.
		 */
		public void setNumberOfBookings(int numberOfBookings) {
			this.numberOfBookings = numberOfBookings;
		}

		/**
		 * Gets number of tickets attribute.
		 * @return Number of tickets.
		 */
		public int getNumberOfTickets() {
			return numberOfTickets;
		}

		/**
		 * Sets number of tickets attribute.
		 * @param numberOfTickets Number of tickets.
		 */
		public void setNumberOfTickets(int numberOfTickets) {
			this.numberOfTickets = numberOfTickets;
		}

		/**
		 * Gets total revenue attribute.
		 * @return Total revenue.
		 */
		public double getTotalRevenue() {
			return totalRevenue;
		}

		/**
		 * Sets the total revenue attribute.
		 * @param totalRevenue Total revenue.
		 */
		public void setTotalRevenue(double totalRevenue) {
			this.totalRevenue = totalRevenue;
		}

		/**
		 * Gets the total value of services attribute.
		 * @return Total value of services.
		 */
		public double getTotalServices() {
			return totalServices;
		}

		/**
		 * Sets the total value of services attribute.
		 * @param totalServices Total value of services.
		 */
		public void setTotalServices(double totalServices) {
			this.totalServices = totalServices;
		}
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
		System.out.println("Generating report......\n\n\n\n");
		
		switch(report_type){
			case FlightHistoryReport:
				displayFlightHistoryReport(username);
				break;
			case MonthlyFlightStatisticsReport:
				displayMonthlyFlightStatisticsReport();
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
	 * Displays the monthly flight statistics report.
	 */
	private static void displayMonthlyFlightStatisticsReport(){
		BookingController bc = new BookingController();
		
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		displayHeader("FLIGHT STATISTICS REPORT FOR THE MONTH OF " + month + " " + year);
	}
	
	/**
	 * Displays the monthly financial report.
	 */
	private static void displayMonthlyRevenueReport(){
		BookingController bc = new BookingController();
		
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		FlightStats stats = getFlightStatsForMonth(month, year, bc);
		
		displayHeader("MONTHLY REVENUE REPORT FOR THE MONTH OF " + month + " " + year);
		
		System.out.printf("Average money spent on services per ticket: $%.2\n", stats.getTotalServices() / stats.getNumberOfTickets());
		System.out.printf("Total revenue from services: $%.2\n", stats.getTotalServices());
		System.out.printf("Average revenue per booking: $%.2\n", stats.getTotalRevenue() / stats.getTotalRevenue());
		System.out.printf("Monthly total revenue: $%.2\n", stats.getTotalRevenue());
	}
	
	/**
	 * Gets the Flight statistics based on the given information.
	 * @param month The month to get the statistics for.
	 * @param year The 
	 * @param bc
	 * @return 
	 */
	private static FlightStats getFlightStatsForMonth(String month, int year, BookingController bc){
		ServiceController sc = new ServiceController();
		
		FlightStats stats = new FlightStats();
		
		int number_of_bookings;
		int number_of_tickets = 0;
		double total_revenue = 0;
		double total_services = 0;
		
		List<Map.Entry<Booking, List<Ticket>>> bookings_and_tickets
				= bc.getBookingsForMonth(month, year);
		
		number_of_bookings = bookings_and_tickets.size();
		
		for (Map.Entry<Booking, List<Ticket>> entry: bookings_and_tickets) {
			Booking booking = entry.getKey();
			List<Ticket> tickets = entry.getValue();
			total_revenue += booking.getTotalPrice();
			number_of_tickets += tickets.size();
			
			for (Ticket ticket: tickets) {
				List<ServiceBooking> services_booked = bc.getServicesBookedForTicket(ticket);
				
				for (ServiceBooking service_booking: services_booked) {
					Service service = sc.getService(service_booking.getServiceId());
					
					total_services += service.getCost();
				} //end services_booked for loop
				
			} //end tickets for loop
			
		} //end bookings_and_tickets for loop
		
		stats.setNumberOfBookings(number_of_bookings);
		stats.setNumberOfTickets(number_of_tickets);
		stats.setTotalRevenue(total_revenue);
		stats.setTotalServices(total_services);
		return stats;
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
		
		int i = 1;
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		List<Map.Entry<Booking, List<Ticket>>> bookings_and_tickets
				= bc.getBookingsForMonth(month, year);
		
		displayHeader("BOOKING REPORT FOR THE MONTH OF " + month + " " + year);
		
		System.out.printf("%-4s%-15s%-15s%-15s%-15s%-25s", "#", "Booking ID",
				"Flight ID", "Booking Status", "Total Price (AUD)", "Booking Date");
		
		for (Map.Entry<Booking, List<Ticket>> entry: bookings_and_tickets) {
			int j = 1;
			
			System.out.printf("%-4s", i + ". ");
			System.out.println(entry.getKey().toString());
			
			List<Ticket> tickets = entry.getValue();
			
			System.out.println();
			
			System.out.printf("%-4s%-4s%-14s%-25s%-12s", "#", "Ticket ID",
					"Price (AUD)", "Username/Person ID", "Seat Number");
			for (Ticket ticket: tickets) {
				System.out.printf("%-4s", j + ". ");
				System.out.println(ticket.toString());
				j++;
			}
			i++;
			System.out.println();
		}
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
	
	//private 
}
