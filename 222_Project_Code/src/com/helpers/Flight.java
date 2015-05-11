package com.helpers;

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
	private int firstClassSeats;
	private int businessClassSeats;
	private int premiumEconomyClassSeats;
	private int economyClassSeats;
	
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
	
	public void setFirstClassSeats(int fcs){
		firstClassSeats = fcs;
	}
	
	public void setBusinessClassSeats(int bcs){
		businessClassSeats = bcs;
	}
	
	public void setPremiumEconomyClassSeats(int pecs){
		premiumEconomyClassSeats = pecs;
	}
	
	public void setEconomyClassSeats(int ecs){
		economyClassSeats = ecs;
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
	
	public int getFirstClassSeats(){
		return firstClassSeats;
	}
	
	public int getBusinessClassSeats(){
		return businessClassSeats;
	}
	
	public int getPremiumEconomyClassSeats(){
		return premiumEconomyClassSeats;
	}
	
	public int getEconomyClassSeats(){
		return economyClassSeats;
	}
	
	public int[] getAvailableSeats(){
		int[] seats = {firstClassSeats, businessClassSeats, premiumEconomyClassSeats, economyClassSeats};
		
		return seats;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(this.flightID);
		builder.append("     ");
		builder.append(this.arriveTime);
		builder.append("     ");
		builder.append(this.departureTime);
		
		return builder.toString();
	}
}
