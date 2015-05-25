/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.*;

import java.io.*;
import java.util.*;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FleetEntity {
	
	// In the fleet.csv:
	// planeId + planeModel + numberAvailable 
	// + firstClassSeats + businessClassSeats + premiumEconomyClassSeats + economyClassSeats
	// of Plane.java;
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "fleet.csv";
	private BufferedReader reader;
	private PrintWriter writer;
	
	public FleetEntity(){
		
	}
	
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

	public void editPlane(Plane plane) {
		int planeId = plane.getPlaneID();
		String planeModel = plane.getPlaneModel();
		int numberAvailable = plane.getNumberAvailable();
		int fclass = plane.getFirstClassSeats();
		int bclass = plane.getBusinessClassSeats();
		int peclass = plane.getPremiumEconomyClassSeats();
		int eclass = plane.getEconomyClassSeats();
		
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

	public void deletePlane(String model) {
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
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
}
