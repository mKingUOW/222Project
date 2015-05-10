/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class BookingController {
	private Scanner in = new Scanner(System.in);
	
	public BookingController(){
		
	}
	
	public void makeBooking(){
		String origin;
		String destination;
		
		System.out.print("Please enter the flight origin of choice: ");
		origin = in.nextLine();
		
		System.out.print("Please enter the flight destination of choice: ");
		destination = in.nextLine();
		
		
	}
	
	public void cancelBooking(){
		
	}
}
