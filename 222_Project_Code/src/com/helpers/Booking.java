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
	private double total_price;
	
	public Booking(){
	}
	
	public Booking(int bid, String fid){
		bookingId = bid;
		flightId = fid;
	}
	
	public Booking(int bid,String fid,String b_status,double total){
		bookingId = bid;
		flightId = fid;
		booking_status = b_status;
		total_price = total;
	}
	
	public void setBookingId(int bid){
		bookingId = bid;
	}
	
	public void setFlightId(String fid){
		flightId = fid;
	}
	
	public void setTotalPrice(double total){
		total_price = total;
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
	
	public double getTotalPrice(){
		return total_price;
	}

	public void setStatus(String booking_status) {
		this.booking_status = booking_status;
	}
	
	public String getString(){
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-15s$%-15.2f", bookingId, booking_status, total_price);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-15s%-15s$%-15.2f", bookingId, flightId, booking_status, total_price);
		
		return sb.toString();
	}
}
