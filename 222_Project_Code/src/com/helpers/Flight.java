package com.helpers;

import java.util.Formatter;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Flight {
	
	private String flightID;
	private Route route = new Route();
	private String departureTime;
	private String arriveTime;
	private Plane plane = new Plane();
	
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
	
	public Flight(){					//Default Constructor without arguments;
		
	}
	
	public void setFlightID(String fid){
		this.flightID = fid;
	}
	
	public void setPlaneID(int pid){
		this.plane.setPlaneID(pid);
	}
	
	public void setRouteNumber(int rnum){
		this.route.setRouteNumber(rnum);
	}
	
	public void setDepartureTime(String depTime){
		this.departureTime = depTime;
	}
	
	public void setArriveTime(String arrTime){
		this.arriveTime = arrTime;
	}
	
	public void setFirstClassSeats(int fc){
		this.plane.setFirstClassSeats(fc);
	}
	
	public void setBusinessClassSeats(int bc){
		this.plane.setBusinessClassSeats(bc);
	}
	
	public void setPremiumEconomyClassSeats(int pec){
		this.plane.setPremiumEconomyClassSeats(pec);
	}
	
	public void setEconomyClassSeats(int ec){
		this.plane.setEconomyClassSeats(ec);
	}
	
	public String getFlightID(){
		return this.flightID;
	}
	
	public int getPlaneID(){
		return this.plane.getPlaneID();
	}
	
	public int getRouteNumber(){
		return this.route.getRouteNumber();
	}
	
	public String getDepartureTime(){
		return this.departureTime;
	}
	
	public String getArriveTime(){
		return this.arriveTime;
	}
	
	public void setPlane(Plane plane){
		this.plane = plane;
	}
	
	public Plane getPlane(){
		return this.plane;
	}
	
	public int getFirstClassSeats(){
		return this.plane.getFirstClassSeats();
	}
	
	public int getBusinessClassSeats(){
		return this.plane.getBusinessClassSeats();
	}
	
	public int getPremiumEconomyClassSeats(){
		return this.plane.getPremiumEconomyClassSeats();
	}
	
	public int getEconomyClassSeats(){
		return this.plane.getEconomyClassSeats();
	}
	
	public int[] getAvailableSeats(){
		int[] seats = this.plane.getAvailableSeats();
		
		return seats;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		
		formatter.format("%-15s%-35s%-35s", this.flightID, this.departureTime, this.arriveTime);
		
		return builder.toString();
	}
}
