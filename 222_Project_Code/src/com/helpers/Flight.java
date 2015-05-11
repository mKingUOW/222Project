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
	
	public Flight(String fid,int pid,int rnum,String depTime,String arrTime,int fc,int bc,int pec,int ec){
		this.flightID = fid;
		this.planeID = pid;
		this.routeNumber = rnum;
		this.departureTime = depTime;
		this.arriveTime = arrTime;
		this.firstClassSeats = fc;
		this.businessClassSeats = bc;
		this.premiumEconomyClassSeats = pec;
		this.economyClassSeats = ec;
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
	
	public void setFirstClassSeats(int fc){
		this.firstClassSeats = fc;
	}
	
	public void setBusinessClassSeats(int bc){
		this.businessClassSeats = bc;
	}
	
	public void setPremiumEconomyClassSeats(int pec){
		this.premiumEconomyClassSeats = pec;
	}
	
	public void setEconomyClassSeats(int ec){
		this.economyClassSeats = ec;
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
		return this.firstClassSeats;
	}
	
	public int getBusinessClassSeats(){
		return this.businessClassSeats;
	}
	
	public int getPremiumEconomyClassSeats(){
		return this.premiumEconomyClassSeats;
	}
	
	public int getEconomyClassSeats(){
		return this.economyClassSeats;
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
		builder.append(this.departureTime);
		builder.append("     ");
		builder.append(this.arriveTime);
		
		return builder.toString();
	}
}
