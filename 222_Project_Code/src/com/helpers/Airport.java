/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 * Allows a information of an airport to passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Airport {
	/**
	 * The ID number for the airport object.
	 */
	private int airportId;
	/**
	 * The name of the airport object.
	 */
	private String airportName;
	/**
	 * The city where the airport is located.
	 */
	private String city;
	/**
	 * The country where the airport is located.
	 */
	private String country;
	/**
	 * The IATA code for the airport object.
	 */
	private String IATA;
	/**
	 * The latitude for the airport object.
	 */
	private double latitude;
	/**
	 * The longitude for the airport object.
	 */	
	private double longitude;
	/**
	 * The altitude for the airport object.
	 */
	private int altitude;
	/**
	 * The timezone for the airport object.
	 */
	private int timezone;
	/**
	 * The Daylight Savings Time for the airport object.
	 */
	private char DST;
	/**
	 * The timezone database reference for the airport object.
	 */
	private String tzDatabaseTimezone;

	/**
	 * Default constructor
	 */
	public Airport() {
	}
	
	/**
	 * Airport constructor with parameters
	 * @param airportId Airport ID number
	 * @param airportName Airport Name
	 * @param city Airport City
	 * @param country Airport Country
	 * @param IATA IATA Code
	 * @param latitude Airport Latitude
	 * @param longitude Airport Longitude
	 * @param altitude Airport Altitude
	 * @param timezone Airport Timezone
	 * @param DST Airport Daylight Savings Time
	 * @param tzDatabaseTimezone Airport Timezone Database Reference
	 * @author Michael Y.M. Kong
	 */
	public Airport(int airportId, String airportName, String city, String country, String IATA, double latitude, double longitude, int altitude, int timezone, char DST, String tzDatabaseTimezone) {
		this.airportId = airportId;
		this.airportName = airportName;
		this.city = city;
		this.country = country;
		this.IATA = IATA;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.timezone = timezone;
		this.DST = DST;
		this.tzDatabaseTimezone = tzDatabaseTimezone;
	}
	/**
	 * Gets the airportID for the airport object
	 * @return The airportID
	 */
	public int getAirportId() {
		return airportId;
	}
	/**
	 * Sets the airportID for the airport object to the parameter passed
	 * @param airportId The value of airportID to be set to
	 */
	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}
	/**
	 * Gets the airport name for the airport object
	 * @return The Airport name
	 */
	public String getAirportName() {
		return airportName;
	}
	/**
	 * Sets the airport name for the airport object to the parameter passed
	 * @param airportName The value of airport name to be set
	 */
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	/**
	 * Gets the airports city name
	 * @return The city where the airport is located
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Sets the city for the airport object to the parameter passed
	 * @param city The value of city to be set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Gets the country of the airport object
	 * @return The country of the airport
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Sets the country of the airport object to the parameter passed
	 * @param country The value of country to be set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Gets the IATA code for the airport object
	 * @return The IATA code of the airport
	 */
	public String getIATA() {
		return IATA;
	}
	/**
	 * Sets the IATA code for the airport object to the parameter passed
	 * @param IATA The value of IATA code to be set
	 */
	public void setIATA(String IATA) {
		this.IATA = IATA;
	}
	/**
	 * Gets the latitude of the airport object
	 * @return The latitude of the airport
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * Sets the latitude of the airport object to the parameter passed
	 * @param latitude The value latitude to be set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * Gets the longitude of the airport object
	 * @return The longitude of the airport
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * Sets the longitude of the airport object to the parameter passed
	 * @param longitude The value longitude to be set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * Gets the altitude of the airport object
	 * @return The altitude of the airport.
	 */
	public int getAltitude() {
		return altitude;
	}
	/**
	 * Sets the altitude of the airport object to the parameter passed
	 * @param altitude The value altitude to be set
	 */
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	/**
	 * Gets the timezone of the airport object
	 * @return The timezone of the airport
	 */
	public int getTimezone() {
		return timezone;
	}
	/**
	 * Sets the timezone of the airport object to the parameter passed
	 * @param timezone The value timezone to be set
	 */
	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
	/**
	 * Gets the Daylight Savings Time of the airport object
	 * @return The Daylight Savings Time of the airport
	 */
	public char getDST() {
		return DST;
	}
	/**
	 * Sets the Daylight Savings Time of the airport object to the parameter passed
	 * @param DST The value of DST to be set
	 */
	public void setDST(char DST) {
		this.DST = DST;
	}
	/**
	 * Gets the tz database timezone of the airport object
	 * @return The tz database timezone of the airport
	 */
	public String getTzDatabaseTimezone() {
		return tzDatabaseTimezone;
	}
	/**
	 * Sets the tz database timezone of the airport object to the parameter passed
	 * @param tzDatabaseTimezone The value tz database timezone to be set
	 */
	public void setTzDatabaseTimezone(String tzDatabaseTimezone) {
		this.tzDatabaseTimezone = tzDatabaseTimezone;
	}
}
