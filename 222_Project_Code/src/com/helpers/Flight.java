package com.helpers;

import java.util.Formatter;

/**
 * Allows a information of a flight to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Flight {
	/**
	 * Flight ID for the flight
	 */
	private String flightID;
	/**
	 * Route object containing route details for the flight
	 */
	private Route route = new Route();
	/**
	 * Departure time for the flight
	 */
	private String departureTime;
	/**
	 * Arrival time for the flight
	 */
	private String arriveTime;
	/**
	 * Plane object containing details of an aircraft for the flight
	 */
	private Plane plane = new Plane();
	
	/**
	 * Flight Constructor
	 * @param fid The flight ID
	 * @param pid The Plane ID number
	 * @param rnum The route number
	 * @param depTime The departure time for the flight
	 * @param arrTime The arrival time for the flight
	 * @param fc The number of first class seats for the flight
	 * @param bc The number of business class seats for the flight
	 * @param pec The number of premium economy seats available for the flight
	 * @param ec The number of economy seats available for the flight
	 */
	public Flight(String fid,int pid,int rnum,String depTime,String arrTime,int fc,int bc,int pec,int ec){
		this.flightID = fid;
		this.route.setRouteNumber(rnum);
		this.departureTime = depTime;
		this.arriveTime = arrTime;
		this.plane.setPlaneID(pid);
		this.plane.setFirstClassSeats(fc);
		this.plane.setBusinessClassSeats(bc);
		this.plane.setPremiumEconomyClassSeats(pec);
		this.plane.setEconomyClassSeats(ec);
	}
	/**
	 * Empty Default Flight Constructor
	 */
	public Flight(){
		
	}
	/**
	 * Sets Flight ID for the flight
	 * @param fid The value of flight ID to be set
	 */
	public void setFlightID(String fid){
		this.flightID = fid;
	}
	/**
	 * Sets Plane ID for the flight
	 * @param pid The value of plane ID to be set
	 */
	public void setPlaneID(int pid){
		this.plane.setPlaneID(pid);
	}
	/**
	 * Sets route number for the flight
	 * @param rnum The value of route number ID to be set
	 */
	public void setRouteNumber(int rnum){
		this.route.setRouteNumber(rnum);
	}
	/**
	 * Sets the departure time for the flight
	 * @param depTime The value of departure time to be set
	 */
	public void setDepartureTime(String depTime){
		this.departureTime = depTime;
	}
	/**
	 * Sets arrival time for the flight
	 * @param arrTime The value of arrival time to be set
	 */
	public void setArriveTime(String arrTime){
		this.arriveTime = arrTime;
	}
	/**
	 * Sets the number first class seats for the flight
	 * @param fc The value of first class seats to be set
	 */
	public void setFirstClassSeats(int fc){
		this.plane.setFirstClassSeats(fc);
	}
	/**
	 * Sets the number business class seats for the flight
	 * @param bc The value of business class seats to be set
	 */
	public void setBusinessClassSeats(int bc){
		this.plane.setBusinessClassSeats(bc);
	}
	/**
	 * Sets the number premium economy class seats for the flight
	 * @param pec The value of premium economy class seats to be set
	 */
	public void setPremiumEconomyClassSeats(int pec){
		this.plane.setPremiumEconomyClassSeats(pec);
	}
	/**
	 * Sets the number economy class seats for the flight
	 * @param ec The value of economy class seats to be set
	 */
	public void setEconomyClassSeats(int ec){
		this.plane.setEconomyClassSeats(ec);
	}
	/**
	 * Gets Flight ID for the flight
	 * @return The flight ID of the flight
	 */
	public String getFlightID(){
		return this.flightID;
	}
	/**
	 * Gets plane ID for the flight
	 * @return The plane ID of the flight
	 */
	public int getPlaneID(){
		return this.plane.getPlaneID();
	}
	/**
	 * Gets route number for the flight
	 * @return The route number of the flight
	 */
	public int getRouteNumber(){
		return this.route.getRouteNumber();
	}
	/**
	 * Gets departure time for the flight
	 * @return The departure time of the flight
	 */
	public String getDepartureTime(){
		return this.departureTime;
	}
	/**
	 * Gets arrival time for the flight
	 * @return The arrival time of the flight
	 */
	public String getArriveTime(){
		return this.arriveTime;
	}
	/**
	 * Sets the plane object for the flight
	 * @param plane The value of plane object to be set
	 */
	public void setPlane(Plane plane){
		this.plane = plane;
	}
	/**
	 * Gets plane object for the flight
	 * @return The plane object of the flight
	 */
	public Plane getPlane(){
		return this.plane;
	}
	/**
	 * Gets the number of first class seats for the flight
	 * @return The first class seats of the flight
	 */
	public int getFirstClassSeats(){
		return this.plane.getFirstClassSeats();
	}
	/**
	 * Gets the number of business class seats for the flight
	 * @return The business class seats of the flight
	 */
	public int getBusinessClassSeats(){
		return this.plane.getBusinessClassSeats();
	}
	/**
	 * Gets the number of premium economy class seats for the flight
	 * @return The premium economy class seats of the flight
	 */
	public int getPremiumEconomyClassSeats(){
		return this.plane.getPremiumEconomyClassSeats();
	}
	/**
	 * Gets the number of economy class seats for the flight
	 * @return The economy class seats of the flight
	 */
	public int getEconomyClassSeats(){
		return this.plane.getEconomyClassSeats();
	}
	/**
	 * Gets the number of available seats for the flight
	 * @return An array of seats available for the flight
	 */
	public int[] getAvailableSeats(){
		int[] seats = this.plane.getAvailableSeats();
		
		return seats;
	}
	/**
	 * Returns a formatted string consisting of flightID, departure time and arrival time
	 * @return a formatted string consisting of flightID, departure time and arrival time
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		
		formatter.format("%-15s%-35s%-35s", this.flightID, this.departureTime, this.arriveTime);
		
		return builder.toString();
	}
}
