package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class FlightEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "flight-route-schedule.csv";
	private BufferedReader reader;
	private List<Flight> flights;
	
	public FlightEntity(){
		flights = new ArrayList<Flight>();
	}
	
	public List<Flight> getFlights(int routeNumber){
		
		String oneLine = "";
		String abbreviation = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String tmpFlightID = words[1];
				int tmpPlaneID = Integer.parseInt(words[2]);				
				int tmpRouteNum = Integer.parseInt(words[3]);
				
				if(routeNumber == tmpRouteNum){
					Flight aflight = new Flight(tmpFlightID,tmpPlaneID,tmpRouteID,words[4],words[5]);
					flights.add(aflight);
				}
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flights;
	}
	
	
};