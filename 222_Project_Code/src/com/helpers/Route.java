/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 * Allows a information of a route to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Route {
	/**
	 * Route number for the route
	 */
	private int routeNumber;
	/**
	 * Airport origin code for the route
	 */
	private String originCode;
	/**
	 * Airport destination code for the route
	 */
	private String destinationCode;
	/**
	 * Codeshare status for the route
	 */
	private char codeShare;
	/**
	 * Stops for the route
	 */
	private int stops;

	/**
	 * Empty Default Constructor
	 */
	public Route() {
	}
	/**
	 * Route Constructor with parameters
	 * @param originCode The origin airport code
	 * @param destinationCode The destination airport code
	 */
	public Route(String originCode, String destinationCode) {
		this.originCode = originCode;
		this.destinationCode = destinationCode;
	}
	/**
	 * Gets the route number for the route
	 * @return The route number for the route
	 */
	public int getRouteNumber() {
		return routeNumber;
	}
	/**
	 * Sets the route number for the route
	 * @param routeNumber The value of route number to be set
	 */
	public void setRouteNumber(int routeNumber) {
		this.routeNumber = routeNumber;
	}
	/**
	 * Gets the origin airport code for the route
	 * @return The origin airport code for the route
	 */
	public String getOriginCode() {
		return originCode;
	}
	/**
	 * Sets the origin airport code for the route
	 * @param originCode The value of origin airport code to be set
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	/**
	 * Gets the destination airport code for the route
	 * @return The destination airport code for the route
	 */
	public String getDestinationCode() {
		return destinationCode;
	}
	/**
	 * Sets the destination airport code for the route
	 * @param destinationCode The value of destination airport code to be set
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	/**
	 * Gets the code share status for the route
	 * @return The code share status for the route
	 */
	public char getCodeShare() {
		return codeShare;
	}
	/**
	 * Sets the code share status for the route
	 * @param codeShare The value of code share status to be set
	 */
	public void setCodeShare(char codeShare) {
		this.codeShare = codeShare;
	}
	/**
	 * Gets the number of stops for the route
	 * @return The number of stops for the route
	 */
	public int getStops() {
		return stops;
	}
	/**
	 * Sets the number of stops for the route
	 * @param stops The value of the number of stops to be set
	 */
	public void setStops(int stops) {
		this.stops = stops;
	}
}
