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
	private AirportEntity ae = new AirportEntity();
	
	public AirportController(){
		
	}
	
	public void editAirportsOption(){
		
	}
	
	public String getAirportCode(String city_name){
		return ae.getAirportCode(city_name); //if return null that means airport name doesn't exist
	}
	
	public String getAirportCountry(String city_name){
		return ae.getAirportCountry(city_name); //if return null that means airport name doesn't exist
	}
	
	public String getAirportCity(String airport_code){
		return ae.getAirportCity(airport_code);
	}
}
