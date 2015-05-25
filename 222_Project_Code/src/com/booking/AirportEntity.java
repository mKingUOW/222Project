package com.booking;

import com.helpers.Airport;
import java.io.*;

public class AirportEntity{

// Attributes in Airport.java	
/*	int airportId;			
	String airportName;
	String city;
	String country;
	String IATA;
	double latitude;		
	double longitude;
	int altitude;
	int timezone;
	char DST;
	String tzDatabaseTimezone;
*/	
	String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "airports.csv";
	BufferedReader reader;
	private PrintWriter writer;
	
	public AirportEntity(){
	}
	
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
	
	public void deleteAirport(String IATA){
		String oneLine = "";
		String data = "";
		String updatedLine = "";
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