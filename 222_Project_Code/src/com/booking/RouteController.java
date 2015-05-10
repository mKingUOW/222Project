/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

/**
 *
 * @author Michael Y.M. Kong
 */
public class RouteController {

	private AirportController ac = new AirportController();
	
	public RouteController(){
		
	}
	
	public int getRoute(String origin, String destination){
		String origin_code = ac.getAirportCode(origin);
		String dest_code = ac.getAirportCode(destination);
		int route_number;
		
		if (origin_code == null || dest_code == null){ //if return null that means airport name doesn't exist
			route_number = -1;
		} else{
			RouteEntity re = new RouteEntity();
		
			route_number = re.getRoute(origin_code, dest_code);
		}
		
		return route_number;
	}
}
