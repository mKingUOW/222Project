/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.report;

import com.booking.BookingController;
import com.booking.FleetController;
import com.booking.FlightController;
import com.booking.RouteController;
import com.booking.ServiceController;
import com.helpers.Booking;
import com.helpers.Flight;
import com.helpers.Service;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
	 * Private default constructor disallows instantiation.
	 */
	private ReportBuilder(){
	}
	
	/**
	 * Builds and displays the report.
         * @param report_type The report
         * @param username
	 */
	public static void displayReport(ReportType report_type, String username){		
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
         * @param username The username for the flight history to display.
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
		
		int i;
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		FlightStats stats = getFlightStatsForMonth(month, year, bc);
		
		List<Map.Entry<Map.Entry<String, String>, Integer>> top_3_routes = stats.getTop3Routes();
		List<Map.Entry<String, Integer>> top_3_planes = stats.getTop3Planes();
		
		displayHeader("FLIGHT STATISTICS REPORT FOR THE MONTH OF " + month.toUpperCase() + " " + year);
		
		System.out.println("Average number of flights per week: " + (stats.getNumberOfFlights() / 4) + " (Note: Assuming a typical 4 week month)");
		System.out.println("Number of flights scheduled during this month: " + stats.getNumberOfFlights());
		
		i = 1;
		System.out.println();
		System.out.println("The Top 3 Flight Routes for this Month: ");
		for (Map.Entry<Map.Entry<String, String>, Integer> entry: top_3_routes) {
			Map.Entry<String, String> route_cities = entry.getKey();
			int count = entry.getValue();
			
			System.out.print(i + ". ");
			System.out.print(route_cities.getKey() + " to " + route_cities.getValue());
			System.out.println(" (Count: " + count + ")");
			i++;
		}
		
		i = 1;
		System.out.println();
		System.out.println("The Top 3 Used Planes for this Month: ");
		for (Map.Entry<String, Integer> entry: top_3_planes) {
			String model = entry.getKey();
			int count = entry.getValue();
			
			System.out.print(i + ". ");
			System.out.println(model + " (Count: " + count + ")");
			i++;
		}
	}
	
	/**
	 * Gets the Flight statistics based on the given information.
	 * @param month The month to get the statistics for.
	 * @param year The year to get the statistics for.
	 * @param bc The BookingController object to retrieve the data from.
	 * @return A FlightStats object containing all the computed statistics.
	 */
	private static FlightStats getFlightStatsForMonth(String month, int year, BookingController bc){
		FlightController fc = new FlightController();
		RouteController rc = new RouteController();
		FleetController ftc = new FleetController();
		
		FlightStats stats = new FlightStats();
		HashMap<Integer, Integer> route_hashmap = new HashMap<>();
		HashMap<Integer, Integer> plane_hashmap = new HashMap<>();
		
		int no_of_flights;
		
		//basically a List of a Map pair corresponding to a Route city pair and the count
		//the Route city pair contains the names of the cities of that route
		List<Map.Entry<Map.Entry<String, String>, Integer>> top_3_routes = new ArrayList(3);
		List<Map.Entry<String, Integer>> top_3_planes = new ArrayList(3);
		
		//gets the flights for the given month and year
		List<Flight> flights = fc.getFlightsForMonth(month, year);
		
		//get the number of flights
		no_of_flights = flights.size();
		
		//for all flights
		for (Flight flight: flights) {
			//first if-else computes the route with the highest number of flights
			
			//if the route number doesn't exist, put it in the map
			if (!route_hashmap.containsKey(flight.getRouteNumber())) { 
				route_hashmap.put(flight.getRouteNumber(), 1);
			} else{
				//if the route number exists, increment the count
				
				Integer count = route_hashmap.get(flight.getRouteNumber());
				count++;
				route_hashmap.put(flight.getRouteNumber(), count);
			}
			
			//second if-else computes the plane model with
			//the highest number of usage
			
			//if the plane ID doesn't exist, put it in the map
			if (!plane_hashmap.containsKey(flight.getPlaneID())) {
				plane_hashmap.put(flight.getPlaneID(), 1);
			} else{
				//if the plane ID exists, increment the count
				
				Integer count = plane_hashmap.get(flight.getPlaneID());
				count++;
				plane_hashmap.put(flight.getPlaneID(), count);
			}
		}
		
		//sort the route_hashmap by the value (which is the count)
		Map<Integer, Integer> route_map = sortByValues(route_hashmap);
		
		//get the keys of the sorted map in the sorted order
		List<Integer> route_keys = new ArrayList<>(route_map.keySet());
		
		//for the first 3 keys, get the route cities and the route count
		for (int i = 0; i < 3; i++) {
			int route_number = route_keys.get(i);
			int route_count = route_hashmap.get(route_number);
			
			Map.Entry<String, String> cities = rc.getRouteCities(route_number);
			
			top_3_routes.add(new AbstractMap.SimpleEntry<>(cities, route_count));
		}
		
		//sort the plane_hashmap by the value (which is the count)
		Map<Integer, Integer> plane_map = sortByValues(plane_hashmap);
		
		//get the keys of the sorted map in the sorted order
		List<Integer> plane_keys = new ArrayList<>(plane_map.keySet());
		
		//for the first 3 keys, get the plane model and the plane count
		for (int i = 0; i < 3; i++) {
			int plane_id = plane_keys.get(i);
			int plane_count = plane_hashmap.get(plane_id);
			
			String model = ftc.getPlaneModel(plane_id);
			
			top_3_planes.add(new AbstractMap.SimpleEntry<>(model, plane_count));
		}
		
		//fill up the statistics object
		stats.setNumberOfFlights(no_of_flights);
		stats.setTop3Planes(top_3_planes);
		stats.setTop3Routes(top_3_routes);
		
		return stats;
	}
	
	/**
	 * Displays the monthly financial report.
	 */
	private static void displayMonthlyRevenueReport(){
		BookingController bc = new BookingController();
		
		String month = bc.enterMonth();
		int year = bc.enterYear();
		
		FlightRevenue stats = getFlightRevenueForMonth(month, year, bc);
		
		displayHeader("MONTHLY REVENUE REPORT FOR THE MONTH OF " + month + " " + year);
		
		System.out.printf("Average money spent on services per ticket: $%.2f\n", stats.getTotalServices() / stats.getNumberOfTickets());
		System.out.printf("Total revenue from services: $%.2f\n", stats.getTotalServices());
		System.out.printf("Average revenue per booking: $%.2f\n", stats.getTotalRevenue() / stats.getNumberOfBookings());
		System.out.printf("Monthly total revenue: $%.2f\n", stats.getTotalRevenue());
	}
	
	/**
	 * Gets the Flight revenue based on the given information.
	 * @param month The month to get the revenue data for.
	 * @param year The year to get the revenue data  for.
	 * @param bc The BookingController object to retrieve the data from.
	 * @return A FlightRevenue object containing all the computed revenue data.
	 */
	private static FlightRevenue getFlightRevenueForMonth(String month, int year, BookingController bc){
		ServiceController sc = new ServiceController();
		
		FlightRevenue stats = new FlightRevenue();
		
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
                        Service service = entry.getKey();
                    
			System.out.printf("%-4s", i + ". ");
			System.out.println(service.getServiceString());
			System.out.println("Number of times this service has been booked: " + entry.getValue());
                        System.out.printf("Total earnings from this service: $%.2f\n", service.getCost() * entry.getValue());
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
		
		displayHeader("BOOKING REPORT FOR THE MONTH OF " + month.toUpperCase() + " " + year);
		
		for (Map.Entry<Booking, List<Ticket>> entry: bookings_and_tickets) {
			int j = 1;
			
			System.out.printf("%-4s%-15s%-15s%-15s%-16s%-25s\n", "#", "Booking ID",
				"Flight ID", "Booking Status", "Total Cost", "Booking Date");
			System.out.printf("%-4s", i + ". ");
			System.out.println(entry.getKey().toString());
			
			List<Ticket> tickets = entry.getValue();
			
			System.out.println();
			
			System.out.println("\tTickets for this booking:");
			System.out.printf("\t%-4s%-4s%-15s%-25s%-12s\n", "#", "ID",
					"Price (AUD)", "Username/Person ID", "Seat Number");
			for (Ticket ticket: tickets) {
				System.out.printf("\t%-4s", j + ". ");
				System.out.println(ticket.toString());
				j++;
			}
			i++;
			System.out.println();
		}
		
		if (bookings_and_tickets.isEmpty()) {
			System.out.println("There are no bookings for " + month + " " + year);
		}
		
	}
	
	/**
	 * Displays the header.
	 * @param header_title The header title to display.
	 */
	private static void displayHeader(String header_title){
		System.out.println("Generating report......\n\n\n\n");
		System.out.println("---------------" + header_title + "---------------");
		System.out.println("Report generated at: " + getCurrentDateString());
		System.out.println();
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
	
	/**
	 * Code taken from: http://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
	 * Allows sorting by values on a HashMap
	 * @param map The HashMap to sort.
	 * @return A by-value-sorted version of the given HashMap.
	 */
	private static HashMap sortByValues(HashMap map) { 
       List list = new ArrayList(map.entrySet());
       // Defined Custom Comparator here
       Collections.sort(list, new Comparator() {
			@Override
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o2)).getValue())
                  .compareTo(((Map.Entry) (o1)).getValue());
            }
       });

       // Here I am copying the sorted list in HashMap
       // using LinkedHashMap to preserve the insertion order
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }
}


/**
 * A self contained class allowing the ReportBuilder class to conveniently pass
 * flight revenue data amongst its methods.
 */
class FlightRevenue{
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
	public FlightRevenue() {
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
 * A self contained class allowing the ReportBuilder class to conveniently pass
 * flight statistics data amongst its methods.
 */
class FlightStats{
	
	/**
	 * Number of flights.
	 */
	private int numberOfFlights;
	
	/**
	 * A List of Map Entry objects containing another Map Entry containing
	 * the names of the cities of that route, and the count of that route.
	 */
	private List<Map.Entry<Map.Entry<String, String>, Integer>> top3Routes;
	
	/**
	 * A List of Map Entry objects containing the plane model and the count
	 * of that plane model.
	 */
	private List<Map.Entry<String, Integer>> top3Planes;
	
	/**
	 * Default constructor
	 */
	public FlightStats() {
	}
	
	/**
	 * Gets number of flights.
	 * @return The number of flights.
	 */
	public int getNumberOfFlights() {
		return numberOfFlights;
	}

	/**
	 * Sets the number of flights.
	 * @param numberOfFlights The number of flights.
	 */
	public void setNumberOfFlights(int numberOfFlights) {
		this.numberOfFlights = numberOfFlights;
	}

	/**
	 * Gets the top 3 routes.
	 * @return The top 3 routes.
	 */
	public List<Map.Entry<Map.Entry<String, String>, Integer>> getTop3Routes() {
		return top3Routes;
	}

	/**
	 * Sets the top 3 routes.
	 * @param top3Routes The top 3 routes.
	 */
	public void setTop3Routes(List<Map.Entry<Map.Entry<String, String>, Integer>> top3Routes) {
		this.top3Routes = top3Routes;
	}

	/**
	 * Gets the top 3 planes.
	 * @return The top 3 planes.
	 */
	public List<Map.Entry<String, Integer>> getTop3Planes() {
		return top3Planes;
	}

	/**
	 * Sets the top 3 planes.
	 * @param top3Planes The top 3 planes.
	 */
	public void setTop3Planes(List<Map.Entry<String, Integer>> top3Planes) {
		this.top3Planes = top3Planes;
	}
}