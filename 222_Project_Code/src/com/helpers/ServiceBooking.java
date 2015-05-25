/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceBooking {
	private int bookingId;
	private int ticketId;
	private int serviceId;
	
	public ServiceBooking(){
		
	}
	
	public ServiceBooking(int bid, int tid, int sid){
		bookingId = bid;
		ticketId = tid;
		serviceId = sid;
	}
	
	public ServiceBooking(int tid, int sid){
		ticketId = tid;
		serviceId = sid;
	}
	
	public void setTicketId(int tid){
		ticketId = tid;
	}
	
	public void setServiceId(int sid){
		serviceId = sid;
	}
	
	public int getTicketId(){
		return ticketId;
	}
	
	public int getServiceId(){
		return serviceId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
}
