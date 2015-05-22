/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Route;
import java.util.AbstractMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class RouteController {

	private AirportController ac = new AirportController();
	private RouteEntity re = new RouteEntity();
	private Scanner in = new Scanner(System.in);
	
	public RouteController(){
		
	}
	
	public void editRouteOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Route", "Edit Route", "Delete Route"};
		
		System.out.println();
		
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
	
	private void addRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		
		do {
			isOkay = true;
			
			origin = enterFlightOrigin(false);
			destination = enterFlightDestination(false);
			
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
	
	private void editRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		int option = 0;
		
		do {
			isOkay = true;
			
			origin = enterFlightOrigin(true);
			destination = enterFlightDestination(true);
			
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
				option = in.nextLine().charAt(0);
				
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
					origin = enterFlightOrigin(true);
					
					origin_code = ac.getAirportCode(origin);
										
					if (doesRouteExist(origin_code, destination_code)) {
						isOkay = false;
						System.out.println("The route already exists. Please try again!\n");
					} else{
						route.setOriginCode(origin_code);
					}
					break;
				case 2:
					destination = enterFlightDestination(true);
					
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
	
	private void deleteRoute(){
		String origin;
		String destination;
		boolean isOkay;
		String origin_code = "";
		String destination_code = "";
		
		do {
			isOkay = true;
			
			origin = enterFlightOrigin(true);
			destination = enterFlightDestination(true);
			
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
	
	private String enterFlightOrigin(boolean check){
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
	
	private String enterFlightDestination(boolean check){
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
	
	public boolean doesRouteExist(String code1, String code2){
		if(code1 != null && code2 != null){
			if (re.getRouteNumber(code1, code2) == -1) {
				return false;
			} else{
				return true;
			}
		}
		return false;
	}
	
	public int getRoute(String origin, String destination){
		String origin_code = ac.getAirportCode(origin);
		String dest_code = ac.getAirportCode(destination);
		int route_number;
		
		if (doesRouteExist(origin_code, dest_code)){ //if return null that means airport name doesn't exist
			route_number = -2;
		} else{
			route_number = re.getRouteNumber(origin_code, dest_code);
		}
		
		return route_number;
	}
	
	public AbstractMap.SimpleImmutableEntry<String, String> getRouteLocations(int routeNumber){
		AbstractMap.SimpleImmutableEntry<String, String> route_points
				= getRoutePoints(routeNumber);
		
		String origin = ac.getAirportCity(route_points.getKey());
		String destination = ac.getAirportCity(route_points.getValue());
		
		return new AbstractMap.SimpleImmutableEntry<>(origin, destination);
	}
	
	public AbstractMap.SimpleImmutableEntry<String, String> getRoutePoints(int routeNumber){
		return re.getRoutePoints(routeNumber);
	}
	
	public boolean isInternationalRoute(String origin, String destination){
		String origin_country = ac.getAirportCountry(origin);
		String destination_country = ac.getAirportCountry(destination);
		
		return (!origin_country.equals(destination_country)); //countries are not the same; therefore international
	}
}
