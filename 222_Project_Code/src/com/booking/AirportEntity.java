package com.booking;

import com.helpers.Airport;
import java.io.*;

public class AirportEntity{

	/**
	 * A quick reference to the airport database file.
	 */
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "airports.csv";
	
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
	public AirportEntity(){
	}
	
	/**
	 * Gets the airport code associated with the airport name given.
	 * @param airport_name The airport name to get the code of.
	 * @return The airport code of the given airport name. Returns null if the
	 * airport name is not found.
	 */
	public String getAirportCode(String airport_name){
		
		String oneLine = "";
		boolean existed = false;
		String abbreviation = null;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!existed && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(airport_name.equals(words[1])){
					existed = true;
					abbreviation = words[4];
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return abbreviation;
	}

	/**
	 * Gets the airport country associated with the airport name given.
	 * @param airport_name The airport name to get the code of.
	 * @return The name of the country of the given airport name. Returns an
	 * empty String if the airport name is not found.
	 */
	public String getAirportCountry(String airport_name){
		String oneLine = "";
		boolean existed = false;
		String country = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!existed && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(airport_name.equals(words[1])){
					existed = true;
					country = words[3];
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return country;
	}
	
	/**
	 * Gets the airport city associated with the airport IATA given.
	 * @param IATA The IATA to get the city of.
	 * @return The name of the city of the given airport IATA. Returns an
	 * empty string when IATA is not found.
	 */
	public String getAirportCity(String IATA){
		String oneLine = "";
		boolean isFound = false;
		String city = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!isFound && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(IATA.equals(words[4])){
					isFound = true;
					city = words[2];
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return city;
	}
	
	/**
	 * Gets an Airport object based on the given IATA.
	 * @param IATA The IATA to get the corresponding airport.
	 * @return An Airport object corresponding to the given IATA. Returns null
	 * if the IATA is not fount.
	 */
	public Airport getAirport(String IATA){
		Airport airport = null;
		String oneLine = "";
		boolean isFound = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!isFound && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(IATA.equals(words[4])){
					int airportId = Integer.parseInt(words[0]);			
					String airportName = words[1];
					String city = words[2];
					String country = words[3];
					// IATA is words[4];
					double latitude = Double.parseDouble(words[5]);		
					double longitude = Double.parseDouble(words[6]);
					int altitude = Integer.parseInt(words[7]);
					int timezone = Integer.parseInt(words[8]);
					char DST = words[9].charAt(0);
					String tzDatabaseTimezone = words[10];
					
					airport = new Airport(airportId,airportName,city,country,IATA,latitude,longitude,altitude,timezone,DST,tzDatabaseTimezone);
					
					isFound = true;
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return airport;
	}
	
	/**
	 * Adds an airport.
	 * @param airport The Airport object to add to the database.
	 */
	public void addAirport(Airport airport){
		int airportId = airport.getAirportId();
		String airportName = airport.getAirportName();
		String city = airport.getCity();
		String country = airport.getCountry();
		String IATA = airport.getIATA();
		double latitude = airport.getLatitude();
		double longitude = airport.getLongitude();
		int altitude = airport.getAltitude();
		int timezone = airport.getTimezone();
		char DST = airport.getDST();
		String tzDatabaseTimezone = airport.getTzDatabaseTimezone();
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(filepath),true));		//To append to the file using "true";
			writer.println(airportId + "," + airportName + "," + city + "," + country + "," 
						 + IATA + "," + latitude + "," + longitude + "," + altitude + "," 
						 + timezone + "," + DST + "," + tzDatabaseTimezone );					   
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Edits an airport.
	 * @param airport The Airport object to edit. The matching airport in
	 * the database will be replaced with this object.
	 */
	public void editAirport(Airport airport){
		int airportId = airport.getAirportId();
		String airportName = airport.getAirportName();
		String city = airport.getCity();
		String country = airport.getCountry();
		String IATA = airport.getIATA();
		double latitude = airport.getLatitude();
		double longitude = airport.getLongitude();
		int altitude = airport.getAltitude();
		int timezone = airport.getTimezone();
		char DST = airport.getDST();
		String tzDatabaseTimezone = airport.getTzDatabaseTimezone();
		
		boolean found = false;
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				String tmp_IATA = words[4];
				if(IATA.equals(tmp_IATA)){
					updatedLine += airportId;		
					updatedLine += ",";
					updatedLine += airportName;		
					updatedLine += ",";
					updatedLine += city;		
					updatedLine += ",";
					updatedLine += country;		
					updatedLine += ",";
					updatedLine += IATA;		
					updatedLine += ",";
					updatedLine += latitude;		
					updatedLine += ",";
					updatedLine += longitude;		
					updatedLine += ",";
					updatedLine += altitude;		
					updatedLine += ",";
					updatedLine += timezone;		
					updatedLine += ",";
					updatedLine += DST;		
					updatedLine += ",";
					updatedLine += tzDatabaseTimezone;		

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
	 * Deletes an airport.
	 * @param IATA The IATA of the airport to delete by.
	 */
	public void deleteAirport(String IATA){
		String oneLine = "";
		String data = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				if(IATA.equals(words[4])){
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