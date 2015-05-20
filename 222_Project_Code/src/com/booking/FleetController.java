/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FleetController {
	
	private FleetEntity fe = new FleetEntity();
	
	public FleetController(){
		
	}
	
	public void editFleetOption(){
		
	}
	
	public int[] getSeatsForPlane(int planeId){
		return fe.getSeatsForPlane(planeId);
	}
	
}
