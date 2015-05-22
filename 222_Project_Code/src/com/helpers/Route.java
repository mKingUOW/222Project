/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Route {
	int routeNumber;
	String originCode;
	String destinationCode;
	char codeShare;
	int stops;

	public Route() {
	}

	public int getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(int routeNumber) {
		this.routeNumber = routeNumber;
	}

	public Route(String originCode, String destinationCode) {
		this.originCode = originCode;
		this.destinationCode = destinationCode;
	}

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public char getCodeShare() {
		return codeShare;
	}

	public void setCodeShare(char codeShare) {
		this.codeShare = codeShare;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}
}
