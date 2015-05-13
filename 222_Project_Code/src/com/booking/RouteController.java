/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import java.util.AbstractMap;

/**
 *
 * @author Michael Y.M. Kong
 */
public class RouteController {

	private AirportController ac = new AirportController();
	private RouteEntity re = new RouteEntity();
	
	public RouteController(){
		
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
	
	public AbstractMap.SimpleImmutableEntry<String, String> getRoutePoints(int routeNumber){
		return re.getRoutePoints(routeNumber);
	}
	
	public boolean isInternationalRoute(String origin, String destination){
		String origin_country = ac.getAirportCountry(origin);
		String destination_country = ac.getAirportCountry(destination);
		
		return (!origin_country.equals(destination_country)); //countries are not the same; therefore international
	}
}
