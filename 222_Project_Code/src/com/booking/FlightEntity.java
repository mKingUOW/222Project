package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class FlightEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "flight-route-schedule.csv";
	private BufferedReader reader;
	
	public FlightEntity(){
		
	}
	
	public List<Flight> getFlights(int routeNumber){
		List<Flight> flights = new ArrayList<>();
		String oneLine = "";
		String abbreviation = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String tmpFlightID = words[1];
				int tmpPlaneID = Integer.parseInt(words[2]);				
				int tmpRouteNum = Integer.parseInt(words[3]);
				int fcSeat = Integer.parseInt(words[6]);
				int bcSeat = Integer.parseInt(words[7]);
				int pecSeat = Integer.parseInt(words[8]);
				int ecSeat = Integer.parseInt(words[9]);
				
				if(routeNumber == tmpRouteNum){
					Flight aflight = new Flight(tmpFlightID,tmpPlaneID,tmpRouteNum,words[4],words[5],fcSeat,bcSeat,pecSeat,ecSeat);
					flights.add(aflight);
				}
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flights;
	}
		
}