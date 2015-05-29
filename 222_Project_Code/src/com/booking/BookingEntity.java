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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Entity class that maps to the booking .csv files. 
 * .csv files:
 * booking_constants.csv, booking.csv, tickets.csv, services_booked.csv
 * All transactions that deal with bookings and the associated data such as
 * tickets and services booked are done here.
 * @author Michael Y.M. Kong
 * @author Jicheng Li
 */
public class BookingEntity {
	
	/**
	 * A quick reference to the discount database file.
	 */
	private String bookingConstantsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "booking_constants.csv";
	
	/**
	 * A quick reference to the bookings database file.
	 */
	private String bookingFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "booking.csv";
	
	/**
	 * A quick reference to the tickets database file.
	 */
	private String ticketsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "tickets.csv";
	
	/**
	 * A quick reference to the services booked database file.
	 */
	private String serviceFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "services_booked.csv";
	
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
	public BookingEntity(){
	}
	
	/**
	 * Is called to save a booking to database.
	 * @param flight_id A String representing the flight ID
	 * @param tickets A List of Ticket objects representing the tickets booked
	 * for the flight.
	 * @param services_booked A List of ServiceBooking objects representing the
	 * services booked for for each ticket.
	 */
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
		
		Date booking_date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss a dd/MMM/yyyy");
		
		//bookingID + flightID + Total_Price + Status + bookingDate -- in the booking.csv
		try{
			bk_id++;		// Increment the booking id by 1, for the next booking record;
			writer = new PrintWriter(new FileOutputStream(new File(bookingFile),true));		//To append to the file using "true";
			writer.print(bk_id + "," + flight_id + "," + total_price + ",");
			writer.print(booking_status + "," + fmt.format(booking_date));						   
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
			double ticket_price = tickets.get(i).getPrice();
			
			//ticketID + userName + personID + bookingID + seatNumber + ticketPrice -- in the tickets.csv
			try{ 
				writer = new PrintWriter(new FileOutputStream(new File(ticketsFile),true));		//To append to the file using "true";
				writer.println(ticket_id + "," + uname + "," + person_id + "," + bk_id + "," + seat_number + "," + ticket_price);					   
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
			
			//bookingID + ticketID + serviceID -- in the services_booked.csv
			try{ 
				writer = new PrintWriter(new FileOutputStream(new File(serviceFile),true));		//To append to the file using "true";
				writer.println(bk_id + "," + ticketId + "," + serviceId);					   
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Called to set the discount ratio of the frequent flier points to database.
	 * @param ratio The ratio to set the discount ratio to.
	 */
	public void setDiscountRatio(double ratio){
		String oneLine = "";
		
		try {
			reader = new BufferedReader(new FileReader(bookingConstantsFile));
			oneLine = reader.readLine();
			String [] words = oneLine.split(",");
			reader.close();
			
			writer = new PrintWriter(new FileOutputStream(new File(bookingConstantsFile)));		
			writer.println(ratio + "," + words[1]);
			writer.close();
		} catch (Exception ex) {
		}
	}
	
	/**
	 * Called to get the discount ratio of the frequent flier points.
	 * @return The discount ratio.
	 */
	public double getDiscountRatio(){
		String oneLine = "";
		double discount = 0;
		
		try{
			reader = new BufferedReader(new FileReader(bookingConstantsFile));
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
	
	/**
	 * Gets all bookings associated with a particular username.
	 * @param username The username to search bookings by.
	 * @return A List of Booking objects that are associated with the given username.
	 */
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
							String date = words[4];
							
							Booking bk = new Booking(b_id,flight_id,status,total_price,date);
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
	
	/**
	 * Gets all bookings associated with a particular booking ID.
	 * @param booking_id The booking ID to get tickets by.
	 * @return A List of Ticket objects that are associated with the given booking ID.
	 */
	public List<Ticket> getTickets(int booking_id){
		//ticketID + userName + personID + bookingID + seatNumber -- in the tickets.csv
		String oneLine = "";
		List<Ticket> myTickets = new ArrayList<>();
		
		try{
			reader = new BufferedReader(new FileReader(ticketsFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				int bk_id = Integer.parseInt(words[3]);
				if(bk_id == booking_id){
					int ticket_id = Integer.parseInt(words[0]);
					int person_id = Integer.parseInt(words[2]);
					double ticket_price = Double.parseDouble(words[5]);
					Ticket tmpTicket = new Ticket(ticket_id,words[1],person_id,booking_id,words[4],ticket_price);
					myTickets.add(tmpTicket);
				}
            }			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return myTickets;
	}
	
	/**
	 * Cancels a booking.
	 * @param booking_id The booking ID to cancel the booking of.
	 */
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
					updatedLine += ",";
					updatedLine +=  words[4];	//booking_date
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
			writer = new PrintWriter(new FileOutputStream(new File(bookingFile)));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the cancellation fee.
	 */
	public double getCancellationFee(){
		String oneLine = "";
		double cancellation_fee = 0;
		
		try{
			reader = new BufferedReader(new FileReader(bookingConstantsFile));
			while((oneLine = reader.readLine()) != null){				
				String [] words = oneLine.split(",");
				cancellation_fee = Double.parseDouble(words[1]);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cancellation_fee;
	}
	
	/**
	 * Sets the cancellation fee.
	 */
	public void setCancellationFee(double cancellation_fee){
		String oneLine = "";
		
		try {
			reader = new BufferedReader(new FileReader(bookingConstantsFile));
			oneLine = reader.readLine();
			String [] words = oneLine.split(",");
			reader.close();
			
			writer = new PrintWriter(new FileOutputStream(new File(bookingConstantsFile)));		
			writer.println(words[0] + "," + cancellation_fee);
			writer.close();
		} catch (Exception ex) {
		}
	}
	
	/**
	 * Gets all customers and persons that are on a particular flight
	 * @param flight_id The flight ID of the flight to search for.
	 * @return A List representing all the customers and persons
	 * on the flight.
	 */
	public List<Person> getCustomers(String flight_id){
		List<Person> customers = null;
		String oneLine = "";
/*		
	//First step, from the flight_id,find all the booking_id in the booking.csv;
		List<Integer> booking_id = new ArrayList<>();
		int booking_count = 0;
		
		try{
			reader = new BufferedReader(new FileReader(bookingFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(flight_id.equals(words[1])){
					int bk_id = Integer.parseInt(words[0]);
					booking_id.add(bk_id);
					booking_count++;
				}	
            }			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(booking_count == 0){
			return null;
		}
		
	//Second step,find all the username or person_id from the booking_id, in the tickets.csv;
		List<String> user_names = new ArrayList<String>();
		List<Integer> person_ids = new ArrayList<Integer>();
		int bk_size = booking_id.size();
		
		for(int i=0;i < bk_size;i++){
			int bk_id = booking_id.get(i);
			try{
				reader = new BufferedReader(new FileReader(ticketsFile));			
				while(((oneLine = reader.readLine()) != null)){
					String[] words = oneLine.split(",");
					
					int tmp_booking_id = Integer.parseInt(words[3]);
					if(bk_id == tmp_booking_id){
						String uname = words[1];
						int pid = Integer.parseInt(words[2]);
						
						if(pid != -1){		//pid != -1, a person without username;
							person_ids.add(pid);
						}else{				//pid == -1 , a user;
							user_names.add(uname);
						}
					}
				}			
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
*/		
		return null;
	}
	
	/**
	 * Gets the services booked for the ticket with the corresponding booking ID
	 * and ticket ID.
	 * @param booking_id The booking ID to search by.
	 * @param ticket_id The ticket ID to search by.
	 * @return A list of ServiceBooking objects representing the services booked.
	 */
	public List<ServiceBooking> getServicesBooked(int booking_id, int ticket_id){
		String oneLine = "";
		List<ServiceBooking> services = new ArrayList<>();
		
		try{
			reader = new BufferedReader(new FileReader(serviceFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				int bk_id = Integer.parseInt(words[0]);
				int tk_id = Integer.parseInt(words[1]);
				int service_id = Integer.parseInt(words[2]);
				if((bk_id == booking_id) && (tk_id == ticket_id)){ 
					ServiceBooking a_service = new ServiceBooking(bk_id,tk_id,service_id);
					services.add(a_service);
				}
            }			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return services;
	}
	
	/**
	 * Gets all the service bookings that have been made.
	 * @return 
	 */
	public List<ServiceBooking> getAllServicesBooked(){
		String oneLine = "";
		List<ServiceBooking> services = new ArrayList<>();
		
		try{
			reader = new BufferedReader(new FileReader(serviceFile));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				int bk_id = Integer.parseInt(words[0]);
				int tk_id = Integer.parseInt(words[1]);
				int service_id = Integer.parseInt(words[2]);
				
				ServiceBooking a_service = new ServiceBooking(bk_id,tk_id,service_id);
				services.add(a_service);
            }	
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return services;
	}
	
	/**
	 * Updates the bookings of a particular ticket.
	 * @param service_bookings List of service bookings
	 */
	public void updateServicesBooked(List<ServiceBooking> service_bookings){
		/*
		 * ALL the service bookings in this list have the same booking ID and ticket ID.
		 * A ServiceBooking object already contains the booking ID and ticket ID.
		 * SO just search the database for the bookings matching the booking ID adn ticket ID, 
		 * delete them and replace them with the service bookings in the list.
		 */
		
		int size = service_bookings.size();
		for(int i=0;i < size;i++){
			ServiceBooking a_service = service_bookings.get(i);
			int bk_id = a_service.getBookingId();
			int tk_id = a_service.getTicketId();
			int svc_id = a_service.getServiceId();
			
			String oneLine = "";
			String data = "";
			String updatedLine = "";
			
			try{
				reader = new BufferedReader(new FileReader(serviceFile));
				while((oneLine = reader.readLine()) != null){				
					String[] words = oneLine.split(",");
				
					int tmp_bk_id = Integer.parseInt(words[0]);
					int tmp_tk_id = Integer.parseInt(words[1]);
					String service_id = Integer.toString(svc_id);
				
					if((bk_id == tmp_bk_id) && (tk_id == tmp_tk_id)){
						updatedLine +=  words[0];
						updatedLine += ",";
						updatedLine +=  words[1];
						updatedLine += ",";
						updatedLine +=  service_id;
					
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
				writer = new PrintWriter(new FileOutputStream(new File(serviceFile)));	
				writer.print(data);
				writer.close();	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Gets the bookings for a particular flight ID.
	 * @param flight_id The flight ID to get the bookings of.
	 * @return A List of Booking objects which correspond to the given flight ID.
	 */
	public List<Booking> getBookingsForFlight(String flight_id){
		/*
		 * Remember to only return the bookings that are not Canceled.
		 */
		 
		List<Booking> bookings = null;
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(bookingFile));			
			while((oneLine = reader.readLine()) != null){
				String[] words = oneLine.split(",");
				
				if(words[1].equals(flight_id)){	
					int booking_id = Integer.parseInt(words[0]);
					double total_price = Double.parseDouble(words[2]);
					String status = words[3];
					String date = words[4];
					if(!status.equals("Cancelled")){	//If the flight is not "Cancelled",then add;
						Booking bk = new Booking(booking_id,flight_id,status,total_price,date);
						bookings.add(bk);
					}
				}
			}	
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bookings;
	}
	
	/**
	 * Gets all bookings.
	 * @return A List of Booking objects.
	 */
	public List<Booking> getAllBookings(){
		List<Booking> bookings = new ArrayList<>();
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(bookingFile));			
			while((oneLine = reader.readLine()) != null){
				String[] words = oneLine.split(",");
				
				int booking_id = Integer.parseInt(words[0]);
				String flight_id = words[1];
				double total_price = Double.parseDouble(words[2]);
				String status = words[3];
				String date = words[4];
				
				Booking bk = new Booking(booking_id,flight_id,status,total_price,date);
				bookings.add(bk);
			}	
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bookings;
	}
}
