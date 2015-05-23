/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
import com.helpers.Plane;
import java.util.AbstractMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FlightController {
	private RouteController rc = new RouteController();
	private FleetController ftc = new FleetController();
	private FlightEntity fe = new FlightEntity();
	private Scanner in = new Scanner(System.in);
	
	public FlightController(){
		
	}
	
	public void editFlightsOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Flight", "Edit Flight", "Delete Flight", "Cancel"};
		
		System.out.println();
		
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
	
	private void addFlight() {
		Flight flight = new Flight();
		
		flight.setFlightID(enterFlightId(true));
		flight.setPlane(enterPlane());
		flight.setRouteNumber(rc.enterRoute());
		flight.setDepartureTime(enterDepartureTime());
		flight.setArriveTime(enterArrivalTime());
		
		fe.addFlight(flight);
		
		System.out.println("Flight " + flight.getFlightID() + " has been saved.\n");
	}
	
	private void editFlight() {
		boolean isOkay;
		int option = 0;
		
		Flight flight = fe.getFlight(enterFlightId(false));
		
		System.out.println("Flight ID: " + flight.getFlightID());
		System.out.println("1. Route: " + flight.getRouteNumber());
		System.out.println("2. Departure Time: " + flight.getDepartureTime());
		System.out.println("3. Arrival Time: " + flight.getArriveTime());
		System.out.println("4. Plane: " + flight.getPlane().getPlaneModel());
		
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

	private void deleteFlight() {
		String flightId = enterFlightId(false);
		
		fe.deleteFlight(flightId);
		
		System.out.println("Flight " + flightId + " has been deleted.\n");
	}
	
	private String enterFlightId(boolean check_exists){
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
	
	private String enterDepartureTime(){
		System.out.print("Please enter the departure time (Eg: Fri May 01 12:00:00 EST 2015): ");
		return in.nextLine();
	}
	
	private String enterArrivalTime(){
		System.out.print("Please enter the arrival time (Eg: Fri May 01 12:00:00 EST 2015): ");
		return in.nextLine();
	}
	
	private boolean doesFlightExist(String flight_id){
		return (fe.getFlight(flight_id) != null);
	}
	
	public List<Flight> getFlights(String origin, String destination){
		int route_number = rc.getRoute(origin, destination);
		
		List<Flight> flights = null;
		
		if (route_number > 0) { //the route exists
			flights = fe.getFlights(route_number);
		}
		
		return flights;
	}
	
	public void updateAvailableSeats(String flight_id, int[] available_seats){
		fe.updateAvailableSeats(flight_id, available_seats);
	}
	
	public boolean isInternationalFlight(String origin, String destination){
		return rc.isInternationalRoute(origin, destination);
	}
	
	public AbstractMap.SimpleImmutableEntry<String, String> getRouteLocations(int routeNumber){
		return rc.getRouteLocations(routeNumber);
	}
	
	public double[] getSeatPrices(String flight_id){
		return new double[]{20, 15, 10, 5};
	}
}
