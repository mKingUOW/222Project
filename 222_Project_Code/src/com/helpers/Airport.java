/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Airport {
	private int airportId;
	private String airportName;
	private String city;
	private String country;
	private String IATA;
	private double latitude;		
	private double longitude;
	private int altitude;
	private int timezone;
	private char DST;
	private String tzDatabaseTimezone;

	public int getAirportId() {
		return airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIATA() {
		return IATA;
	}

	public void setIATA(String IATA) {
		this.IATA = IATA;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public char getDST() {
		return DST;
	}

	public void setDST(char DST) {
		this.DST = DST;
	}

	public String getTzDatabaseTimezone() {
		return tzDatabaseTimezone;
	}

	public void setTzDatabaseTimezone(String tzDatabaseTimezone) {
		this.tzDatabaseTimezone = tzDatabaseTimezone;
	}
}
