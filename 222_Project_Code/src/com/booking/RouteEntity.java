package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

/**
 * The Entity class that maps to the routes.csv file.
 * All transactions that deal with routes and the associated data
 * are done here.
 * @author Michael Y.M. Kong
 * @author Jicheng Li
 */
public class RouteEntity{
	
	// routeNumber + origin_code + destination_code + codeShare + stops  --- in the routes.csv
	/**
	 * A quick reference to the route database file.
	 */
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "routes.csv";
	
	/**
	 * A BufferedReader object that allows the class to read from files.
	 */
	private BufferedReader reader;
	
	/**
	 * A PrintWriter object that allows the class to write to files.
	 */
	private PrintWriter writer;
	
	/**
	 * Default constructor.
	 */
	public RouteEntity(){
	}
	
	/**
	 * Gets the route number based on the origin and destination airports.
	 * @param origin_airport The origin airport.
	 * @param destination_airport The destination airport.
	 * @return The route number matching the origin and destination airports.
	 * -1 if route is not found.
	 */
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
	
	/**
	 * Gets the codes for either ends of the given route number.
	 * @param routeNumber The route number to search for.
	 * @return A pair of Strings. If the route number does not exist, a pair
	 * of empty Strings will be returned.
	 */
	public Map.Entry<String, String> getRoutePointsCodes(int routeNumber){

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
	
	/**
	 * Gets the Route object matching the given origin and destination code.
	 * @param origin_code The origin code.
	 * @param destination_code The destination code.
	 * @return Route object which matches the given origin and destination code.
	 * If route not found, returns null.
	 */
	public Route getRoute(String origin_code, String destination_code){
		Route route = null;
		String oneLine = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(origin_code.equals(words[1]) && destination_code.equals(words[2])){
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
	
	/**
	 * Adds the given Route object to the database.
	 * @param route The Route object to add to database.
	 */
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
	
	/**
	 * Edits a route in the database.
	 * @param route The Route object to update the database.
	 */
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
	
	/**
	 * Deletes a route from the database.
	 * @param origin_code The origin code or the route.
	 * @param destination_code The destination code of the route.
	 */
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