/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
import java.util.AbstractMap;
import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FlightController {
	private RouteController rc = new RouteController();
	private FlightEntity fe = new FlightEntity();
	
	public FlightController(){
		
	}
	
	public List<Flight> getFlights(String origin, String destination){
		int route_number = rc.getRoute(origin, destination);
		
		List<Flight> flights = null;
		
		if (route_number != -1) { //the route exists
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
