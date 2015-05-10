/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

/**
 *
 * @author Michael Y.M. Kong
 */
public class AirportController {
	public AirportController(){
		
	}
	
	public String getAirportCode(String city_name){
		AirportEntity ae = new AirportEntity();
		
		return ae.getAirportCode(city_name); //if return null that means airport name doesn't exist
	}
}
