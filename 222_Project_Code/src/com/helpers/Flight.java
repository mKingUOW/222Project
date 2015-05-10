
package com.helpers;

import java.util.*;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Flight {
	
	private String flightID;
	private int planeID;
	private int routeNumber;
	private String departureTime;
	private String arriveTime;
	
	public Flight(String fid,int pid,int rnum,String depTime,String arrTime){
		this.flightID = fid;
		this.planeID = pid;
		this.routeNumber = rnum;
		this.departureTime = depTime;
		this.arriveTime = arrTime;
	}
	
	public Flight(){					//Default Constructor without arguments;
		
	}
	
	public void setFlightID(String fid){
		this.flightID = fid;
	}
	
	public void setPlaneID(int pid){
		this.planeID = pid;
	}
	
	public void setRouteNumber(int rnum){
		this.routeNumber = rnum;
	}
	
	public void setDepartureTime(String depTime){
		this.departureTime = depTime;
	}
	
	public void setArriveTime(String arrTime){
		this.arriveTime = arrTime;
	}
	
	public String getFlightID(){
		return this.flightID;
	}
	
	public int getPlaneID(){
		return this.planeID;
	}
	
	public int getRouteNumber(){
		return this.routeNumber;
	}
	
	public String getDepartureTime(){
		return this.departureTime;
	}
	
	public String getArriveTime(){
		return this.arriveTime;
	}
	
};
