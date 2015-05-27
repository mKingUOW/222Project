/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.AirportController;
import com.booking.FleetController;
import com.booking.FlightController;
import com.booking.RouteController;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FlightManagerRole extends NormalStaffRole{
	private String[] choices = {"Edit Routes", "Edit Fleet", "Edit Flight Schedule", "Edit Airports"};
	private RouteController rc = new RouteController();
	private FleetController ftc = new FleetController();
	private FlightController fc = new FlightController();
	private AirportController ac = new AirportController();
	
	public FlightManagerRole(){
		super();
		addChoices(choices);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Edit Routes":
				rc.editRouteOption();
				break;
			case "Edit Fleet":
				ftc.editFleetOption();
				break;
			case "Edit Flight Schedule":
				fc.editFlightsOption();
				break;	
			case "Edit Airports":
				ac.editAirportsOption();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Flight Manager";
	}
}
