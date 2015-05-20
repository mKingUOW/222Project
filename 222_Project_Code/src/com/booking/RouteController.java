/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

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
		
	}
	
	private void editRoute(){
		
	}
	
	private void deleteRoute(){
		
	}
	
	public int getRoute(String origin, String destination){
		String origin_code = ac.getAirportCode(origin);
		String dest_code = ac.getAirportCode(destination);
		int route_number;
		
		if (origin_code == null || dest_code == null){ //if return null that means airport name doesn't exist
			route_number = -1;
		} else{
		
			route_number = re.getRoute(origin_code, dest_code);
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
