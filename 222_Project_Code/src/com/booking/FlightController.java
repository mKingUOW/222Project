/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
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
	private FlightEntity fe = new FlightEntity();
	private Scanner in = new Scanner(System.in);
	
	public FlightController(){
		
	}
	
	public void editFlightsOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Flight", "Edit Flight", "Delete Flight"};
		
		for (int i = 0; i < choices.length; i++) {
			System.out.print((i + 1) + ". ");
			System.out.println(choices[i]);
		}
		
		System.out.println();
		
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
		
	}

	private void editFlight() {
		
	}

	private void deleteFlight() {
		
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
