/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 * Allows a information of a service booking to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class ServiceBooking {
	/**
	 * The booking ID for this service booking.
	 */
	private int bookingId;
	
	/**
	 * The ticket ID for this service booking.
	 */
	private int ticketId;
	
	/**
	 * The service ID for this service booking.
	 */
	private int serviceId;
	
	/**
	 * Default constructor.
	 */
	public ServiceBooking(){
		
	}
	
	/**
	 * Constructor with 3 arguments.
	 * @param bid The booking ID.
	 * @param tid The ticket ID.
	 * @param sid The service ID.
	 */
	public ServiceBooking(int bid, int tid, int sid){
		bookingId = bid;
		ticketId = tid;
		serviceId = sid;
	}
	
	/**
	 * Constructor with 2 arguments.
	 * @param tid The ticket ID.
	 * @param sid The service ID.
	 */
	public ServiceBooking(int tid, int sid){
		ticketId = tid;
		serviceId = sid;
	}
	
	/**
	 * Sets the ticket ID for this service booking.
	 * @param tid The ticket ID.
	 */
	public void setTicketId(int tid){
		ticketId = tid;
	}
	
	/**
	 * Sets the booking ID for this service booking.
	 * @param bookingId The booking ID
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	
	/**
	 * Sets the service ID for this service booking.
	 * @param sid 
	 */
	public void setServiceId(int sid){
		serviceId = sid;
	}
	
	/**
	 * Gets the ticket ID for this service booking.
	 * @return The ticket ID
	 */
	public int getTicketId(){
		return ticketId;
	}
	
	/**
	 * Gets the booking ID for this service booking.
	 * @return The booking ID
	 */
	public int getBookingId() {
		return bookingId;
	}
	
	/**
	 * Gets the service ID for this service booking.
	 * @return The service ID
	 */
	public int getServiceId(){
		return serviceId;
	}
}
