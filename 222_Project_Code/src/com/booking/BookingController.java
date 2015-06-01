/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.profile.ProfileController;
import com.helpers.Booking;
import com.helpers.Flight;
import com.helpers.Person;
import com.helpers.Service;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class provides methods pertaining to any required 
 * functionality and processing that is related to bookings.
 * @author Michael Y.M. Kong
 */
public class BookingController {
	/**
	 * Scanner object to use the standard in from the console.
	 */
	private Scanner in;
	
	/**
	 * Holds the current customer username if the user is a customer or travel
	 * agency. Else it will be null.
	 */
	private String customerUsername;
	
	/**
	 * AirportController requires the BookingEntity class to write/read data
	 * to the database.
	 */
	private BookingEntity be;
	
	/**
	 * AirportController requires the FleetController class to query fleet
	 * data.
	 */
	private FleetController ftc;
	
	/**
	 * AirportController requires the FlightController class to query flight
	 * data.
	 */
	private FlightController fc;
	
	/**
	 * AirportController requires the ServiceController class to query service
	 * data.
	 */
	private ServiceController sc;
	
	/**
	 * AirportController requires the ProfileController class to query profile
	 * and customer data.
	 */
	private ProfileController pc;
	
	/**
	 * The default discount ratio.
	 */
	private double discountRatio;
	
	/**
	 * The default cancellation fee.
	 */
	private double cancellationFee;
	
	/**
	 * Creates a BookingController.
	 * Initializes the discount ratio and cancellation fee from database.
	 */
	public BookingController(){
                in = new Scanner(System.in);
                customerUsername = null;
                be = new BookingEntity();
                ftc = new FleetController();
                fc = new FlightController();
                sc = new ServiceController();
                pc = new ProfileController();
		discountRatio = be.getDiscountRatio();
		cancellationFee = be.getCancellationFee();
	}
	
	/**
	 * Method for normal staff to make a booking on behalf of other customers.
	 */
	public void makeBooking(){
		System.out.println("\nMAKE BOOKING FOR CUSTOMER");
		setUsername(pc.enterUsername());
		makeBooking("CUS");
		setUsername(null);
	}
	
	/**
	 * The method that is called to make a booking.
	 * @param role The role that is making this booking
	 */
	public void makeBooking(String role){
		Map.Entry<Flight, Boolean> flight_choice;
		String[] customer_usernames = null;
		List<Integer> person_ids = null;
		List<Ticket> tickets = new ArrayList<>();
		List<ServiceBooking> services_booked = new ArrayList<>();
		
		System.out.println("\nMAKE BOOKING");
		
		//check if this user can fly
		if("CUS".equals(role)){
			String fly_status = pc.canUserFly(customerUsername);
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
				Map.Entry<String, Double> seat;
				double total_services_price = 0.0;
				
				System.out.println("\nTicket for Customer with username \"" + username + "\"");
				
				seat = chooseSeat(available_seats, total_seats, seat_prices);
				
				in.nextLine(); //clear buffer
				
				System.out.print("Do you want to book services for this customer? (Y/N): ");
				char char_choice = in.nextLine().charAt(0);
				
				if (char_choice == 'Y' || char_choice == 'y') {
					List<Integer> service_ids = bookServices(flight_choice.getValue());
					
					for (Integer service_id: service_ids) {
						services_booked.add(new ServiceBooking(current_ticket_id, service_id));
						total_services_price += sc.getService(service_id).getCost();
					}
				}
				tickets.add(new Ticket(current_ticket_id, username, -1, seat.getKey(), seat.getValue() + total_services_price));
				current_ticket_id++; //increment to next ticket
			} //end for loop
		} //end if for customer computation
		/* End Customer Tickets */
		
		/* Start Solo Customer Booking */
		if ("CUS".equals(role)){
			Map.Entry<String, Double> seat;
			double total_services_price = 0.0;
			
			System.out.println("\nYour Ticket (" + customerUsername + ")");
				
			seat = chooseSeat(available_seats, total_seats, seat_prices);

			in.nextLine(); //clear buffer

			System.out.print("Do you want to book services for yourself? (Y/N): ");
			char char_choice = in.nextLine().charAt(0);

			if (char_choice == 'Y' || char_choice == 'y') {
				
				List<Integer> service_ids = bookServices(flight_choice.getValue());

				for (Integer service_id: service_ids) {
					services_booked.add(new ServiceBooking(current_ticket_id, service_id));
					total_services_price += sc.getService(service_id).getCost();
				}
			}
			
			tickets.add(new Ticket(current_ticket_id, customerUsername, -1, seat.getKey(), seat.getValue() + total_services_price));
			
			current_ticket_id++; //increment to next ticket
		}
		/* End Solo Customer Booking */
		
		/* Start Person Tickets */
		if (person_ids != null) {
			for (Integer person_id: person_ids) {
				Map.Entry<String, Double> seat;
				double total_services_price = 0.0;
				
				System.out.println("\nTicket for Person with ID \"" + person_id + "\"");
				
				seat = chooseSeat(available_seats, total_seats, seat_prices);
				
				in.nextLine(); //clear buffer
				
				System.out.print("Do you want to book services for this person? (Y/N): ");
				char char_choice = in.nextLine().charAt(0);
				
				if (char_choice == 'Y' || char_choice == 'y') {
					List<Integer> service_ids = bookServices(flight_choice.getValue());
					
					for (Integer service_id: service_ids) {
						services_booked.add(new ServiceBooking(current_ticket_id, service_id));
						total_services_price += sc.getService(service_id).getCost();
					}
				}
				tickets.add(new Ticket(current_ticket_id, null, person_id, seat.getKey(), seat.getValue() + total_services_price));
				
				current_ticket_id++; //increment to next ticket
			} //end for loop
		}
		/* End Person Tickets */
		/* End Fill Ticket Details */
		
		/* Start Display Booking Summary */
		String origin;
		String destination;
		double total_price = 0.0;
		
		Map.Entry<String, String> route_locations
				= fc.getRouteCities(chosen_flight.getRouteNumber());
		
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
		}
		
		System.out.println();
		
		System.out.printf("%-13s$%.2f\n", "TOTAL PRICE: ", total_price);
		/* End Display Booking Summary */
		
		/* Start Payment */
		if ("CUS".equals(role)){
			int frequent_flier_points = pc.getFrequentFlierPoints(customerUsername);

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
					pc.setFrequentFlierPoints(customerUsername, frequent_flier_points);
				}
			}
		}
		
		if (total_price == 0) {
			System.out.println("Your frequent flier points are sufficient to cover your booking. ");
		} else{
			System.out.print("Enter any key to proceed with payment: ");
			in.nextLine(); //just get anything; doesn't matter
			
			pc.chargeAccount(customerUsername, total_price);
			
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
	 * The UI for users to choose seats for the booking.
	 * @param available_seats Available seats on this flight
	 * @param total_seats Total seats that this flight's plane can accommodate
	 * @return The seat number and the price of that seat.
	 */
	private Map.Entry<String, Double>
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
	 * The UI for users to choose a flight.
	 * @return The Flight object and a boolean whether it's an international flight
	 */
	private Map.Entry<Flight, Boolean> chooseFlight(){
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
			System.out.print("\nPlease enter the origin airport of choice (airport name): ");
			origin = in.nextLine();

			System.out.print("Please enter the destination airport of choice (airport name): ");
			destination = in.nextLine();

			flights = fc.getFlights(origin, destination);
			
			if (flights == null || flights.isEmpty()) {
				System.out.println("Flights from " + origin + " to " + destination + " are not available!");
				System.out.println("Please try again!\n");
				isOkay = false;
			}
                        
                        is_international_flight = fc.isInternationalFlight(origin, destination);
                        
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
		
		return new AbstractMap.SimpleImmutableEntry<>(flight_choice, is_international_flight);
		/* End Select Flight */
	}
	
	/**
	 * Called to add Customers to the booking.
	 * @return String array of customer usernames
	 */
	private String[] addCustomers(){
		String[] customer_usernames;
		boolean areUsernamesOkay;
		
		do {
			areUsernamesOkay = true;
			customer_usernames = pc.enterUsernames();
			
			//check if any users can't fly
			for (String customer_username : customer_usernames) {
				String fly_status = pc.canUserFly(customer_username);
				
				if ("no fly".equals(fly_status)) {
					System.out.println("ALERT: Customer " + customer_username + " is not allowed to fly.");
					areUsernamesOkay = false;
				}
			}
		} while (!areUsernamesOkay);
		
		return customer_usernames;
	}
	
	/**
	 * Called to add Persons to the booking.
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
				
				System.out.print("Do this person have a passport? (Y/N): ");
				hasPassport = in.nextLine();
				
				Person person = new Person(title, firstName, lastName,
						gender, DOB, phoneNumber, email,
						street, state, city, country,
						creditCardType, creditCardNumber, hasPassport);
				
				person_ids.add(pc.addPerson(person));
				
				System.out.println("\nPerson " + firstName + " " + lastName + " has been added.\n");
				
				System.out.print("\nDo you want to add another person? (Y/N): ");
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
	 * The UI for users to book services for a ticket.
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
	
	/**
	 * Allows dynamic checking whether the user who is canceling a booking
	 * is a customer role or not. If the user is not a customer, then the user
	 * must key in a valid username before selecting a flight to cancel
	 * for that customer.
	 */
	public void cancelBooking(){
		System.out.println("\nCANCEL BOOKING");
		
		if (customerUsername == null) { //if it is not a customer
                        boolean isOkay = cancelBooking(pc.enterUsername());
			if (isOkay) {
				System.out.println("The booking has been cancelled.\n");
			}
		} else{
                        boolean isOkay = cancelBooking(customerUsername);
			if (isOkay) {
				pc.chargeAccount(customerUsername, cancellationFee);
				System.out.printf("A cancellation fee of $%.2f has been charged to your account.\n", cancellationFee);
			}
		}
	}
	
	/**
	 * Called to cancel a booking.
	 * @param username Used to find the bookings for this user
	 * @return True if a booking was canceled, false otherwise.
	 */
	private boolean cancelBooking(String username){
		List<Booking> bookings = be.getBookings(username);
		int i = 1;
		int choice = 0;
		boolean isOkay;
		
		if (!viewBookings(bookings, true)) {
			return false;
		}
		
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
		
		Flight flight = fc.getFlight(bookings.get(choice - 1).getFlightId());
		int[] available_seats = flight.getAvailableSeats();
		
		//1. get the List of tickets associated with this flight
		List<Ticket> tickets = be.getTickets(bookings.get(choice - 1).getBookingId());
		
		for (Ticket ticket: tickets) {
			char seat_class = ticket.getSeatNumber().charAt(0); //get the first character
			
			switch (seat_class){
				case 'F': //first class
					available_seats[0]++;
					break;
				case 'B': //business class
					available_seats[1]++;
					break;
				case 'P': //premium economy class
					available_seats[2]++;
					break;
				case 'E': //economy class
					available_seats[3]++;
					break;
			}
		}
		
		fc.updateAvailableSeats(bookings.get(choice - 1).getFlightId(), available_seats);
		
		be.cancelBooking(bookings.get(choice - 1).getBookingId());
		
		return true;
	}
	
	/**
	 * For external classes to call.
	 * Displays all bookings for a customer.
	 * @return False if no bookings available
	 */
	public boolean viewBookings(){
		return viewBookings(be.getBookings(customerUsername), false);
	}
	
	/**
	 * For the cancelBooking method to call straightaway.
	 * Is called only within this function.
	 * @param customer_bookings List of customer bookings to display
	 * @return False if no bookings available
	 */
	private boolean viewBookings(List<Booking> customer_bookings, boolean check_cancelled){
		List<Booking> bookings = customer_bookings;
		int i = 1;
		
		if (bookings == null) {
			System.out.println("\nYou have no bookings.\n");
			return false;
		}
		
		List<Map.Entry<Booking, List<Ticket>>> bookings_and_tickets = new ArrayList<>();
		
		for (Booking booking: bookings) {
			if (check_cancelled) {
				
				//do not get the cancelled bookings
				if (!"Cancelled".equals(booking.getStatus())) {
					bookings_and_tickets.add(new AbstractMap.SimpleImmutableEntry<>(booking, be.getTickets(booking.getBookingId())));
				}
			} else{
				bookings_and_tickets.add(new AbstractMap.SimpleImmutableEntry<>(booking, be.getTickets(booking.getBookingId())));
			}
		}
		
		for (Map.Entry<Booking, List<Ticket>> entry: bookings_and_tickets) {
			int j = 1;
			
			System.out.printf("%-4s%-15s%-15s%-15s%-16s%-25s\n", "#", "Booking ID",
				"Flight ID", "Booking Status", "Total Cost", "Booking Date");
			System.out.printf("%-4s", i + ". ");
			System.out.println(entry.getKey().toString());
			
			List<Ticket> tickets = entry.getValue();
			
			System.out.println();
			
			System.out.println("\tTickets for this booking:");
			System.out.printf("\t%-4s%-4s%-15s%-25s%-12s\n", "#", "ID",
					"Price (AUD)", "Username/Person ID", "Seat Number");
			for (Ticket ticket: tickets) {
				System.out.printf("\t%-4s", j + ". ");
				System.out.println(ticket.toString());
				j++;
			}
			i++;
			System.out.println();
		}
		
		if (bookings_and_tickets.isEmpty()) {
			System.out.println("\nYou have no active bookings.\n");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method for normal staff to edit services on behalf of other customers.
	 */
	public void editServices(){
		if (customerUsername == null) {
			System.out.println("\nEDIT SERVICES FOR CUSTOMER");
			editServices(pc.enterUsername());
		} else{
			editServices(customerUsername);
		}
	}
	
	/**
	 * The method that is called to edit services.
	 * @param username 
	 */
	public void editServices(String username){
		boolean isOkay;
		int choice = 0;
		int booking_id;
		int ticket_id;
		List<Booking> bookings = be.getBookings(username);
		
		System.out.println("\nEDIT SERVICES");
		
		if (!viewBookings(bookings, false)) { //if there are no bookings, return.
			return;
		}
		
		do {
			isOkay = true;
			System.out.print("Please select the booking that you want to modify the service of: ");
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
		
		booking_id = bookings.get(choice - 1).getBookingId();
		List<Ticket> tickets = be.getTickets(booking_id);
		
		System.out.printf("%-4s%-4s%-15s%-25s%-12s\n", "#", "ID", "Price (AUD)", "Username/Person ID", "Seat Number");
		for (int i = 0; i < tickets.size(); i++) {
			System.out.printf("%-4s", (i + 1) + ". ");
			System.out.println(tickets.get(i).toString());
		}
		
		do {
			isOkay = true;
			System.out.print("Please select the ticket that you want to modify the service of: ");
			try {
				choice = in.nextInt();
				
				if (choice < 1 || choice > tickets.size()) {
					System.out.println("The ticket selected does not exist. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("The input is not valid. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		Ticket chosen_ticket = tickets.get(choice - 1);
		ticket_id = chosen_ticket.getTicketId();
		List<ServiceBooking> services_booked = be.getServicesBooked(booking_id, ticket_id);
		List<Service> services = new ArrayList<>();
		
		//get all the services booked
		for (ServiceBooking service_booked: services_booked) {
			Service service = sc.getService(service_booked.getServiceId());
			services.add(service);
		}
		
		String[] choices = {"Add Service", "Delete Service", "Cancel"};
		
		System.out.println();
		
		for (int i = 0; i < choices.length; i++) {
			System.out.print((i + 1) + ". ");
			System.out.println(choices[i]);
		}
		
		do {
			isOkay = true;
			System.out.print("Please select an option: ");
			
			try {
				choice = in.nextInt();
				if (choice < 0 || choice > choices.length) {
					System.out.println("That option is out of range. Please try again!\n");
					isOkay = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
                if (choice == 3) {
                    return;
                }
                
		in.nextLine();
		
		System.out.println("\nCurrent Services");
		System.out.printf("%-4s%-20s%-14s\n", "#", "Name", "Price (AUD)");
		for (int i = 0; i < services.size(); i++) {
			System.out.printf("%-4s", (i + 1) + ". ");
			System.out.println(services.get(i).getString());
		}
		
                double total_price = 0;
                
		switch (choice){
			case 1:
				total_price = addService(services_booked);
				break;
			case 2:
				total_price = deleteService(services_booked);
				break;
		}
                pc.chargeAccount(username, total_price);
		be.updateServicesBooked(services_booked);
	}
	
	/**
	 * Allows the user to add a service to a ticket
	 * @param services_booked List of services booked
	 */
	private double addService(List<ServiceBooking> services_booked){
		List<Service> services = sc.getServices(true);
		String[] choices;
		boolean isOkay;
		int booking_id = services_booked.get(0).getBookingId();
		int ticket_id = services_booked.get(0).getTicketId();
		
		System.out.println("Services available:");
		System.out.printf("%-5s%-25s%-10s\n", "#", "Service Name", "Price (AUD)");
		
		int i = 1;
		for (Service service: services) {
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
		
		double total_price = 0;
		
		for (String choice: choices) {
			services_booked.add(new ServiceBooking(booking_id, ticket_id, Integer.parseInt(choice)));
			total_price += services.get(Integer.parseInt(choice) - 1).getCost();
		}
                
		System.out.printf("Extra services have been added to this ticket. $%.2f has been charged.\n", total_price);
                
                return total_price;
	}
	
	/**
	 * Allows the user to delete a service from a ticket
	 */
	private double deleteService(List<ServiceBooking> services_booked){
		List<Service> services = sc.getServices(true);
		String[] choices;
		boolean isOkay;
		
		do {			
			isOkay = true;
			System.out.print("Enter the numbers of the services separated by a space: ");
			choices = in.nextLine().split(" "); 

			for (String str: choices) {
				int choice = Integer.parseInt(str);
				if (choice < 1 || choice > services_booked.size()) {
					isOkay = false;
					System.out.println("One or more of the chosen options do not exist. Please try again!\n");
					break;
				} 
			}
		} while (!isOkay);
		
		double total_price = 0;
		
		for (String choice: choices) {
			ServiceBooking service = services_booked.remove(Integer.parseInt(choice) - 1);
                        
			total_price += services.get(service.getServiceId() - 1).getCost();
		}
                
		System.out.printf("The services have been deleted from this ticket. $%.2f has been credited.\n", total_price);
                
                return -total_price;
	}
	
	/**
	 * Called to move passengers between flights by booking.
	 */
	public void movePassengers(){
		System.out.println("\nMOVE PASSENGERS BETWEEN FLIGHTS");
		
		System.out.println("\nFlight to move from:");
		String current_flight_id = fc.enterFlightId(false);
		List<Booking> bookings = getBookingsForFlight(current_flight_id);
		Booking booking;
		int choice = 0;
		boolean isOkay;
		
		System.out.printf("%-4s%-15s%-15s%-15f%-25s\n", "#", "Booking ID", "Booking Status", "Total Price (AUD)", "Booking Date");
		
		for (int i = 0; i < bookings.size(); i++) {
			System.out.printf("%-4s", (i + 1) + ". ");
			System.out.println(bookings.get(i).getString());
		}
		
		do {			
			isOkay = true;
			System.out.print("Enter the number corresponding to the booking: ");
			
			try {
				choice = in.nextInt();
				
				if (choice < 1 || choice > bookings.size()) {
					isOkay = false;
					System.out.println("This option does not exist. Please try again!\n");
				} 
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("An invalid character detected. Please try again!\n");
			}
		} while (!isOkay);
		
		booking = bookings.get(choice - 1);
		
		System.out.println("\nFlight to move to:");
		String new_flight_id = fc.enterFlightId(false);
		
		booking.setFlightId(new_flight_id);
		
		
		
		//figure out logic how to move passengers from current flight to new flight
	}
	
	/**
	 * Called to move passengers within a flight.
	 */
	public void changePassengerSeating(){
		System.out.println("\nCHANGE PASSENGER SEATING");
	}
	
	/**
	 * Gets the bookings for a particular flight ID.
	 * @param flight_id The flight ID to get the bookings of.
	 * @return A List of Booking objects which correspond to the given flight ID.
	 */
	private List<Booking> getBookingsForFlight(String flight_id){
		return be.getBookingsForFlight(flight_id);
	}
	
	/**
	 * Gets all the services booked for a particular booking.
	 * @param booking_id The booking ID to get the services of.
	 * @param tickets The tickets corresponding to the booking ID.
	 * @return A list of all the bookings for the given booking ID.
	 */
	public List<ServiceBooking> getServicesBookedForBooking(int booking_id, List<Ticket> tickets){
		List<ServiceBooking> services_booked = new ArrayList<>();
		
		for (Ticket ticket: tickets) {
			List<ServiceBooking> sb = be.getServicesBooked(booking_id, ticket.getTicketId());
			services_booked.addAll(sb);
		}
		
		return services_booked;
	}
	
	/**
	 * Gets the services booked for a ticket.
	 * @param ticket The ticket to get the services for.
	 * @return A List of ServiceBooking objects.
	 */
	public List<ServiceBooking> getServicesBookedForTicket(Ticket ticket){
		return be.getServicesBooked(ticket.getBookingId(), ticket.getTicketId());
	}
	
	/**
	 * Called to set the discount ratio of the frequent flier points.
	 */
	public void setDiscountRatio(){
		double ratio = discountRatio;
		boolean isOkay;
		
		System.out.println("\nSET DISCOUNT RATIO");
		
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
	
	/**
	 * Allows the reservation system manager to set a cancellation fee.
	 */
	public void setCancellationFee(){
		double cancellation_fee = cancellationFee;
		boolean isOkay;
		
		System.out.println("\nSET CANCELLATION FEE");
		
		do {
			isOkay = true;
			try {
				System.out.printf("The current cancellation fee is $%.2f\n", cancellationFee);
				System.out.print("Please enter the new cancellation fee: ");
				cancellation_fee = in.nextDouble();
				
				if (cancellation_fee < 0) {
					System.out.println("The input is not valid. Please try again!\n");
					isOkay = false;
				}
				
			} catch (InputMismatchException e) {
				System.out.println("The input is not valid. Please try again!\n");
				isOkay = false;
			}
		} while (!isOkay);
		
		cancellationFee = cancellation_fee;
		be.setCancellationFee(cancellation_fee);
                
                System.out.printf("The cancellation fee has been set to $%.2f\n", cancellationFee);
	}
	
	/**
	 * Set the username for the customer when a CustomerRole instance is using
	 * this class.
	 * @param username 
	 */
	public void setUsername(String username){
		customerUsername = username;
	}
	
	/**
	 * Allows user to get bookings for a particular month.
	 * @param month The month of to search for.
	 * @param year The year to search for.
	 * @return A List of Map.Entry Objects which contain a Booking object and
	 * a of List of Ticket objects that correspond with the the given month.
	 */
	public List<Map.Entry<Booking, List<Ticket>>> getBookingsForMonth(String month, int year){
		List<Booking> all_bookings = be.getAllBookings();
		List<Map.Entry<Booking, List<Ticket>>> bookings_and_tickets = new ArrayList<>();
		
		for (Booking booking: all_bookings) {
			String booking_date = booking.getBookingDate();
			String booking_month = booking_date.substring(15, 18);
			int booking_year = Integer.parseInt(booking_date.substring(19));
			
			if (month.equals(booking_month) && year == booking_year) { //if this is a wanted booking
				bookings_and_tickets.add(new AbstractMap.SimpleImmutableEntry<>(booking, be.getTickets(booking.getBookingId())));		
			}
		}
		return bookings_and_tickets;
	}
	
	/**
	 * Gets the top 5 services booked and their count for all time.
	 * @return A List of 5 Map Entry objects containing a Service – Integer pair.
	 */
	public List<Map.Entry<Service, Integer>> getTop5ServicesBooked(){
		List<ServiceBooking> services_booked = be.getAllServicesBooked();
		
		//Integer 1 = service ID
		//Integer 2 = occurences of service ID
		Map<Integer, Integer> popular_service_map = new HashMap<>();
		
		for (ServiceBooking serviceBooking: services_booked) {
			int cur_service_id = serviceBooking.getServiceId();
			
			Integer cur_service_count = popular_service_map.remove(cur_service_id);
			
			if (cur_service_count == null) {
				popular_service_map.put(cur_service_id, 1);
			} else{
				cur_service_count++;
				popular_service_map.put(cur_service_id, cur_service_count);
			}
		}
		
		List<Map.Entry<Integer, Integer>> service_list = new ArrayList<>(popular_service_map.entrySet());
    
		Collections.sort(service_list, new Comparator(){
			@Override
			public int compare(Object obj1, Object obj2){
				return (
					(Comparable)((Map.Entry)(obj1)).getValue()
				).compareTo(((Map.Entry)(obj2)).getValue()); 
			}
		});
		
		List<Map.Entry<Service, Integer>> popular_service_list = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) { //get only 5 objects
			Map.Entry<Integer, Integer> service = service_list.get(i);
			popular_service_list.add(
					new AbstractMap.SimpleImmutableEntry<>(sc.getService(service.getKey()), service.getValue())
			);
		}
		
		return popular_service_list;
	}
	
	/**
	 * Allows the user to enter in a (MMM) format month.
	 * @return The month in (MMM) format.
	 */
	public String enterMonth(){
		String month;
		boolean isOkay;
		
		do {			
			isOkay = true;
			
			System.out.print("Please enter a month to search for (MMM): ");
			month = in.nextLine();
			
			switch(month){
				case "Jan":
				case "Feb":
				case "Mar":
				case "Apr":
				case "May":
				case "Jun":
				case "Jul":
				case "Aug":
				case "Sep":
				case "Oct":
				case "Nov":
				case "Dec":
					//nothing to do because just check if the month corresponds to any of the above.
					break;
				default:
					isOkay = false;
					System.out.println("This month does not exist. Please try again!\n");
					break;
			}
		} while (!isOkay);
		
		return month;
	}
	
	/**
	 * Allows the user to enter in a (YYYY) format year.
	 * @return The year in (YYYY) format.
	 */
	public int enterYear(){
		int year = 0;
		boolean isOkay;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter a year to search for (YYYY): ");
			
			try {
				year = in.nextInt();
				
				if (year < 1000) {
					isOkay = false;
					System.out.println("Year must be 4 numbers long. Please try again!\n");
				}
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid input detected. Please try again!\n");
			}
		} while (!isOkay);
		
		return year;
	}

	/**
	 * For the cancelBooking method to call straightaway
	 * @param customer_bookings
	 */
	private void viewBookings(List<Booking> customer_bookings) {
		// TODO - implement BookingController.viewBookings
		throw new UnsupportedOperationException();
	}
}
