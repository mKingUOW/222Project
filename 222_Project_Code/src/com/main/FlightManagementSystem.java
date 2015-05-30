package com.main;

import com.interaction.UserInterface;

/**
 * The class that contains the main method of the Flight Management System.
 * @author Michael Y.M. Kong
 */
public class FlightManagementSystem{
	
	/**
	 * Main method that starts the whole system.
	 * @param args Cmd line arguments
	 */
	public static void main(String args[]){
		new UserInterface().start();
	}
}