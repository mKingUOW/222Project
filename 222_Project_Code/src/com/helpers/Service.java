package com.helpers;

import java.util.Formatter;

public class Service {
	
	private int serviceID;
	private String name;
	private double cost;
	private String availability;	

	public Service() {
	}
	
	public Service(int sid,String itm,double co,String avail){
		this.serviceID = sid;
		this.name = itm;
		this.cost = co;
		this.availability = avail;
	}
	
	public void setServiceID(int sid){
		this.serviceID = sid;
	}
	
	public void setName(String itm){
		this.name = itm;
	}
	
	public void setCost(double co){
		this.cost = co;
	}
	
	public void setAvailability(String avail){
		this.availability = avail;
	}
	
	public int getServiceID(){
		return this.serviceID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getCost(){
		return this.cost;
	}
	
	public String getAvailability(){
		return this.availability;
	}

	public String getString(){
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-20s$%-14.2f", name, cost);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-20s$%-14.2f%-15s", name, cost, availability);
		
		return sb.toString();
	}
}