/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
import com.helpers.Plane;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class provides methods pertaining to any required 
 * functionality and processing that is related to flights.
 * @author Michael Y.M. Kong
 */
public class FlightController {
	/**
	 * FlightController requires the RouteController class to query route
	 * data.
	 */
	private RouteController rc;
	
	/**
	 * FlightController requires the FleetController class to query plane
	 * data.
	 */
	private FleetController ftc;
	
	/**
	 * AirportController requires the FlightEntity class to write/read data
	 * to the database.
	 */
	private FlightEntity fe;
	
	/**
	 * Scanner allows class to use the basic input from the console.
	 */
	private Scanner in;
	
	/**
	 * Default constructor.
	 */
	public FlightController(){
            rc = new RouteController();
            ftc = new FleetController();
            fe = new FlightEntity();
            in = new Scanner(System.in);
	}
	
	/**
	 * Provides an interface for the Flight Manager to choose whether to
	 * add, edit or delete flights.
	 */
	public void manageFlightsOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Flight", "Edit Flight", "Delete Flight", "Cancel"};
		
		System.out.println("\nMANAGE FLIGHTS");
		
		for (int i = 0; i < choices.length; i++) {
			System.out.print((i + 1) + ". ");
			System.out.println(choices[i]);
		}
		
		do {
			isOkay = true;
			System.out.print("Please select an option: ");
			
			try {
				choice = in.nextInt();
				if (choice < 0 || choice > choices.length) {
					System.out.println("That option is out of range. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		in.nextLine();
		
		switch (choice){
			case 1:
				addFlight();
				break;
			case 2:
				editFlight();
				break;
			case 3:
				deleteFlight();
				break;
		}
	}
	
	/**
	 * Method called by the manageFlightsOption() method when the Flight Manager
	 * chooses to add a flight.
	 */
	private void addFlight() {
		Flight flight = new Flight();
		
		System.out.println("\nADD FLIGHT");
		
		flight.setFlightID(enterFlightId(true));
		flight.setPlane(enterPlane());
		flight.setRouteNumber(rc.enterRoute());
		flight.setDepartureTime(enterDepartureTime());
		flight.setArriveTime(enterArrivalTime());
		
		fe.addFlight(flight);
		
		setSeatPrices(flight.getFlightID());
		
		System.out.println("Flight " + flight.getFlightID() + " has been saved.\n");
	}
	
	/**
	 * Method called by the manageFlightsOption() method when the Flight Manager
	 * chooses to edit a flight.
	 */
	private void editFlight() {
		boolean isOkay;
		int option = 0;
		
		System.out.println("\nEDIT FLIGHT");
		
		Flight flight = fe.getFlight(enterFlightId(false));
                String plane_model = ftc.getPlaneModel(flight.getPlaneID());
                Map.Entry<String, String> route_points = rc.getRouteCities(flight.getRouteNumber());
		
		System.out.println("Flight ID: " + flight.getFlightID());
		System.out.println("1. Route: " + flight.getRouteNumber() + " \u2192 " + route_points.getKey() + " to " + route_points.getValue());
		System.out.println("2. Departure Time: " + flight.getDepartureTime());
		System.out.println("3. Arrival Time: " + flight.getArriveTime());
		System.out.println("4. Plane: " + plane_model);
		
		do {
			isOkay = true;
			
			System.out.print("Please select an option to edit: ");
		
			try {
				option = in.nextInt();
				
				if (option < 1 || option > 4) {
					isOkay = false;
					System.out.println("That option doesn't exist. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("That option is not valid. Please try again!\n");
			}
		} while (!isOkay);
		
		do {
                    in.nextLine();
			switch (option){
				case 1:
					flight.setRouteNumber(rc.enterRoute());
					break;
				case 2:
					flight.setDepartureTime(enterDepartureTime());
					break;
				case 3:
					flight.setArriveTime(enterArrivalTime());
					break;
				case 4:
					flight.setPlane(enterPlane());
					break;
			}
		} while (!isOkay);
		
		fe.editFlight(flight);
		
		System.out.println("Flight " + flight.getFlightID() + " has been edited.\n");
	}

	/**
	 * Method called by the manageFlightsOption() method when the Flight Manager
	 * chooses to delete a flight.
	 */
	private void deleteFlight() {
		System.out.println("\nDELETE FLIGHT");
		
		String flightId = enterFlightId(false);
		
		fe.deleteFlight(flightId);
		
		System.out.println("Flight " + flightId + " has been deleted.\n");
	}
	
	/**
	 * A UI method that allows the Flight Manager to enter a flight ID. 
	 * This method also checks whether the name of the flight ID is valid.
	 * @param check_exists True if this method should check if this flight ID exists in the database.
	 * False otherwise.
	 * @return A String which is the entered flight ID. 
	 */
	public String enterFlightId(boolean check_exists){
		boolean isOkay;
		String flightId = "";
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the flight ID (Eg: ABC1234): ");
			flightId = in.nextLine();
			
			if (check_exists) {
				if (doesFlightExist(flightId)) {
					isOkay = false;
					System.out.println("Flight " + flightId + " already exists. Please try again!\n");
				}
			} else{
				if (!doesFlightExist(flightId)) {
					isOkay = false;
					System.out.println("Flight " + flightId + " does not exist. Please try again!\n");
				}
			}
			
		} while (!isOkay);
		
		return flightId;
	}
	
	/**
	 * A UI method that allows the Flight Manager to enter a Plane. 
	 * This method also checks whether the plane model is valid.
	 * @return The Plane object that is associated with the given plane model.
	 */
	private Plane enterPlane(){
		boolean isOkay;
		Plane plane;
		String planeModel = "";
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the plane model: ");
			planeModel = in.nextLine();
			
			if (!ftc.doesModelExist(planeModel)) {
				isOkay = false;
				System.out.println("Plane model " + planeModel + " does not exist. Please try again!\n");
			}
			
		} while (!isOkay);
		
		plane = ftc.getPlane(planeModel);
		
		return plane;
	}
	
	/**
	 * A UI method that allows the Flight Manager to enter a departure time. 
	 * @return The entered departure time.
	 */
	private String enterDepartureTime(){
		System.out.print("Please enter the departure time (Eg: Fri May 01 12:00:00 EST 2015): ");
		return in.nextLine();
	}
	
	/**
	 * A UI method that allows the Flight Manager to enter a arrival time. 
	 * @return The entered arrival time.
	 */
	private String enterArrivalTime(){
		System.out.print("Please enter the arrival time (Eg: Fri May 01 12:00:00 EST 2015): ");
		return in.nextLine();
	}
	
	/**
	 * Checks whether the flight exists.
	 * @param flight_id The flight ID to check.
	 * @return True if the flight exists, false otherwise.
	 */
	private boolean doesFlightExist(String flight_id){
		return (fe.getFlight(flight_id) != null);
	}
	
	/**
	 * Gets the flight based on the given flight ID.
	 * @param flight_id The flight ID to get the Flight of.
	 * @return 
	 */
	public Flight getFlight(String flight_id){
		return fe.getFlight(flight_id);
	}
	
	/**
	 * Gets a List of Flight objects based on the given origin and destination.
	 * @param origin_airport The airport of origin.
	 * @param destination_airport The airport of destination.
	 * @return A List of Flight objects that match the origin and destination airports.
	 */
	public List<Flight> getFlights(String origin_airport, String destination_airport){
		int route_number = rc.getRouteNumber(origin_airport, destination_airport);
		
		List<Flight> flights = null;
		
		if (route_number > 0) { //the route exists
			flights = fe.getFlights(route_number);
		}
		
		return flights;
	}
	
	/**
	 * Allows user to get flights for a particular month.
	 * @param month The month of to search for.
	 * @param year The year to search for.
	 * @return A List of Flight objects for the given month.
	 */
	public List<Flight> getFlightsForMonth(String month, int year){
		List<Flight> flights_for_month = new ArrayList<>();
		List<Flight> all_flights = fe.getAllFlights();
		
		for (Flight flight: all_flights) {
			String flight_date = flight.getArriveTime();
			String flight_month = flight_date.substring(4, 7);
			int flight_year = Integer.parseInt(flight_date.substring(24));
			
			if (month.equals(flight_month) && year == flight_year) { //if this is a wanted flight
				flights_for_month.add(flight);
			}
		}
		
		return flights_for_month;
	}
	
	/**
	 * Updates the available seats of a particular flight.
	 * @param flight_id The flight ID of the flight to update the seats of.
	 * @param available_seats The current available seats for the flight.
	 */
	
	public void updateAvailableSeats(String flight_id, int[] available_seats){
		fe.updateAvailableSeats(flight_id, available_seats);
	}
	
	/**
	 * Checks if the flight with the origin airport and destination airport.
	 * @param origin_airport The origin airport.
	 * @param destination_airport The destination airport.
	 * @return True if both the origin and destination airport are not in the same country.
	 * False otherwise.
	 */
	public boolean isInternationalFlight(String origin_airport, String destination_airport){
		return rc.isInternationalRoute(origin_airport, destination_airport);
	}
	
	/**
	 * Gets the route cities for a given route number.
	 * @param routeNumber The route number to get the route cities of.
	 * @return A pair of Strings that are the names of the cities that this route covers.
	 */
	public Map.Entry<String, String> getRouteCities(int routeNumber){
		return rc.getRouteCities(routeNumber);
	}
	
	/**
	 * Gets the seat prices for a certain flight.
	 * @param flight_id The flight ID to the seat prices of.
	 * @return A double array of seat prices for the given flight.
	 */
	public double[] getSeatPrices(String flight_id){
		return fe.getSeatPrices(flight_id);
	}
	
	/**
	 * Sets the seat prices for the given flight.
	 * @param flight_id The flight ID to set the seat prices of.
	 */
	public void setSeatPrices(String flight_id){
		if (flight_id == null) {
			flight_id = enterFlightId(false);
		} else{
			System.out.println("\nSET SEAT PRICES");
		}
		double[] prices = new double[4];
		
		for (int i = 0; i < prices.length; i++) {
			prices[i] = enterPrice(i);
		}
		
		fe.setSeatPrice(flight_id, prices);
		
		System.out.println("The seat prices for flight " + flight_id + " has been updated.\n");
	}
	
	/**
	 * A UI method that allows the Flight Manager to enter the flight price. 
	 * Includes validation.
	 * @param class_type The class type to enter the price for.
	 * @return The price that was entered.
	 */
	private double enterPrice(int class_type){
		boolean isOkay;
		double price = 0.0;
		String[] class_types = {"first", "business", "premium economy", "economy"};
		
		do {
			isOkay = true;
			
			System.out.print("Enter the price for " + class_types[class_type] + " class seats: $");
			
			try {
				price = in.nextDouble();
				
				if (price < 0) {
					isOkay = false;
					System.out.println("Price must be a positive number. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid price detected. Please try again!\n");
			}
			
		} while (!isOkay);
		
		return price;
	}

	/**
	 * 
	 * @param routeNumber
	 */
	public AbstractMap.SimpleImmutableEntry<String, String> getRouteLocations(int routeNumber) {
		// TODO - implement FlightController.getRouteLocations
		throw new UnsupportedOperationException();
	}
}
