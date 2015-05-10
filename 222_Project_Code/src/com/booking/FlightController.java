/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FlightController {
	private RouteController rc = new RouteController();
	
	public FlightController(){
		
	}
	
	public List<Flight> getFlights(String origin, String destination){
		int route_number = rc.getRoute(origin, destination);
		List<Flight> flights = null;
		
		if (route_number != -1) { //the route exists
			FlightEntity fe = new FlightEntity();
			
			flights = fe.getFlights(route_number);
		}
		
		return flights;
	}
}
