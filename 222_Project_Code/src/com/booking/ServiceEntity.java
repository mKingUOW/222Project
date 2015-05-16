package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class ServiceEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "servicesInventory.csv";
	private BufferedReader reader;
	
	
	public ServiceEntity(){
		
	}
	
	public List<Service> getServices(boolean isInternational){
		List<Service> services = new ArrayList<>();
		String oneLine = "";
		boolean existed = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while((oneLine = reader.readLine()) != null){
				
				String [] words = oneLine.split(",");
				
				String availability = words[3];
				double cost = Double.parseDouble(words[2]);
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
	
	public Service getService(int serviceId){
		Service aservice = null;
		String oneLine = "";
		boolean found = false;
		int sid = 0;
		double cost = 0;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){				
				String [] words = oneLine.split(",");
				
				sid = Integer.parseInt(words[0]);
				cost = Double.parseDouble(words[2]);
				if(sid == serviceId){
					found = true;
					aservice = new Service(sid,words[1],cost,words[3]);
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return aservice;
	}
	
	public double getPrice(int serviceId){
		String oneLine = "";
		boolean found = false;
		int sid = 0;
		double price = 0;
		double cost = 0;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){				
				String [] words = oneLine.split(",");
				
				sid = Integer.parseInt(words[0]);
				cost = Double.parseDouble(words[2]);
				if(sid == serviceId){
					found = true;
					price = cost;
				}
			}	

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return price;
	}
	
}