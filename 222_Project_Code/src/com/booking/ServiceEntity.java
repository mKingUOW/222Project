package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

/**
 * The Entity class that maps to the servicesInventory.csv file.
 * All transactions that deal with services and the associated data
 * are done here.
 * @author Michael Y.M. Kong
 * @author Jicheng Li
 */
public class ServiceEntity{
	
	/**
	 * A quick reference to the service database file.
	 */
	private String filepath;
	
	/**
	 * A BufferedReader object that allows the class to read from files.
	 */
	private BufferedReader reader;
	
	/**
	 * A PrintWriter object that allows the class to write to files.
	 */
	private PrintWriter writer;
	
	/**
	 * Default constructor
	 */
	public ServiceEntity(){
            filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "services.csv";
	}
	
	/**
	 * Gets a List of Service objects matching whether the flight is an
	 * international flight or not.
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
		}catch(Exception e){e.printStackTrace();
			
		}
		
		return services;
	}
	
	/**
	 * Gets the Service object for the given service ID.
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
		}catch(Exception e){e.printStackTrace();
			 
		}
		
		return aservice;
	}
	
	/**
	 * Gets the price for the given service ID.
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
		}catch(Exception e){e.printStackTrace();
			 
		}
		
		return price;
	}
	
	/**
	 * Adds a new Service to the database
	 * @param new_services A Service object containing the details of the new service
	 */
	public void addServices(List<Service> new_services){
		int size = new_services.size();
		int serviceId = 0;
                
                String oneLine;
		
                try{
                    reader = new BufferedReader(new FileReader(filepath));			
                    while(((oneLine = reader.readLine()) != null)){
                        String[] words = oneLine.split(",");
                        serviceId = Integer.parseInt(words[0]); 				
                    }			
                    reader.close();
		}catch(Exception e){e.printStackTrace();
			 
		}
                
		for(int i=0;i < size;i++){
			Service service = new_services.get(i);
			
			serviceId++;
			String serviceName = service.getName();
			String cost = Double.toString(service.getCost());
			String availability = service.getAvailability();
			
			try{
				writer = new PrintWriter(new FileOutputStream(new File(filepath),true));	
				writer.println(serviceId + "," + serviceName + "," + cost + "," + availability);
				writer.close();	
			}catch(Exception e){e.printStackTrace();
				 
			}	
		}
		
	}
	
	/**
	 * This method pseudo-removes the services from the database by 
	 * setting the availability to "Removed".
	 * @param service_ids_to_remove 
	 */
	public void removeService(int[] service_ids_to_remove){
            String oneLine = "";
            String data = "";

            try{
                    reader = new BufferedReader(new FileReader(filepath));
                    while((oneLine = reader.readLine()) != null){				
                        String[] words = oneLine.split(",");
                        
                        boolean isOkay = true;
                        
                        for(int i=0;i < service_ids_to_remove.length;i++){
                            int serviceId = service_ids_to_remove[i];
                            int tmp_svc_id = Integer.parseInt(words[0]);				
                            if(serviceId == tmp_svc_id){
                                data += words[0];
                                data += ",";
                                data += words[1];
                                data += ",";
                                data += words[2];
                                data += ",";
                                data += "Removed\n";
                                isOkay = false;
                                break;
                            } 
                        }
                        
                        if (isOkay) {
                            data += oneLine + "\n";
                        }
                    }

                    reader.close();
            }catch(Exception e){e.printStackTrace();
                     
            }

            try{
                    writer = new PrintWriter(new FileOutputStream(new File(filepath)));	
                    writer.print(data);
                    writer.close();	
            }catch(Exception e){e.printStackTrace();
                     
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
		}catch(Exception e){e.printStackTrace();
			 
		}
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(filepath)));	
			writer.print(data);
			writer.close();	
		}catch(Exception e){e.printStackTrace();
			 
		}	
	}
}