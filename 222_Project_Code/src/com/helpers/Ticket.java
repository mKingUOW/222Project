/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

import java.util.Formatter;

/**
 * Allows a information of a ticket to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Ticket {
	/**
	 * Booking ID of this ticket
	 */
	private int bookingId;
	
	/**
	 * Ticket ID of this ticket
	 */
	private int ticketId;
	
	/**
	 * Username of this ticket. Will be null if this ticket is for a person.
	 */
	private String username;
	
	/**
	 * Person ID of this ticket. Will be -1 if this ticket is for a user.
	 */
	private int personId;
	
	/**
	 * Seat number associated with this ticket.
	 */
	private String seatNumber;
	
	/**
	 * The price of this ticket.
	 */
	private double price;
	
	/**
	 * Default constructor.
	 */
	public Ticket(){
	}
	
	/**
	 * Constructor with 5 parameters. Is used mainly to save tickets to the 
	 * database.
	 * @param tid The ticket ID.
	 * @param un The username.
	 * @param pid The person ID.
	 * @param sn The seat number.
	 * @param prc The price of the ticket.
	 */
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
	
	/**
	 * Constructor with 6 parameters. Is used mainly to retrieve tickets from
	 * the database.
	 * @param tid The ticket ID.
	 * @param un The username.
	 * @param pid The person ID.
	 * @param bid The booking ID that this ticket is tied to.
	 * @param sn The seat number.
	 * @param prc The price of the ticket.
	 */
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
	
	/**
	 * Gets the booking ID of this ticket.
	 * @return The booking ID.
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking ID of this ticket.
	 * @param bookingId The booking ID.
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the ticket ID of this ticket.
	 * @return The ticket ID.
	 */
	public int getTicketId() {
		return ticketId;
	}

	/**
	 * Sets the ticket ID of this ticket.
	 * @param ticketId The ticket ID.
	 */
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * Gets the username of this ticket.
	 * @return The username. Will return null if person ID is set.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of this ticket.
	 * @param username The username.
	 */
	public void setUsername(String username) {
		if ("null".equals(username)) {
			this.username = null;
		} else{
			this.username = username;
		}
	}

	/**
	 * Gets the person ID of this ticket.
	 * @return The person ID.
	 */
	public int getPersonId() {
		return personId;
	}

	/**
	 * Sets the person ID of this ticket.
	 * @param The person ID. Will return -1 if username is set.
	 */
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	/**
	 * Gets the seat number of this ticket.
	 * @return The seat number.
	 */
	public String getSeatNumber() {
		return seatNumber;
	}

	/**
	 * Sets the seat number of this ticket.
	 * @param The seat number.
	 */
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * Gets the price of this ticket.
	 * @return The ticket price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price of this ticket.
	 * @param The ticket price.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Gets a formatted String of the ticket ID, price, username/person ID and 
	 * seat number.
	 * @return A formatted String.
	 */
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
