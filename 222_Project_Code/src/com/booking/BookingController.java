/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Booking;
import com.profile.PersonController;
import com.profile.ProfileController;
import com.helpers.Flight;
import com.helpers.Person;
import com.helpers.Service;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class BookingController {
	private Scanner in = new Scanner(System.in);
	private String customerUsername = null;
	
	private BookingEntity be = new BookingEntity();
	private FleetController ftc = new FleetController();
	private FlightController fc = new FlightController();
	private PersonController pc = new PersonController();
	private ServiceController sc = new ServiceController();
	private ProfileController pfc = new ProfileController();
	
	private double discountRatio = 1; //default
	
	/**
	 * 
	 * @param username The username of the customer if the Customer role is booking a flight
	 */
	public BookingController(){
		discountRatio = be.getDiscountRatio();
	}
	
	/**
	 * 
	 * @param role The role that is making this booking
	 */
	public void makeBooking(String role){
		AbstractMap.SimpleImmutableEntry<Flight, Boolean> flight_choice;
		String[] customer_usernames = null;
		List<Integer> person_ids = null;
		List<Ticket> tickets = new ArrayList<>();
		List<ServiceBooking> services_booked = new ArrayList<>();
		
		//check if this user can fly
		if("CUS".equals(role)){
			String fly_status = pfc.canUserFly(customerUsername);
			switch (fly_status) {
				case "watch":
					System.out.println("\nWARNING: Your profile is currently marked as \"WATCH\".");
					System.out.println("You may have limited access in booking flights.");
					break;
				case "no fly":
					System.out.println("\nWARNING: Your profile is currently marked as \"NO FLY\".");
					System.out.println("You may not book a flight.");
					return;
					//break; don't need break because we are returning
			}
		}
		
		flight_choice = chooseFlight();
		
		in.nextLine(); //clear buffer
		
		/* Start Add Customers and Persons */
		switch (role){
			case "TA":
				customer_usernames = addCustomers();
				person_ids = addPersons();
				break;
			case "CUS":
				customer_usernames = null;
				person_ids = addPersons();
				break;
			default:
				break;
		}
		/* End Add Customers and Persons */
		
		/* Start Fill Ticket Details */
		Flight chosen_flight = flight_choice.getKey();
		int[] available_seats = chosen_flight.getAvailableSeats();
		int[] total_seats = ftc.getSeatsForPlane(chosen_flight.getPlaneID());
		double[] seat_prices = fc.getSeatPrices(chosen_flight.getFlightID());
		int current_ticket_id = 1;
		
		/* Start Customer Tickets */
		if ("TA".equals(role) && customer_usernames != null){ //extra computation for customers
			for (String username: customer_usernames) {
				AbstractMap.SimpleImmutableEntry<String, Double> seat;
				
				System.out.println("\nTicket for Customer with username \"" + username + "\"");
				
				seat = chooseSeat(available_seats, total_seats, seat_prices);
				
				in.nextLine(); //clear buffer
				
				tickets.add(new Ticket(current_ticket_id, username, -1, seat.getKey(), seat.getValue()));
				
				System.out.print("Do you want to book services for this customer? (Y/N): ");
				char char_choice = in.nextLine().charAt(0);
				
				if (char_choice == 'Y' || char_choice == 'y') {
					List<Integer> service_ids = bookServices(flight_choice.getValue());
					
					for (Integer service_id: service_ids) {
						services_booked.add(new ServiceBooking(current_ticket_id, service_id));
					}
				}
				current_ticket_id++; //increment to next ticket
			} //end for loop
		} //end if for customer computation
		/* End Customer Tickets */
		
		/* Start Solo Customer Booking */
		if ("CUS".equals(role)){
			AbstractMap.SimpleImmutableEntry<String, Double> seat;
			
			System.out.println("\nYour Ticket (" + customerUsername + ")");
				
			seat = chooseSeat(available_seats, total_seats, seat_prices);

			in.nextLine(); //clear buffer

			tickets.add(new Ticket(current_ticket_id, customerUsername, -1, seat.getKey(), seat.getValue()));

			System.out.print("Do you want to book services for yourself? (Y/N): ");
			char char_choice = in.nextLine().charAt(0);

			if (char_choice == 'Y' || char_choice == 'y') {
				List<Integer> service_ids = bookServices(flight_choice.getValue());

				for (Integer service_id: service_ids) {
					services_booked.add(new ServiceBooking(current_ticket_id, service_id));
				}
			}
			current_ticket_id++; //increment to next ticket
		}
		/* End Solo Customer Booking */
		
		/* Start Person Tickets */
		if (person_ids != null) {
			for (Integer person_id: person_ids) {
				AbstractMap.SimpleImmutableEntry<String, Double> seat;
				
				System.out.println("\nTicket for Person with ID \"" + person_id + "\"");
				
				seat = chooseSeat(available_seats, total_seats, seat_prices);
				
				in.nextLine(); //clear buffer
				
				tickets.add(new Ticket(current_ticket_id, null, person_id, seat.getKey(), seat.getValue()));
				
				System.out.print("Do you want to book services for this person? (Y/N): ");
				char char_choice = in.nextLine().charAt(0);
				
				if (char_choice == 'Y' || char_choice == 'y') {
					List<Integer> service_ids = bookServices(flight_choice.getValue());
					
					for (Integer service_id: service_ids) {
						services_booked.add(new ServiceBooking(current_ticket_id, service_id));
					}
				}
				current_ticket_id++; //increment to next ticket
			} //end for loop
		}
		/* End Person Tickets */
		/* End Fill Ticket Details */
		
		/* Start Display Booking Summary */
		String origin;
		String destination;
		double total_price = 0.0;
		
		AbstractMap.SimpleImmutableEntry<String, String> route_locations
				= fc.getRouteLocations(chosen_flight.getRouteNumber());
		
		origin = route_locations.getKey();
		destination = route_locations.getValue();
		
		System.out.println("---------- BOOKING SUMMARY ----------");
		
		System.out.println("FLIGHT:");
		System.out.println("Origin: " + origin);
		System.out.println("Destination: " + destination);
		System.out.println(chosen_flight.toString());
		
		System.out.println(); 
		
		System.out.println("TICKETS BOOKED:");
		System.out.println(tickets.size() + " tickets booked");
		System.out.printf("%-4s%-15s%-25s%-12s\n", "ID", "Price (AUD)", "Username/Person ID", "Seat Number");
		for (Ticket ticket: tickets) {
			System.out.println(ticket.toString());
			total_price += ticket.getPrice();
		}
		
		System.out.println();
		
		System.out.println("SERVICES BOOKED:");
		System.out.printf("%-12s%-12s\n", "Ticket ID", "Service Name");
		for (ServiceBooking service_booked: services_booked) {
			Service service = sc.getService(service_booked.getServiceId());
			
			System.out.printf("%-12s", service_booked.getTicketId());
			System.out.printf("%-12s", service.getName());
			System.out.println();
			total_price += service.getCost();
		}
		
		System.out.println();
		
		System.out.printf("%-13s$%.2f\n", "TOTAL PRICE: ", total_price);
		/* End Display Booking Summary */
		
		/* Start Payment */
		if ("CUS".equals(role)){
			int frequent_flier_points = pfc.getFrequentFlierPoints(customerUsername);

			if(frequent_flier_points != 0){
				System.out.print("Do you want to use your frequent flier points to get a discount? (Y/N): ");
				char choice = in.nextLine().charAt(0);

				if (choice == 'Y' || choice == 'y') {
					double discount = frequent_flier_points * discountRatio;
					if (discount > total_price) { //if the discount is more than the total price
						discount = discount - total_price;
						total_price = 0;
						frequent_flier_points = (int)Math.floor(discount / discountRatio);
					} else{
						total_price = total_price - discount;
						frequent_flier_points = 0;
					}
					System.out.println("Points remaining: " + frequent_flier_points);
					pfc.setFrequentFlierPoints(customerUsername, frequent_flier_points);
				}
			}
		}
		
		if (total_price == 0) {
			System.out.println("Your frequent flier points are sufficient to cover your booking. ");
		} else{
			System.out.print("Enter any key to proceed with payment: ");
			in.nextLine(); //just get anything; doesn't matter
			
			System.out.printf("\nYour credit card has been charged $%.2f!\nThank you for making a booking with us!\n", total_price);
			System.out.println();
		}
		/* End Payment */
		
		//update the available seats on the flight
		fc.updateAvailableSeats(flight_choice.getKey().getFlightID(), available_seats);
		
		//save booking
		be.saveBooking(flight_choice.getKey().getFlightID(), tickets, services_booked);
	}
	
	/**
	 * 
	 * @param available_seats Available seats on this flight
	 * @param total_seats Total seats that this flight's plane can accommodate
	 * @return The seat number
	 */
	private AbstractMap.SimpleImmutableEntry<String, Double>
			chooseSeat(int[] available_seats, int[] total_seats, double[] seat_prices){
		boolean isOkay;
		int choice = 0;
		
		System.out.printf("1. First class (" + available_seats[0] + " seats available) $%.2f\n", seat_prices[0]);
		System.out.printf("2. Business class (" + available_seats[1] + " seats available) $%.2f\n", seat_prices[1]);
		System.out.printf("3. Premium economy class (" + available_seats[2] + " seats available) $%.2f\n", seat_prices[2]);
		System.out.printf("4. Economy class (" + available_seats[3] + " seats available) $%.2f\n", seat_prices[3]);

		do {
			isOkay = true;

			System.out.print("Please enter your choice: ");

			try {
				choice = in.nextInt(); 

				if (choice < 1 || choice > 4) {
					isOkay = false;
					System.out.println("This option does not exist. Please try again!\n");
				} else{
					if(available_seats[choice - 1] > 0){
						available_seats[choice - 1]--;
					} else{
						isOkay = false;
						System.out.println("There are no more seats of this class. Please try again!\n");
					}
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("This option does not exist. Please try again!\n");
			}

		} while (!isOkay);
		
		//compute the seat number
		int seat_number = total_seats[choice - 1] - available_seats[choice - 1];
		String seat_class = "";
		double seat_price = seat_prices[choice - 1];
		
		switch(choice){
			case 1:
				seat_class = "F";
				break;
			case 2:
				seat_class = "B";
				break;
			case 3:
				seat_class = "PE";
				break;
			case 4:
				seat_class = "E";
				break;
		}
		
		System.out.println("Your seat number is " + seat_class + seat_number + "\n");
		
		return new AbstractMap.SimpleImmutableEntry<>(seat_class + seat_number, seat_price);
	}
	
	/**
	 * 
	 * @return The Flight object and a boolean whether it's an international flight
	 */
	private AbstractMap.SimpleImmutableEntry<Flight, Boolean> chooseFlight(){
		boolean isOkay;
		String origin;
		String destination;
		List<Flight> flights;
		Flight flight_choice;
		Boolean is_international_flight;
		int choice;
		
		/* Start Enter Origin and Destination choice */
		do {	
			isOkay = true;
			System.out.print("\nPlease enter the flight origin of choice (city name): ");
			origin = in.nextLine();

			System.out.print("Please enter the flight destination of choice (city name): ");
			destination = in.nextLine();

			flights = fc.getFlights(origin, destination);
			
			if (flights == null) {
				System.out.println("Flights from " + origin + " to " + destination + " are not available!");
				System.out.println("Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		/* End Enter Origin and Destination choice */
		
		/* Start Display Flights Available */
		System.out.print("\n#  ");
		System.out.printf("%-15s%-35s%-35s", "Flight ID", "Departure Time", "Arrival Time");
		System.out.println();
		
		int i = 1;
		for (Flight flight: flights) {
			System.out.print(i + ". ");
			System.out.println(flight.toString());
			i++;
		}
		/* End Display Flights Available */
		
		/* Start Select Flight */
		do {	
			isOkay = true;
			System.out.print("Please select your flight: ");
			choice = in.nextInt() - 1;
			
			if (choice < 0 || choice > flights.size()) {
				isOkay = false;
				System.out.println("The selected flight is not valid. Please try again!\n");
			}
		} while (!isOkay);
		
		flight_choice = flights.get(choice);
		is_international_flight = fc.isInternationalFlight(origin, destination);
		
		return new AbstractMap.SimpleImmutableEntry<>(flight_choice, is_international_flight);
		/* End Select Flight */
	}
	
	/**
	 * 
	 * @return String array of customer usernames
	 */
	private String[] addCustomers(){
		String[] customer_usernames;
		boolean areUsernamesOkay;
		
		do {
			System.out.print("Please enter the usernames of existing customers separated by spaces: ");
			customer_usernames = in.nextLine().split(" ");

			areUsernamesOkay = pfc.checkUsernames(customer_usernames);

			if (!areUsernamesOkay) {
				System.out.println("A username that was entered is not valid!\nPlease try again!\n");
			}
			
			//check if any users can't fly
			for (String customer_username : customer_usernames) {
				String fly_status = pfc.canUserFly(customer_username);
				
				if ("no fly".equals(fly_status)) {
					System.out.println("ALERT: Customer " + customer_username + " is not allowed to fly.");
					areUsernamesOkay = false;
				}
			}
		} while (!areUsernamesOkay);
		
		return customer_usernames;
	}
	
	/**
	 * 
	 * @return Integer List of Person IDs
	 */
	private List<Integer> addPersons(){
		System.out.print("Do you want to add persons to the booking? (Y/N): ");
		char choice = in.nextLine().charAt(0);
		List<Integer> person_ids = null;
		
		if (choice == 'Y' || choice == 'y') {
			person_ids = new ArrayList<>();
			
			boolean isDone;
			
			String title;
			String firstName;
			String lastName;
			String gender;
			String DOB;
			String phoneNumber;
			String email;
			String street;
			String state;
			String city;
			String country;
			String creditCardType;
			String creditCardNumber;
			String hasPassport;
			
			do{
				System.out.print("\nTitle: ");
				title = in.nextLine();
				
				System.out.print("First Name: ");
				firstName = in.nextLine();
				
				System.out.print("Last Name: ");
				lastName = in.nextLine();
				
				System.out.print("Gender (Male/Female): ");
				gender = in.nextLine();
				
				System.out.print("Date of Birth: ");
				DOB = in.nextLine();
				
				System.out.print("Phone Number: ");
				phoneNumber = in.nextLine();
				
				System.out.print("Email: ");
				email = in.nextLine();
				
				System.out.print("Street: ");
				street = in.nextLine();
				
				System.out.print("State: ");
				state = in.nextLine();
				
				System.out.print("City: ");
				city = in.nextLine();
				
				System.out.print("Country: ");
				country = in.nextLine();
				
				System.out.print("Credit Card Type: ");
				creditCardType = in.nextLine();
				
				System.out.print("Credit Card Number: ");
				creditCardNumber = in.nextLine();
				
				System.out.print("Do you have a passport? (Y/N): ");
				hasPassport = in.nextLine();
				
				Person person = new Person(title, firstName, lastName,
						gender, DOB, phoneNumber, email,
						street, state, city, country,
						creditCardType, creditCardNumber, hasPassport);
				
				person_ids.add(pc.addPerson(person));
				
				System.out.print("Do you want to add another person? (Y/N): ");
				choice = in.nextLine().charAt(0);
				
				if (choice == 'Y' || choice == 'y') {
					isDone = false;
				} else{
					isDone = true;
				}
			} while(!isDone);
			
		} //end if
		
		return person_ids;
	}
	
	/**
	 * 
	 * @param is_international_flight Services change depending on the type of flight passed in
	 * @return Integer List of Service IDs
	 */
	private List<Integer> bookServices(boolean is_international_flight){
		List<Service> services = sc.getServices(is_international_flight);
		List<Integer> booked_services = new ArrayList<>();
		String[] choices;
		boolean isOkay;
		
		System.out.println("Services available:");
		System.out.printf("%-5s%-25s%-10s\n", "#", "Service Name", "Price (AUD)");
		
		int i = 1;
		for (Service service : services) {
			System.out.printf("%-5s%-25s$%-10.2f\n", (i + ". "), service.getName(), service.getCost());
			i++;
		}
		
		do {			
			isOkay = true;
			System.out.print("Enter the numbers of the services separated by a space: ");
			choices = in.nextLine().split(" "); 

			for (String str: choices) {
				int choice = Integer.parseInt(str);
					if (choice < 1 || choice > services.size()) {
					isOkay = false;
					System.out.println("One or more of the chosen options do not exist. Please try again!\n");
					break;
				} 
			}
		} while (!isOkay);
		
		for (String str: choices) {
			booked_services.add(Integer.parseInt(str));
		}
		
		return booked_services;
	}
	
	public void cancelBooking(){
		List<Booking> bookings = be.getBookings(customerUsername);
		int i = 1;
		int choice = 0;
		boolean isOkay;
		
		if (bookings == null) {
			System.out.println("You have no bookings.\n");
			return;
		}
		
		viewBookings(bookings);

		do {
			isOkay = true;
			System.out.print("Please select the booking that you want to cancel: ");
			try {
				choice = in.nextInt();
				
				if (choice < 1 || choice > bookings.size()) {
					System.out.println("The booking selected does not exist. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("The input is not valid. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		be.cancelBooking(choice); //what do we do when we delete a booking?
		
		//is someone able to manipuate this?????
		System.out.println("A cancellation fee of $10.00 has been charged to your account.\n");
	}
	
	/**
	 * For external classes to call
	 */
	public void viewBookings(){
		viewBookings(be.getBookings(customerUsername));
	}
	
	/**
	 * For the cancelBooking method to call straightaway
	 * @param customer_bookings 
	 */
	private void viewBookings(List<Booking> customer_bookings){
		List<Booking> bookings = customer_bookings;
		int i = 1;
		
		System.out.printf("%-4s%-15s%-10s\n", "#", "Booking ID", "Flight ID");
		for (Booking booking : bookings) {
			System.out.print(i + ". ");
			System.out.println(booking.toString());
			i++;
		}
	}
	
	public void setDiscountRatio(){
		double ratio = discountRatio;
		boolean isOkay;
		
		do {
			isOkay = true;
			try {
				System.out.println("The current discount ratio is " + discountRatio);
				System.out.print("Please enter the new discount ratio: ");
				ratio = in.nextDouble();
				
				if (ratio < 0) {
					System.out.println("The input is not valid. Please try again!\n");
					isOkay = false;
				}
				
			} catch (InputMismatchException e) {
				System.out.println("The input is not valid. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		discountRatio = ratio;
		be.setDiscountRatio(ratio);
	}
	
	public void setUsername(String username){
		customerUsername = username;
	}
	
}
