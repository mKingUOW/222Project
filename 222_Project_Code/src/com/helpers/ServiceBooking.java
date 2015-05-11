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
	private int ticket_id;
	private int service_id;
	
	public ServiceBooking(){
		
	}
	
	public ServiceBooking(int tid, int sid){
		ticket_id = tid;
		service_id = sid;
	}
	
	public void setTicketId(int tid){
		ticket_id = tid;
	}
	
	public void setServiceId(int sid){
		service_id = sid;
	}
	
	public int getTicketId(){
		return ticket_id;
	}
	
	public int getServiceId(){
		return service_id;
	}
}
