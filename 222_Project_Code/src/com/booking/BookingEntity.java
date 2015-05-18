/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Booking;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class BookingEntity {
	
	private String discountFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "discount.csv";
	private String bookingFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "booking.csv";
	private BufferedReader reader;
	private PrintWriter writer;
	
	public BookingEntity(){
		
	}
	
	public void saveBooking(String flight_id, List<Ticket> tickets, List<ServiceBooking> services_booked){
		
	}
	
	public void setDiscountRatio(double ratio){
		try{
			writer = new PrintWriter(new FileOutputStream(new File(discountFile)));		
			writer.println(ratio);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public double getDiscountRatio(){
		String oneLine = "";
		double discount = 0;
		
		try{
			reader = new BufferedReader(new FileReader(discountFile));
			while((oneLine = reader.readLine()) != null){				
				String [] words = oneLine.split(",");
				discount = Double.parseDouble(words[0]);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return discount;
	}
	
	public List<Booking> getBookings(String username){
		return null;
	}
	
	public void cancelBooking(int booking_id){
		
	}
}
