package com.helpers;

import java.util.Formatter;
import java.util.Objects;

/**
 * Allows a information of a service to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Service {
	/**
	 * Service Id number for the service
	 */
	private int serviceID;
	/**
	 * Service name for the service
	 */
	private String name;
	/**
	 * Service cost for the service
	 */
	private double cost;
	/**
	 * Service availability for the service e.g. international, domestic, all etc
	 */
	private String availability;	

	/**
	 * Empty Default Constructor
	 */
	public Service() {
	}
	/**
	 * Constructor with parameters
	 * @param sid The service ID number
	 * @param itm The service item name
	 * @param co The cost of the service
	 * @param avail The availability of the service
	 */
	public Service(int sid,String itm,double co,String avail){
		this.serviceID = sid;
		this.name = itm;
		this.cost = co;
		this.availability = avail;
	}
	/**
	 * Sets the service ID number for the service
	 * @param sid The value of the service ID number to be set
	 */
	public void setServiceID(int sid){
		this.serviceID = sid;
	}
	/**
	 * Sets the name for the service
	 * @param sid The value of the service ID number to be set
	 */
	public void setName(String itm){
		this.name = itm;
	}
	/**
	 * Sets the cost for the service
	 * @param co The value of the cost to be set
	 */
	public void setCost(double co){
		this.cost = co;
	}
	/**
	 * Sets the availability for the service
	 * @param avail The value of availability to be set
	 */
	public void setAvailability(String avail){
		this.availability = avail;
	}
	/**
	 * Gets the service ID number for the service
	 * @return The service ID number
	 */
	public int getServiceID(){
		return this.serviceID;
	}
	/**
	 * Gets the name for the service
	 * @return The service name
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Gets the cost for the service
	 * @return The cost of the service
	 */
	public double getCost(){
		return this.cost;
	}
	/**
	 * Gets the availability of the service
	 * @return The availability of the service
	 */
	public String getAvailability(){
		return this.availability;
	}
	/**
	 * Gets the name and cost for the service in a formatted string
	 * @return The formatted string
	 */
	public String getString(){
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-20s$%-14.2f", name, cost);
		
		return sb.toString();
	}
	/**
	 * Gets the service ID, name, cost and availability for the service in a formatted string
	 * @return The formatted string
	 */
	public String getServiceString(){
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-12s%-20s$%-14.2f%-15s", serviceID, name, cost, availability);
		
		return sb.toString();
	}
	
	/**
	 * Gets the name, cost and availability for the service in a formatted string
	 * @return The formatted string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-20s$%-14.2f%-15s", name, cost, availability);
		
		return sb.toString();
	}

	/**
	 * Equals method to test equality
	 * @param obj Object to test against.
	 * @return True if the given object is equal to this object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Service other = (Service) obj;
		if (this.serviceID != other.serviceID) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
			return false;
		}
		if (!Objects.equals(this.availability, other.availability)) {
			return false;
		}
		return true;
	}
}
