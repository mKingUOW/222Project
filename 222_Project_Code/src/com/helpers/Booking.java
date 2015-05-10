/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Booking {
	private String[] customer_usernames;
	private List<Integer> person_ids;
	private String flight_id;
	
	public Booking(){
		
	}
	
	public void addCustomers(String[] usernames){
		customer_usernames = usernames;
	}
	
	public void addPersons(List<Integer> person_ids){
		this.person_ids = person_ids;
	}
}
