/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Ticket {
	private int booking_id;
	private int ticket_id;
	private String username;
	private int person_id;
	private String seat_number;
	
	public Ticket(){
		
	}
	
	public Ticket(int tid, String un, int pid){
		ticket_id = tid;
		username = un;
		person_id = pid;
	}
	
	public Ticket(int bid, int tid, String un, int pid){
		booking_id = bid;
		ticket_id = tid;
		username = un;
		person_id = pid;
	}
	
	public Ticket(int bid, int tid, String un, int pid, String sn){
		booking_id = bid;
		ticket_id = tid;
		username = un;
		person_id = pid;
		seat_number = sn;
	}
	
	public int getBookingId(){
		return booking_id;
	}
	
	public int getTicketId(){
		return ticket_id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public int getPersonId(){
		return person_id;
	}
	
	public String getSeatNumber(){
		return seat_number;
	}
	
	public boolean isCustomer(){
		return (username != null);
	}
	
	public boolean isPerson(){
		return (person_id != -1);
	}
}
