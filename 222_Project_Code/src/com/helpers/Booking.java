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
	private String booking_status;
	
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

	public String getStatus() {
		return booking_status;
	}

	public void setStatus(String booking_status) {
		this.booking_status = booking_status;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-10s%-10s", bookingId, flightId, booking_status);
		
		return sb.toString();
	}
}
