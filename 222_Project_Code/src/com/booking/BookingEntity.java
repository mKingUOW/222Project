/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Booking;
import com.helpers.Person;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class BookingEntity {
	
	private String discountFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "discount.csv";
	private String bookingFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "booking.csv";
	private String ticketsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "tickets.csv";
	private String serviceFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "services_booked.csv";
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	public BookingEntity(){
		
	}
	
	public void saveBooking(String flight_id, List<Ticket> tickets, List<ServiceBooking> services_booked){	
		//First step, save the booking info into booking.csv.
		int bk_id = 0;	//If no record in the file, default value is 0; It will be incremented by 1 if a record is inserted;
		String booking_status = "Active";
		int tnum = tickets.size();			//Count for tickets totally;
		double total_price = 0.0;			//The total price for all the tickets in the booking;
		String oneLine = "";				//The temporary string for reading from the database file line by line;
		
		for(int p=0;p < tnum;p++){
			total_price += tickets.get(p).getPrice(); 			
		}
		
		try{
			reader = new BufferedReader(new FileReader(bookingFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				bk_id = Integer.parseInt(words[0]); 				
            }			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//bookingID + flightID + Status + Total_Price -- in the booking.csv
		try{
			bk_id++;		// Increment the booking id by 1, for the next booking record;
			writer = new PrintWriter(new FileOutputStream(new File(bookingFile),true));		//To append to the file using "true";
			writer.print(bk_id + "," + flight_id + "," + total_price + ",");
			writer.println(booking_status);						   
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Second step, save the tickets info into tickets.csv, one booking contains many tickets;		
		for(int i=0;i < tnum;i++){		//tnum is the amount of tickets;
			int ticket_id = tickets.get(i).getTicketId();
			String uname = tickets.get(i).getUsername();
			int person_id = tickets.get(i).getPersonId();
			String seat_number = tickets.get(i).getSeatNumber();
			
			//ticketID + userName + personID + bookingID + seatNumber -- in the tickets.csv
			try{ 
				writer = new PrintWriter(new FileOutputStream(new File(ticketsFile),true));		//To append to the file using "true";
				writer.println(ticket_id + "," + uname + "," + person_id + "," + bk_id + "," + seat_number);					   
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Third step, save the services info into services_booked.csv, one ticket may contain many services;
		int snum = services_booked.size();		//Count for services totally;
		for(int k=0;k < snum;k++){
			int ticketId = services_booked.get(k).getTicketId();
			int serviceId = services_booked.get(k).getServiceId();
			
			//ticketID + serviceID -- in the services_booked.csv
			try{ 
				writer = new PrintWriter(new FileOutputStream(new File(serviceFile),true));		//To append to the file using "true";
				writer.println(ticketId + "," + serviceId);					   
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
		List<Booking> bookings = new ArrayList<>();
		List<Integer> booking_id = new ArrayList<>();
		int booking_count = 0;
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(ticketsFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(username.equals(words[1])){
					int bk_id = Integer.parseInt(words[3]);
					booking_id.add(bk_id);
					booking_count++;
				}	
            }			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
		if(booking_count == 0){			//If no booking id is found in tickets.csv, return NULL;
			return null;
		}else{							//Else get the bookings using the booking_ids;
			for(int i=0;i < booking_count;i++){
				int b_id = booking_id.get(i);
				boolean found = false;
				//Read each line of the file to find the booking id.If found,finish this searching,continue to find the next booking id;
				try{
					reader = new BufferedReader(new FileReader(bookingFile));			
					while(!found && ((oneLine = reader.readLine()) != null)){
						String[] words = oneLine.split(",");
						int tmp_booking_id = Integer.parseInt(words[0]);
						if(b_id == tmp_booking_id){		
							String flight_id = words[1];
							double total_price = Double.parseDouble(words[2]);
							String status = words[3];
							
							Booking bk = new Booking(b_id,flight_id,status,total_price);
							bookings.add(bk);
							
							found = true;	
						}
					}				
					reader.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}

			return bookings;
		}	
	}
	
	public List<Ticket> getTickets(int booking_id){
		return null;
	}
	
	public void cancelBooking(int booking_id){
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(bookingFile));
			while((oneLine = reader.readLine()) != null){
                String[] words = oneLine.split(",");
				int tmp_booking_id = Integer.parseInt(words[0]);
				if(booking_id == tmp_booking_id){
					updatedLine += words[0];		//bk_id;
					updatedLine += ",";
					updatedLine += words[1];		//status;
					updatedLine += ",";
					updatedLine +=  words[2];	//total_price
					updatedLine += ",";
					updatedLine +=  "Cancelled"; //flight_id;
					data += updatedLine + "\n";
				}
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			writer = new PrintWriter(new FileOutputStream(new File(bookingFile)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets all customers and persons that are on a particular flight
	 * @param flight_id The flight ID of the flight to search for.
	 * @return A List representing all the customers and persons
	 * on the flight.
	 */
	public List<Person> getCustomers(String flight_id){
		/*
		 * Remember the Customer class is a child class
		 * of the Person class. So you can put a Customer into
		 * the Person List
		 */
		
		return null;
	}
}
