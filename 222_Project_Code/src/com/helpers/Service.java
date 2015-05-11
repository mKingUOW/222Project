package com.helpers;

public class Service {
	
	private int serviceID;
	private String item;
	private int cost;
	private String availability;	

	public Service(int sid,String itm,int co,String avail){
		this.serviceID = sid;
		this.item = itm;
		this.cost = co;
		this.availability = avail;
	}
	
	public void setServiceID(int sid){
		this.serviceID = sid;
	}
	
	public void setItem(String itm){
		this.item = itm;
	}
	
	public void setCost(int co){
		this.cost = co;
	}
	
	public void setAvailability(String avail){
		this.availability = avail;
	}
	
	public int getServiceID(){
		return this.serviceID;
	}
	
	public String getItem(){
		return this.item;
	}
	
	public int getCost(){
		return this.cost;
	}
	
	public String getAvailability(){
		return this.availability;
	}
};