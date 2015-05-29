/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 * Allows a information of a plane to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Plane {
	/**
	 * Plane ID number for the aircraft
	 */
	private int planeID;
	/**
	 * Total number of seats for the aircraft
	 */
	private int numberAvailable;
	/**
	 * Manufacturer Model of the aircraft
	 */
	private String planeModel;
	/**
	 * Total number of first class seats for the aircraft
	 */
	private int firstClassSeats;
	/**
	 * Total number of business class seats for the aircraft
	 */
	private int businessClassSeats;
	/**
	 * Total number of premium economy class seats for the aircraft
	 */
	private int premiumEconomyClassSeats;
	/**
	 * Total number of economy class seats for the aircraft
	 */
	private int economyClassSeats;

	/**
	 * Empty Default Constructor
	 */
	public Plane() {
	}
	/**
	 * Plane Constructor
	 * @param planeID The plane ID number
	 * @param planeModel The manufacturer model of the plane
	 * @param firstClassSeats The total number of first class seats for the plane
 	 * @param businessClassSeats The total number of business class seats for the plane
	 * @param premiumEconomyClassSeats The total number of premium economy class seats for the plane
	 * @param economyClassSeats The total number of economy class seats for the plane
	 */
	public Plane(int planeID, String planeModel, int firstClassSeats,
			int businessClassSeats, int premiumEconomyClassSeats, int economyClassSeats) {
		this.planeID = planeID;
		this.planeModel = planeModel;	
		this.firstClassSeats = firstClassSeats;
		this.businessClassSeats = businessClassSeats;
		this.premiumEconomyClassSeats = premiumEconomyClassSeats;
		this.economyClassSeats = economyClassSeats;
	}
	/**
	 * Gets the total number of seats for the aircraft
	 * @return The total number of seats for the aircraft
	 */
	public int getNumberAvailable() {
		return numberAvailable;
	}
	/**
	 * Sets the total number of seats available for the aircraft
	 * @param numberAvailable The value of the total number of seats available to be set
	 */
	public void setNumberAvailable(int numberAvailable) {
		this.numberAvailable = numberAvailable;
	}
	/**
	 * Gets the plane ID for the aircraft
	 * @return The plane ID for the aircraft
	 */
	public int getPlaneID() {
		return planeID;
	}
	/**
	 * Sets the plane ID for the aircraft
	 * @return planeID The value of plane ID to be set
	 */
	public void setPlaneID(int planeID) {
		this.planeID = planeID;
	}
	/**
	 * Gets the plane model for the aircraft
	 * @return The plane model for the aircraft
	 */
	public String getPlaneModel() {
		return planeModel;
	}
	/**
	 * Gets the plane model for the aircraft
	 * @param planeModel The value of plane model to be set
	 */
	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}
	/**
	 * Gets the total number of first class seats for the aircraft
	 * @return The total number of first class seats for the aircraft
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	/**
	 * Sets the total number of first class seats for the aircraft
	 * @param firstClassSeats The value of the total number of first class seats to be set
	 */
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}
	/**
	 * Gets the total number of business class seats for the aircraft
	 * @return The total number of business class seats for the aircraft
	 */
	public int getBusinessClassSeats() {
		return businessClassSeats;
	}
	/**
	 * Sets the total number of business class seats for the aircraft
	 * @param businessClassSeats The value of the total number of business class seats to be set
	 */
	public void setBusinessClassSeats(int businessClassSeats) {
		this.businessClassSeats = businessClassSeats;
	}
	/**
	 * Gets the total number of premium economy class seats for the aircraft
	 * @return The total number of premium economy class seats for the aircraft
	 */
	public int getPremiumEconomyClassSeats() {
		return premiumEconomyClassSeats;
	}
	/**
	 * Sets the total number of premium economy class seats for the aircraft
	 * @param premiumEconomyClassSeats The value of the total number of premium economy class seats to be set
	 */
	public void setPremiumEconomyClassSeats(int premiumEconomyClassSeats) {
		this.premiumEconomyClassSeats = premiumEconomyClassSeats;
	}
	/**
	 * Gets the total number of economy class seats for the aircraft
	 * @return The total number of economy class seats for the aircraft
	 */
	public int getEconomyClassSeats() {
		return economyClassSeats;
	}
	/**
	 * Sets the total number of economy class seats for the aircraft
	 * @param economyClassSeats The value of the total number of economy class seats to be set
	 */
	public void setEconomyClassSeats(int economyClassSeats) {
		this.economyClassSeats = economyClassSeats;
	}
	/**
	 * Gets the total number of available seats for the aircraft
	 * @return An array of total numbers of seats for each class for the aircraft
	 */
	public int[] getAvailableSeats(){
		int[] seats = {firstClassSeats, businessClassSeats, premiumEconomyClassSeats, economyClassSeats};
		
		return seats;
	}
	/**
	 * Gets the total number seats for the aircraft
	 * @return The total number of seats for the aircraft
	 */
	public int getTotalSeats() {
		return businessClassSeats + firstClassSeats
				+ premiumEconomyClassSeats + economyClassSeats;
	}	
}
