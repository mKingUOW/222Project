package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class FlightEntity{
	
	/**
	 * A quick reference to the flight database file.
	 */
	private String scheduleFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "flight-route-schedule.csv";
	
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
	public FlightEntity(){
	}
	
	/**
	 * Gets a List of Flight objects based on the given route number.
	 * @param routeNumber The route number to get the flights for.
	 * @return A List of Flight objects that match the origin and destination airports.
	 */
	public List<Flight> getFlights(int routeNumber){
		List<Flight> flights = new ArrayList<>();
		String oneLine = "";
		
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
	
	/**
	 * Updates the available seats of a particular flight.
	 * @param flight_id The flight ID of the flight to update the seats of.
	 * @param available_seats The current available seats for the flight.
	 */
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
				
				String tmp_flight_id = words[1];
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
					updatedLine += ",";
					
					//The prices are in the words[10],[11],[12],[13]
					updatedLine += words[10];
					updatedLine += ",";
					updatedLine += words[11];
					updatedLine += ",";
					updatedLine += words[12];
					updatedLine += ",";
					updatedLine += words[13];
					
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
	 * Sets the seat price of a flight.
	 * @param flight_id The flight ID of the flight to set the seat price of.
	 * @param prices A double array of the seat prices.
	 */
	public void setSeatPrice(String flight_id, double[] prices){
		String fcPrice = Double.toString(prices[0]);
		String bcPrice = Double.toString(prices[1]);
		String pecPrice = Double.toString(prices[2]);
		String ecPrice = Double.toString(prices[3]);
		
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){

                String[] words = oneLine.split(",");
				
				if(flight_id.equals(words[1])){
					for(int i=0;i < 10;i++){
						updatedLine += words[i];
						updatedLine += ",";
					}
					//The prices are in the words[10],[11],[12],[13]
					updatedLine +=  fcPrice;
					updatedLine += ",";
					updatedLine +=  bcPrice;
					updatedLine += ",";
					updatedLine +=  pecPrice;
					updatedLine += ",";
					updatedLine +=  ecPrice;
					
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
			writer = new PrintWriter(new FileOutputStream(new File(scheduleFile)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the seats prices of the flight.
	 * @param flight_id The flight ID to get the seat price of.
	 * @return A double array of seat prices for the given flight.
	 */
	public double[] getSeatPrices(String flight_id){
		double [] prices = {0.0,0.0,0.0,0.0};	//Initialise an empty array,default value all 0;
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String tmpFlightId = words[1];	
				if(tmpFlightId.equals(flight_id)){
					for(int i=0;i < 4;i++){
						//The prices are in the words[10],[11],[12],[13]
						prices[i] = Integer.parseInt(words[i+10]);
					}
				}
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prices;
	}
	
	/**
	 * Gets a Flight object based on the given flight ID.
	 * @param flight_id The flight ID to get the corresponding Flight.
	 * @return The Flight object of the given flight ID.
	 */
	public Flight getFlight(String flight_id){
		Flight oneFlight = null;
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String tmpFlightId = words[1];	
				if(tmpFlightId.equals(flight_id)){
					int tmpPlaneID = Integer.parseInt(words[2]);				
					int tmpRouteNum = Integer.parseInt(words[3]);
					int fcSeat = Integer.parseInt(words[6]);
					int bcSeat = Integer.parseInt(words[7]);
					int pecSeat = Integer.parseInt(words[8]);
					int ecSeat = Integer.parseInt(words[9]);
					oneFlight = new Flight(flight_id,tmpPlaneID,tmpRouteNum,words[4],words[5],fcSeat,bcSeat,pecSeat,ecSeat);
				}
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return oneFlight;
	}
	
	/**
	 * Adds a flight to the database
	 * @param flight A Flight object representing the flight details to add to the database.
	 */
	public void addFlight(Flight flight){
		String oneLine = "";
		int autoID = 0;
		
		//Set the autoID to the id of the last line, then increment it by One;
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){		
				String [] words = oneLine.split(",");
				autoID = Integer.parseInt(words[0]);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		autoID++;
		//Then append the new flight into the csv file, the prices are set to default values;
		try{
			writer = new PrintWriter(new FileOutputStream(new File(scheduleFile),true));		//To append to the file using "true";
			writer.print(Integer.toString(autoID) + ",");
			writer.println(flight.getFlightID() + "," + Integer.toString(flight.getPlaneID()) + "," + Integer.toString(flight.getRouteNumber()) + "," 
						 + flight.getDepartureTime() + "," + flight.getArriveTime() + "," 
						 + Integer.toString(flight.getFirstClassSeats()) + "," + Integer.toString(flight.getBusinessClassSeats()) + "," 
						 + Integer.toString(flight.getPremiumEconomyClassSeats()) + "," + Integer.toString(flight.getEconomyClassSeats()) + "," 
						 + "500" + "," + "400" + "," + "300" + "," + "100");
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * Edits a flight in the database.
	 * @param flight The Flight object that will update the database.
	 */
	public void editFlight(Flight flight){
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		String flight_id = flight.getFlightID();
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){			
				String [] words = oneLine.split(",");
				
				String tmpFlightId = words[1];	
				if(tmpFlightId.equals(flight_id)){
					//The prices are in the words[10],[11],[12],[13] and are not modified;
					String fcPrice = words[10];
					String bcPrice = words[11];
					String pecPrice = words[12];
					String ecPrice = words[13];
					
					updatedLine += words[0];
					updatedLine += ",";
					updatedLine += flight_id;
					updatedLine += ",";
					updatedLine += flight.getPlaneID();
					updatedLine += ",";
					updatedLine += flight.getRouteNumber();
					updatedLine += ",";
					updatedLine += flight.getDepartureTime();
					updatedLine += ",";
					updatedLine += flight.getArriveTime();
					updatedLine += ",";
					updatedLine += flight.getFirstClassSeats();
					updatedLine += ",";
					updatedLine += flight.getBusinessClassSeats();
					updatedLine += ",";
					updatedLine += flight.getPremiumEconomyClassSeats();
					updatedLine += ",";
					updatedLine += flight.getEconomyClassSeats();
					updatedLine += ",";
					
					updatedLine += fcPrice;
					updatedLine += ",";
					updatedLine += bcPrice;
					updatedLine += ",";
					updatedLine += pecPrice;
					updatedLine += ",";
					updatedLine += ecPrice;
				
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
			writer = new PrintWriter(new FileOutputStream(new File(scheduleFile)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes a flight from the database.
	 * @param flight_id The flight ID of the flight to delete.
	 */
	public void deleteFlight(String flight_id){
		String oneLine = "";
		String data = "";
		
		try{
			reader = new BufferedReader(new FileReader(scheduleFile));
			while((oneLine = reader.readLine()) != null){			
				String [] words = oneLine.split(",");
				
				//If the flight_id equals words[1], skip it;
				if(!words[1].equals(flight_id)){
					data += oneLine + "\n";
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
}