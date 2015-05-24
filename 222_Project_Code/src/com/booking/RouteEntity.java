package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class RouteEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "routes.csv";
	private BufferedReader reader;
	
	public RouteEntity(){
		
	}
	
	public int getRouteNumber(String origin,String destination){
		
		String oneLine = "";
		boolean existed = false;
		int routeNumber = -1;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!existed && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(origin.equals(words[1]) && destination.equals(words[2])){
					existed = true;
					routeNumber = Integer.parseInt(words[0]);
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return routeNumber;
	}
	
	public AbstractMap.SimpleImmutableEntry<String, String> getRoutePoints(int routeNumber){

		String oneLine = "";
		boolean found = false;
		String origin = "";
		String destination = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				int rnum = Integer.parseInt(words[0]);
				
				if(routeNumber == rnum){
					found = true;
				}
				origin = words[1];
				destination = words[2];
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new AbstractMap.SimpleImmutableEntry<>(origin, destination);
	}
	
	public Route getRoute(String origin, String destination){
		return null;
	}
	
	public void addRoute(Route route){
		
	}
	
	public void editRoute(Route route){
		
	}
	
	public void deleteRoute(String origin_code,String destination_code){
		
	}
	
}