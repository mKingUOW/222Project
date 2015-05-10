/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Booking {
	private String[] customer_usernames;
	private String[] person_ids;
	private String flight_id;
	
	public Booking(){
		
	}
	
	public void addCustomers(String[] usernames){
		customer_usernames = usernames;
	}
}
