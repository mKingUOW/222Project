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
		Route route = null;
		String oneLine = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(origin.equals(words[1]) && destination.equals(words[2])){
					route = new Route();
					
					int routeNumber = Integer.parseInt(words[0]);
					char codeShare = '';
					if("Y".equals(words[3])){
						codeShare = 'Y';
					}
					int stops = Integer.parseInt(words[4]);
					
					route.setRouteNumber(routeNumber);
					route.setOriginCode(words[1]);
					route.setDestinationCode(words[2]);
					route.setCodeShare(codeShare);
					route.setStops(stops);
					
					found = true;
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return route;
	}
	
	public void addRoute(Route route){
		int routeNumber = route.getRouteNumber();
		String origin_code = route.getOriginCode();
		String destination_code = route.getDestinationCode();
		char codeShare = route.getCodeShare();
		int stops = route.getStops();
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(filepath),true));		//To append to the file using "true";
			writer.println(routeNumber + "," + origin_code + "," + destination_code + "," + codeShare + "," + stops);					   
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void editRoute(Route route){
		
	}
	
	public void deleteRoute(String origin_code,String destination_code){
		
	}
	
}