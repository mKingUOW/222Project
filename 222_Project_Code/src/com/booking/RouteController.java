/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Route;
import java.util.AbstractMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * This class provides methods pertaining to any required 
 * functionality and processing that is related to routes.
 * @author Michael Y.M. Kong
 */
public class RouteController {

	/**
	 * RouteController requires the AirportController class to query airport
	 * data.
	 */
	private AirportController ac = new AirportController();
	
	/**
	 * RouteController requires the RouteEntity class to write/read data
	 * to the database.
	 */
	private RouteEntity re = new RouteEntity();
	
	/**
	 * Scanner object to use the standard in from the console.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Default constructor
	 */
	public RouteController(){
	}
	
	/**
	 * UI method to allow the user to choose whether to add, edit or delete a
	 * route. This method will then call the appropriate function upon user's
	 * choice.
	 */
	public void manageRouteOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Route", "Edit Route", "Delete Route", "Cancel"};
		
		System.out.println("\nMANAGE ROUTES");
		
		for (int i = 0; i < choices.length; i++) {
			System.out.print((i + 1) + ". ");
			System.out.println(choices[i]);
		}
		
		do {
			isOkay = true;
			System.out.println("Please select an option: ");
			
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
				addRoute();
				break;
			case 2:
				editRoute();
				break;
			case 3:
				deleteRoute();
				break;
		}
	}
	
	/**
	 * UI function to allow the user to create a new route.
	 */
	private void addRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		
		System.out.println("\nADD ROUTE");
		
		do {
			isOkay = true;
			
			origin = enterRouteOrigin(false);
			destination = enterRouteDestination(false);
			
			origin_code = ac.getAirportCode(origin);
			destination_code = ac.getAirportCode(destination);

			if (doesRouteExist(origin_code, destination_code)) {
				isOkay = false;
				System.out.println("The route from " + origin + " to " + destination + " already exists. Please try again!");
			}
		} while (!isOkay);
		
		Route route = new Route(origin_code, destination_code); //create new Route
		route.setCodeShare(enterCodeshare());
		route.setStops(enterStops());

		re.addRoute(route); //add new route to database
		
		System.out.println("A new route from " + origin + " to " + destination + " has been created.\n");
	}
	
	/**
	 * UI function to allow the user to edit an existing route.
	 */
	private void editRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		int option = 0;
		
		System.out.println("\nEDIT ROUTE");
		
		do {
			isOkay = true;
			
			origin = enterRouteOrigin(true);
			destination = enterRouteDestination(true);
			
			origin_code = ac.getAirportCode(origin);
			destination_code = ac.getAirportCode(destination);

			if (!doesRouteExist(origin_code, destination_code)) {
				isOkay = false;
				System.out.println("The route from " + origin + " to " + destination + " does not exist. Please try again!");
			}
		} while (!isOkay);
		
		Route route = re.getRoute(origin_code, destination_code);
		
		System.out.println("1. Origin: " + route.getOriginCode());
		System.out.println("2. Destination: " + route.getDestinationCode());
		System.out.println("3. Codeshare: " + route.getCodeShare());
		System.out.println("4. Stops: " + route.getStops());
		
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
					origin = enterRouteOrigin(true);
					
					origin_code = ac.getAirportCode(origin);
										
					if (doesRouteExist(origin_code, destination_code)) {
						isOkay = false;
						System.out.println("The route already exists. Please try again!\n");
					} else{
						route.setOriginCode(origin_code);
					}
					break;
				case 2:
					destination = enterRouteDestination(true);
					
					destination_code = ac.getAirportCode(destination);
										
					if (doesRouteExist(origin_code, destination_code)) {
						isOkay = false;
						System.out.println("The route already exists. Please try again!\n");
					} else{
						route.setDestinationCode(destination_code);
					}
					break;
				case 3:
					route.setCodeShare(enterCodeshare());
					break;
				case 4:
					route.setStops(enterStops());
					break;
			}
			
		} while (!isOkay);
		
		re.editRoute(route);
		
		System.out.println("The route has been edited.\n");
	}
	
	/**
	 * UI function to allow the user to delete an existing route.
	 */
	private void deleteRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		
		System.out.println("\nDELETE ROUTE");
		
		do {
			isOkay = true;
			
			origin = enterRouteOrigin(true);
			destination = enterRouteDestination(true);
			
			origin_code = ac.getAirportCode(origin);
			destination_code = ac.getAirportCode(destination);

			if (!doesRouteExist(origin_code, destination_code)) {
				isOkay = false;
				System.out.println("The route from " + origin + " to " + destination + " does not exist. Please try again!");
			}
		} while (!isOkay);
		
		re.deleteRoute(origin_code, destination_code);
		
		System.out.println("The route from " + origin + " to " + destination + " has been deleted.\n");
	}
	
	/**
	 * UI function to allow the user to enter a new route.
	 */
	public int enterRoute(){
		boolean isOkay;
		int route_number;
		
		do {	
			isOkay = true;
			
			String origin = enterRouteOrigin(true);
			String destination = enterRouteDestination(true);
			
			route_number = getRouteNumber(origin, destination);
			
			if (route_number < 0) {
				isOkay = false;
				System.out.println("The route from " + origin + " to " + destination + " does not exist. Please try again!");
			}
		} while (!isOkay);
		
		return route_number;
	}
	
	/**
	 * UI function to allow the user to enter the route origin.
	 * @param check True if this method should check whether the origin airport
	 * exists or not.
	 * @return The entered route origin.
	 */
	private String enterRouteOrigin(boolean check){
		boolean isOkay;
		String origin;
		
		do {
			isOkay = true;
			
			System.out.print("\nPlease enter the route origin of choice (airport name): ");
			origin = in.nextLine();

			if (check) {
				isOkay = ac.doesAirportExist(origin);
			
				if (!isOkay) {
					System.out.println("There is no airport in " + origin + ". Please try again!\n");
				}
			}
			
		} while (!isOkay);
		
		return origin;
	}
	
	/**
	 * UI function to allow the user to enter the route destination.
	 * @param check True if this method should check whether the destination airport
	 * exists or not.
	 * @return The entered route destination.
	 */
	private String enterRouteDestination(boolean check){
		boolean isOkay;
		String destination;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the route destination of choice (airport name): ");
			destination = in.nextLine();

			if (check) {
				isOkay = ac.doesAirportExist(destination);

				if (!isOkay) {
					System.out.println("There is no airport in " + destination + ". Please try again!\n");
				}
			}
			
		} while (!isOkay);
		
		return destination;
	}
	
	/**
	 * UI function to allow the user to enter whether this route is a
	 * code share route.
	 * @return Y or N denoting whether this is a code share flight.
	 */
	private char enterCodeshare(){
		boolean isOkay;
		char codeShare;
		
		do {
			isOkay = true;
			System.out.print("Please enter whether this is a codeshare flight (Y/N): ");
			codeShare = in.nextLine().charAt(0);
			
			if (Character.toUpperCase(codeShare) != 'Y' && Character.toUpperCase(codeShare) != 'N') {
				isOkay = false;
				System.out.println("Please enter only Y or N. Please try again!\n");
			}
		} while (!isOkay);
		
		return Character.toUpperCase(codeShare);
	}
	
	/**
	 * UI function to allow the user to enter the number of stops on this route.
	 * @return The number of stops for the route.
	 */
	private int enterStops(){
		boolean isOkay;
		int stops;
		
		do {
			isOkay = true;
			System.out.print("Please enter how many stops this route has: ");
			stops = in.nextInt();
			
			if (stops < 0) {
				isOkay = false;
				System.out.println("Please enter a positive number only. Please try again!\n");
			}
		} while (!isOkay);
		
		return stops;
	}
	
	/**
	 * Checks whether the route for the given codes exist or not.
	 * @param code1 The first airport code
	 * @param code2 The second airport code
	 * @return True if the route already exists. False otherwise.
	 */
	public boolean doesRouteExist(String code1, String code2){
		if(code1 == null || code2 == null){
			return false;
		}
		if (re.getRouteNumber(code1, code2) == -1) {
			return false;
		} 
		
		return true;
	}
	
	/**
	 * Gets the route number based on the origin and destination airports.
	 * @param origin_airport The origin airport.
	 * @param destination_airport The destination airport.
	 * @return The route number matching the origin and destination airports.
	 * -2 if route is not found.
	 */
	public int getRouteNumber(String origin_airport, String destination_airport){
		String origin_code = ac.getAirportCode(origin_airport);
		String dest_code = ac.getAirportCode(destination_airport);
		int route_number;
		
		if (doesRouteExist(origin_code, dest_code)){ //if return null that means airport name doesn't exist
			route_number = re.getRouteNumber(origin_code, dest_code);
		} else{
			route_number = -2;
		}
		
		return route_number;
	}
	
	/**
	 * Gets the cities that are at either ends of the given route number.
	 * @param routeNumber The route number to search for.
	 * @return A pair of Strings. If the route number does not exist, a pair
	 * of empty Strings will be returned.
	 */
	public Map.Entry<String, String> getRouteCities(int routeNumber){
		Map.Entry<String, String> route_points = getRoutePointsCodes(routeNumber);
		
		String origin_city = ac.getAirportCity(route_points.getKey());
		String destination_city = ac.getAirportCity(route_points.getValue());
		
		return new AbstractMap.SimpleImmutableEntry<>(origin_city, destination_city);
	}
	
	/**
	 * Gets the codes for either ends of the given route number.
	 * @param routeNumber The route number to search for.
	 * @return A pair of Strings. If the route number does not exist, a pair
	 * of empty Strings will be returned.
	 */
	public Map.Entry<String, String> getRoutePointsCodes(int routeNumber){
		return re.getRoutePointsCodes(routeNumber);
	}
	
	/**
	 * Checks whether the route is an international route based on the given
	 * origin and destination airport.
	 * @param origin_airport The origin airport
	 * @param destination_airport The destination airport
	 * @return True if the route is an international route. False otherwise.
	 */
	public boolean isInternationalRoute(String origin_airport, String destination_airport){
		String origin_country = ac.getAirportCountry(origin_airport);
		String destination_country = ac.getAirportCountry(destination_airport);
		
		return (!origin_country.equals(destination_country)); //countries are not the same; therefore international
	}
}
