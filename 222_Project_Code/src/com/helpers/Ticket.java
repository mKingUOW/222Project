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
	
	public Ticket(int tid, String un, int pid, String sn, double prc){
		ticketId = tid;
		if ("null".equals(un)) {
			username = null;
		} else{
			username = un;
		}
		personId = pid;
		seatNumber = sn;
		price = prc;
	}
	
	public Ticket(int tid, String un, int pid, int bid, String sn, double prc){
		bookingId = bid;
		ticketId = tid;
		if ("null".equals(un)) {
			username = null;
		} else{
			username = un;
		}
		personId = pid;
		seatNumber = sn;
		price = prc;
	}
	
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if ("null".equals(username)) {
			this.username = null;
		} else{
			this.username = username;
		}
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
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
		String format_string = "%-4s$%-14.2f%-25s%-12s";
		
		if (username != null) {
			formatter.format(format_string, ticketId, price, username, seatNumber);
		} else{
			formatter.format(format_string, ticketId, price, personId, seatNumber);
		}
		
		return sb.toString();
	}
}
