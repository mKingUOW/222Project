/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.*;

import java.io.*;

/**
 * The Entity class that maps to the fleet.csv file.
 * All transactions that deal with the fleet, planes and the associated data
 * are done here.
 * @author Michael Y.M. Kong
 * @author Jicheng Li
 */
public class FleetEntity {
	
	// In the fleet.csv:
	// planeId + planeModel + numberAvailable 
	// + firstClassSeats + businessClassSeats + premiumEconomyClassSeats + economyClassSeats
	// of Plane.java;
	
	/**
	 * A quick reference to the fleet database file.
	 */
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "fleet.csv";
	
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
	public FleetEntity(){
	}
	
	/**
	 * Gets the number of seats in a plane based on the given plane ID.
	 * @param planeId The plane ID to get the seats of.
	 * @return An integer array representing the 4 classes of seats for the 
	 * given plane ID.
	 */
	public int[] getSeatsForPlane(int planeId){
		String oneLine = "";
		int pid = 0;
		int [] seats = new int[4];
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){				
				String [] words = oneLine.split(",");
				
				pid = Integer.parseInt(words[0]);
				if(planeId == pid){
					found = true;
					seats[0] = Integer.parseInt(words[3]);
					seats[1] = Integer.parseInt(words[4]);
					seats[2] = Integer.parseInt(words[5]);
					seats[3] = Integer.parseInt(words[6]);
				}	
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return seats;
	}
	
	/**
	 * Gets a Plane object based on the given model.
	 * @param model The model to get the Plane object by.
	 * @return The Plane object associated with the given model.
	 */
	public Plane getPlane(String model){
		Plane plane = null;
		String oneLine = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){				
				String [] words = oneLine.split(",");
				
				if(model.equals(words[1])){
					int planeId = Integer.parseInt(words[0]);
					int fc = Integer.parseInt(words[3]);
					int bc = Integer.parseInt(words[4]);
					int pec = Integer.parseInt(words[5]);
					int ec = Integer.parseInt(words[6]);
					
					plane = new Plane(planeId,model,fc,bc,pec,ec);
					
					found = true;					
				}	
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return plane;
	}
	
	/**
	 * Is called to add a Plane to the database.
	 * @param plane The Plane object to add to the database.
	 */
	public void addPlane(Plane plane) {
		int pid = plane.getPlaneID();
		String planeModel = plane.getPlaneModel();
		int numAvail = plane.getNumberAvailable();
		int fclass = plane.getFirstClassSeats();
		int bclass = plane.getBusinessClassSeats();
		int peclass = plane.getPremiumEconomyClassSeats();
		int eclass = plane.getEconomyClassSeats();
		
		String planeId = Integer.toString(pid);
		String numberAvailable = Integer.toString(numAvail);
		String fc = Integer.toString(fclass);
		String bc = Integer.toString(bclass);
		String pec = Integer.toString(peclass);
		String ec = Integer.toString(eclass);
		
		try{
			//Append the new record at the end;
			writer = new PrintWriter(new FileOutputStream(new File(filepath),true));
			writer.println(planeId + "," + planeModel + "," + numberAvailable + "," 
						 + fc + "," + bc + "," + pec + "," + ec);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Is called to edit a Plane in the database.
	 * @param plane The Plane object used to modify the database.
	 */
	public void editPlane(Plane plane) {
		int planeId = plane.getPlaneID();
		String planeModel = plane.getPlaneModel();
		int numberAvailable = plane.getNumberAvailable();
		int fclass = plane.getFirstClassSeats();
		int bclass = plane.getBusinessClassSeats();
		int peclass = plane.getPremiumEconomyClassSeats();
		int eclass = plane.getEconomyClassSeats();
		boolean found = false;
		
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		
		// In the fleet.csv:
		// planeId + planeModel + numberAvailable 
		// + firstClassSeats + businessClassSeats + premiumEconomyClassSeats + economyClassSeats
		// of Plane.java;
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				int tmp_plane_id = Integer.parseInt(words[0]);
				if(tmp_plane_id == planeId){
					updatedLine += Integer.toString(planeId);		
					updatedLine += ",";
					updatedLine += planeModel;		
					updatedLine += ",";
					updatedLine += Integer.toString(numberAvailable);	
					updatedLine += ",";
					updatedLine += Integer.toString(fclass);		
					updatedLine += ",";
					updatedLine += Integer.toString(bclass);
					updatedLine += ",";
					updatedLine += Integer.toString(peclass);
					updatedLine += ",";
					updatedLine += Integer.toString(eclass);
					
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
	 * Called to delete a plane with the given model.
	 * @param model The model of the plane to delete.
	 */
	public void deletePlane(String model) {
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){
				String [] words = oneLine.split(",");
				if(model.equals(words[1])){
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
	
	/**
	 * Gets the plane model based on the given ID.
	 * @param plane_id The plane ID to get the plane model by.
	 * @return The plane model associated with the given plane ID.
	 */
	public String getPlaneModel(int plane_id){
		String model = "";
		String oneLine = "";
		boolean found = false;
		
		try{
			reader = new BufferedReader(new FileReader(filepath));
			while(!found && ((oneLine = reader.readLine()) != null)){				
				String [] words = oneLine.split(",");
				
				int tempPlaneId = Integer.parseInt(words[0]);
				
				if(plane_id == tempPlaneId){
					model = words[1];
					found = true;					
				}	
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
}
