/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.ServiceBooking;
import com.helpers.Ticket;
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
			usrWriter.close();
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
				discount = Integer.parseInt(words[0]);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return discount;
	}
}
