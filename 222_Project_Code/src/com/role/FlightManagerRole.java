/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.role;

import com.booking.AirportController;
import com.booking.FleetController;
import com.booking.FlightController;
import com.booking.RouteController;
import com.report.ReportBuilder;

/**
 * A class that defines what a Flight Manager actor can perform in the system.
 * @author Michael Y.M. Kong
 */
public class FlightManagerRole extends NormalStaffRole{
	/**
	 * The choices available for this role.
	 */
	private String[] choices = {"Manage Routes", "Manage Fleet", "Manage Flight Schedule", "Manage Airports"};
	
	/**
	 * Used to access the route editing functionality.
	 */
	private RouteController rc = new RouteController();
	
	/**
	 * Used to access the fleet editing functionality.
	 */
	private FleetController ftc = new FleetController();
	
	/**
	 * Used to access the flight editing functionality.
	 */
	private FlightController fc = new FlightController();
	
	/**
	 * Used to access the airport editing functionality.
	 */
	private AirportController ac = new AirportController();
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable = {"Monthly Flight Statistics Report"};
	
	public FlightManagerRole(){
		super();
		addChoices(choices);
		addReportChoices(reportsAvailable);
	}

	@Override
	public void executeChoice(){
		String choice = allChoices.get(userChoice);
		
		switch(choice){
			case "Manage Routes":
				rc.manageRouteOption();
				break;
			case "Manage Fleet":
				ftc.manageFleetOption();
				break;
			case "Manage Flight Schedule":
				fc.manageFlightsOption();
				break;	
			case "Manage Airports":
				ac.manageAirportsOption();
				break;
			default:
				super.executeChoice();
				break;
		}
	}
	
	@Override
	public void executeReportChoice() {
		String choice = allReportsAvailable.get(userReportChoice);
		
		switch(choice){
			case "Monthly Flight Statistics Report":
				ReportBuilder.displayReport(ReportBuilder.ReportType.MonthlyFlightStatisticsReport, getUsername());
				break;
			default:
				super.executeReportChoice();
				break;
		}
	}
	
	@Override
	public String getRoleString() {
		return "Flight Manager";
	}
}
