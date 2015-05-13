package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class RouteEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "routes.csv";
	private BufferedReader reader;
	
	public RouteEntity(){
		
	}
	
	public int getRoute(String origin,String destination){
		
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
		AbstractMap.SimpleImmutableEntry<String, String> route_points
				= new AbstractMap.SimpleImmutableEntry<>("", "");
		
		return route_points;
	}
	
};