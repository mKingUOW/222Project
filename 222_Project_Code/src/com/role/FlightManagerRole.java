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
	private String[] choices;
	
	/**
	 * Used to access the route editing functionality.
	 */
	private RouteController rc;
	
	/**
	 * Used to access the fleet editing functionality.
	 */
	private FleetController ftc;
	
	/**
	 * Used to access the flight editing functionality.
	 */
	private FlightController fc;
	
	/**
	 * Used to access the airport editing functionality.
	 */
	private AirportController ac;
	
	/**
	 * The reports available for this role.
	 */
	private String[] reportsAvailable = {"Monthly Flight Statistics Report"};
	
	public FlightManagerRole(){
		super();
                choices = new String[]{"Manage Routes", "Manage Fleet", "Manage Flight Schedule", "Manage Airports"};
                rc = new RouteController();
                ftc = new FleetController();
                fc = new FlightController();
                ac = new AirportController();
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
