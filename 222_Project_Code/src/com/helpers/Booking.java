/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

import java.util.Formatter;

/**
 * Allows a information of a booking to passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Booking {
	
	/**
	 * The booking ID of this booking.
	 */
	private int bookingId;
	
	/*8
	 * The flight ID of this booking.
	 */
	private String flightId;
	
	/**
	 * The booking status of this booking.
	 */
	private String booking_status;
	
	/**
	 * The total price of this booking.
	 */
	private double total_price;
	
	/**
	 * The booking date of this booking.
	 */
	private String bookingDate;
	
	/**
	 * Default constructor.
	 */
	public Booking(){
	}
	
	/**
	 * Constructor with booking ID and flight ID.
	 * @param bid Booking ID
	 * @param fid Flight ID
	 */
	public Booking(int bid, String fid){
		bookingId = bid;
		flightId = fid;
	}
	
	/**
	 * Constructor with full attribute list.
	 * @param bid Booking ID
	 * @param fid Flight ID
	 * @param b_status Booking Status
	 * @param total Booking total price
	 * @param date Date of booking
	 */
	public Booking(int bid,String fid,String b_status,double total, String date){
		bookingId = bid;
		flightId = fid;
		booking_status = b_status;
		total_price = total;
		bookingDate = date;
	}
	
	/**
	 * Sets the booking ID of this booking.
	 * @param bid The booking ID.
	 */
	public void setBookingId(int bid){
		bookingId = bid;
	}
	
	/**
	 * Set the flight ID of this booking.
	 * @param fid The flight ID.
	 */
	public void setFlightId(String fid){
		flightId = fid;
	}
	
	/**
	 * Sets the total price of this booking.
	 * @param total The total price of this booking.
	 */
	public void setTotalPrice(double total){
		total_price = total;
	}
	
	/**
	 * Gets the booking ID of this booking
	 * @return The booking ID.
	 */
	public int getBookingId(){
		return bookingId;
	}
	
	/**
	 * Gets the flight ID of this booking.
	 * @return The flight ID.
	 */
	public String getFlightId(){
		return flightId;
	}

	/**
	 * Gets the status of this booking.
	 * @return The booking status.
	 */
	public String getStatus() {
		return booking_status;
	}
	
	/**
	 * Gets the total price of this booking.
	 * @return The total price of this booking.
	 */
	public double getTotalPrice(){
		return total_price;
	}

	/**
	 * Sets the status of this booking.
	 * @param booking_status The status of this booking.
	 */
	public void setStatus(String booking_status) {
		this.booking_status = booking_status;
	}

	/**
	 * Gets the booking date of this booking.
	 * @return The booking date.
	 */
	public String getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date of this booking.
	 * @param bookingDate The booking date.
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	/**
	 * Gets a formatter String with the booking ID, booking status, total price and booking date.
	 * @return A formatted String describing the booking.
	 */
	public String getString(){
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-15s$%-15.2f%-25s", bookingId, booking_status, total_price, bookingDate);
		
		return sb.toString();
	}
	
	/**
	 * Gets a formatter String with the booking ID, flight ID, booking status, total price and booking date.
	 * @return A formatted String describing the booking.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		formatter.format("%-15s%-15s%-15s$%-15.2f%-25s", bookingId, flightId, booking_status, total_price, bookingDate);
		
		return sb.toString();
	}
}
