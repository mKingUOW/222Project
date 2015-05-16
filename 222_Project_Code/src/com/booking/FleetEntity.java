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
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "fleet.csv";
	private BufferedReader reader;
	
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
}
