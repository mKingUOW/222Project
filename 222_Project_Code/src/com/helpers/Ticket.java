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
public class Ticket {
	private int bookingId;
	private int ticketId;
	private String username;
	private int personId;
	private String seatNumber;
	private double price;
	
	public Ticket(){
		
	}
	
	public Ticket(int tid, String un, int pid, String sn){
		ticketId = tid;
		username = un;
		personId = pid;
	}
	
	public Ticket(int bid, int tid, String un, int pid, String sn){
		bookingId = bid;
		ticketId = tid;
		username = un;
		personId = pid;
		seatNumber = sn;
	}
	
	public int getBookingId(){
		return bookingId;
	}
	
	public int getTicketId(){
		return ticketId;
	}
	
	public String getUsername(){
		return username;
	}
	
	public int getPersonId(){
		return personId;
	}
	
	public String getSeatNumber(){
		return seatNumber;
	}
	
	public boolean isCustomer(){
		return (username != null);
	}
	
	public boolean isPerson(){
		return (personId != -1);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		String format_string = "%-4s%-30s%-12s";
		
		if (username != null) {
			formatter.format(format_string, ticketId, username, seatNumber);
		} else{
			formatter.format(format_string, ticketId, personId, seatNumber);
		}
		
		return sb.toString();
	}
}
