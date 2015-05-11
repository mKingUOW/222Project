package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class AirportEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "airports.csv";
	private BufferedReader reader;
	
	public AirportEntity(){
		
	}
	
	public String getAirportCode(String city){
		
		String oneLine = "";
		boolean existed = false;
		String abbreviation = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!existed && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				
				if(city.equals(words[2])){
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
	
};