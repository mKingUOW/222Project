package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class FlightEntity{
	
	private String scheduleFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "flight-route-schedule.csv";
	private BufferedReader reader;
	
	public FlightEntity(){
		
	}
	
	public List<Flight> getFlights(int routeNumber){
		List<Flight> flights = new ArrayList<>();
		String oneLine = "";
		String abbreviation = "";
		
		//Attributes in the flight-route-schedule.csv:
		//AUTO_INCREMENT_id + flight_id + plane_id + route_number + depart_time + arrive_time
		// + First_Class_SeatS + Business_Class_Seats + Premium_Economic_Seats + Economic_Class_Seats;
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
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
	
	public void updateAvailableSeats(String flight_id, int[] available_seats){
		int fcSeat = available_seats[0];
		int bcSeat = available_seats[1];
		int pecSeat = available_seats[2];
		int ecSeat = available_seats[3];
		
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){
                String[] words = oneLine.split(",");
				
				tmp_flight_id = words[1];
				if(flight_id.equals(tmp_flight_id)){
					for(int i=0;i < 6;i++){
						updatedLine += words[i];					// until arrive_time, which is words[5], no need to modify;
						updatedLine += ",";
					}
					updatedLine += Integer.toString(fcSeat);		// Update First_Class_Seats;
					updatedLine += ",";
					updatedLine += Integer.toString(bcSeat);		// Update Business_Class_Seats;
					updatedLine += ",";
					updatedLine += Integer.toString(pecSeat);		// Update Premium_Economic_Seats;
					updatedLine += ",";
					updatedLine += Integer.toString(ecSeat);		// Update Economic_Class_Seats;
					data += updatedLine + "\n";						// Add the modified record into data;
				}else{
					data += oneLine + "\n";							// If the flight_id doesn't match, add the original record in the database into data;
				}	
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(scheduleFile)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param flight_id Flight ID
	 * @param prices Price
	 */
	public void setSeatPrice(String flight_id, double[] prices){
		
	}
	
	public double[] getSeatPrices(String flight_id){
		return new double[]{20, 15, 10, 5};
	}
	
	public Flight getFlight(String flight_id){
		return null;
	}
	
	public void addFlight(Flight flight){
		
	}
	
	public void editFlight(Flight flight){
		
	}
	
	public void deleteFlight(String flight_id){
		
	}
}