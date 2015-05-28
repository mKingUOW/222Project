package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

public class ServiceEntity{
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "servicesInventory.csv";
	private BufferedReader reader;
	
	/**
	 * Default constructor
	 */
	public ServiceEntity(){
	}
	
	/**
	 * 
	 * @param isInternational A boolean value showing whether the flight calling this
	 * function is an international flight or not.
	 * @return A list of all the services available for the given international status.
	 */
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
				
				if(isInternational){	//If pass "true" as argument,return all the services that are not removed;
					if(!availability.equals("Removed")){
						Service aservice = new Service(serviceID,item,cost,availability);
						services.add(aservice);		// Add the service to the List;
					}
				}else{				   //If pass "false" as argument,return the services that are not international;
					if(!availability.equals("International")){
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
	
	/**
	 * 
	 * @param serviceId The service ID for the service to retrieve
	 * @return A Service object which corresponds to the given service ID
	 */
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
	
	/**
	 * 
	 * @param serviceId The service ID for the service price to retrieve
	 * @return The price of the service which corresponds to the given service ID
	 */
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
	
	/**
	 * Adds a new Service to the database
	 * @param new_services A Service object containing the details of the new service
	 */
	public void addServices(List<Service> new_services){
		int size = new_services.size();
		
		for(int i=0;i < size;i++){
			Service service = new_services.get(i);
			
			String serviceId = Integer.toString(service.getServiceID());
			String serviceName = service.getName();
			String cost = Double.toString(service.getCost());
			String availability = service.getAvailability();
			
			try{
				writer = new PrintWriter(new FileOutputStream(new File(filepath),true));	
				writer.println(serviceId + "," + serviceName + "," + cost + "," + availability);
				writer.close();	
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		
	}
	
	/**
	 * This method pseudo-removes the services from the database by 
	 * setting the availability to "removed".
	 * 
	 * DO NOT CHANGE THE SERVICE ID!!!!
	 * 
	 * @param service_ids_to_remove 
	 */
	public void removeService(int[] service_ids_to_remove){

		for(int i=0;i < service_ids_to_remove.length;i++){
			int serviceId = service_ids_to_remove[i];
			String oneLine = "";
			String data = "";
			
			try{
				reader = new BufferedReader(new FileReader(filepath));
				while((oneLine = reader.readLine()) != null){				
					String[] words = oneLine.split(",");
				
					int tmp_svc_id = Integer.parseInt(words[0]);				
					if(serviceId != tmp_svc_id){
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
		
	}
	
	/**
	 * This method edits the service based on the Service object given.
	 * @param updated_service The Service to update.
	 */
	public void editService(Service updated_service){
		int serviceId = updated_service.getServiceID();
		String serviceName = updated_service.getName();
		double cost = updated_service.getCost();
		String availability = updated_service.getAvailability();
		
		String oneLine = "";
		String data = "";
		String updatedLine = "";
			
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while((oneLine = reader.readLine()) != null){				
				String[] words = oneLine.split(",");
				
				int tmp_svc_id = Integer.parseInt(words[0]);
			
				if(serviceId == tmp_svc_id){
					updatedLine +=  serviceId;
					updatedLine += ",";
					updatedLine +=  serviceName;
					updatedLine += ",";
					updatedLine +=  cost;
					updatedLine += ",";
					updatedLine +=  availability;
					
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
}