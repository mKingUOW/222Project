package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class RouteEntity{
	
	// routeNumber + origin_code + destination_code + codeShare + stops  --- in the routes.csv
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "routes.csv";
	private BufferedReader reader;
	private PrintWriter writer;
	
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
					char codeShare = ' ';
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
		int routeNumber = route.getRouteNumber();
		String origin_code = route.getOriginCode();
		String destination_code = route.getDestinationCode();
		char codeShare = route.getCodeShare();
		int stops = route.getStops();
		boolean found = false;
		
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		// routeNumber + origin_code + destination_code + codeShare + stops  --- in the routes.csv
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				int tmp_route_number = Integer.parseInt(words[0]);
				if(tmp_route_number == routeNumber){
					updatedLine += routeNumber;		
					updatedLine += ",";
					updatedLine += origin_code;		
					updatedLine += ",";
					updatedLine += destination_code;		
					updatedLine += ",";
					updatedLine += codeShare;		
					updatedLine += ",";
					updatedLine += stops;		

					data += updatedLine + "\n";
				}else{
					data += oneLine + "\n";
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(filepath)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteRoute(String origin_code,String destination_code){
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		boolean found = false;
		
		// routeNumber + origin_code + destination_code + codeShare + stops  --- in the routes.csv
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				if(origin_code.equals(words[1]) && destination_code.equals(words[2])){
					//If find the line to be deleted, do not add it to the data;
					//Which means nothing to do in this block;
				}else{
					data += oneLine + "\n";		//Add the remaining to data;
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(filepath)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}