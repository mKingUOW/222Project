/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

import java.util.Formatter;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Booking {
	private int bookingId;
	private String flightId;
	
	public Booking(){
		
	}
	
	public Booking(int bid, String fid){
		bookingId = bid;
		flightId = fid;
	}
	
	public void setBookingId(int bid){
		bookingId = bid;
	}
	
	public void setFlightId(String fid){
		flightId = fid;
	}
	
	public int getBookingId(){
		return bookingId;
	}
	
	public String getFlightId(){
		return flightId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-10s", bookingId, flightId);
		
		return sb.toString();
	}
}
