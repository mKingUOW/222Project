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
	private int booking_id;
	private String flight_id;
	
	public Booking(){
		
	}
	
	public Booking(int bid, String fid){
		booking_id = bid;
		flight_id = fid;
	}
	
	public void setBookingId(int bid){
		booking_id = bid;
	}
	
	public void setFlightId(String fid){
		flight_id = fid;
	}
	
	public int getBookingId(){
		return booking_id;
	}
	
	public String getFlightId(){
		return flight_id;
	}
}
