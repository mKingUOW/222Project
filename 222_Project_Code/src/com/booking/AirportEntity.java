package com.booking;

import com.helpers.Airport;
import java.io.*;

public class AirportEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "airports.csv";
	private BufferedReader reader;
	
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
		return null;
	}
	
	public void addAirport(Airport airport){
		
	}
	
	public void editAirport(Airport airport){
		
	}
	
	public void deleteAirport(String IATA){
		
	}
	
}