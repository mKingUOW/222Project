package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class ServiceEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "servicesInventory.csv";
	private BufferedReader reader;
	private List<Service> services;
	
	public ServiceEntity(){
		services = new ArrayList<Service>();
	}
	
	public List<Service> getServices(boolean isInternational){
		
		String oneLine = "";
		boolean existed = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String availability = words[3];
				int cost = Integer.parseInt(words[2]);
				String item = words[1];
				int serviceID = Integer.parseInt(words[0]);
				
				if(isInternational){	//If pass "true" as argument,return all the services;
					Service aservice = new Service(serviceID,item,cost,availability);
					services.add(aservice);		// Add the service to the List;
				}else{				   //If pass "false" as argument,return the services that are not international;
					if(!availability.equals("international")){
						Service aservice = new Service(serviceID,item,cost,availability);
						services.add(aservice); // Add the service to the List;
					}
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return services;
	}
	
};