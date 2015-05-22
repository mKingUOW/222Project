/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Plane {
	private int planeID;
	private int numberAvailable;
	private String planeModel;
	private int firstClassSeats;
	private int businessClassSeats;
	private int premiumEconomyClassSeats;
	private int economyClassSeats;

	public Plane() {
	}

	public Plane(int planeID, String planeModel, int firstClassSeats,
			int businessClassSeats, int premiumEconomyClassSeats, int economyClassSeats) {
		this.planeID = planeID;
		this.planeModel = planeModel;
		this.firstClassSeats = firstClassSeats;
		this.businessClassSeats = businessClassSeats;
		this.premiumEconomyClassSeats = premiumEconomyClassSeats;
		this.economyClassSeats = economyClassSeats;
	}

	public int getNumberAvailable() {
		return numberAvailable;
	}

	public void setNumberAvailable(int numberAvailable) {
		this.numberAvailable = numberAvailable;
	}

	public int getPlaneID() {
		return planeID;
	}

	public void setPlaneID(int planeID) {
		this.planeID = planeID;
	}

	public String getPlaneModel() {
		return planeModel;
	}

	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}

	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}

	public int getBusinessClassSeats() {
		return businessClassSeats;
	}

	public void setBusinessClassSeats(int businessClassSeats) {
		this.businessClassSeats = businessClassSeats;
	}

	public int getPremiumEconomyClassSeats() {
		return premiumEconomyClassSeats;
	}

	public void setPremiumEconomyClassSeats(int premiumEconomyClassSeats) {
		this.premiumEconomyClassSeats = premiumEconomyClassSeats;
	}

	public int getEconomyClassSeats() {
		return economyClassSeats;
	}

	public void setEconomyClassSeats(int economyClassSeats) {
		this.economyClassSeats = economyClassSeats;
	}


	public int getTotalSeats() {
		return businessClassSeats + firstClassSeats
				+ premiumEconomyClassSeats + economyClassSeats;
	}	
}
