/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.profile.PersonController;
import com.profile.ProfileController;
import com.helpers.Booking;
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
	/**
	 * Scanner object to use the standard in from the console.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Holds the current customer username if the user is a customer or travel
	 * agency. Else it will be null.
	 */
	private String customerUsername = null;
	
	/**
	 * AirportController requires the BookingEntity class to write/read data
	 * to the database.
	 */
	private BookingEntity be = new BookingEntity();
	
	/**
	 * AirportController requires the FleetController class to query fleet
	 * data.
	 */
	private FleetController ftc = new FleetController();
	
	/**
	 * AirportController requires the FlightController class to query flight
	 * data.
	 */
	private FlightController fc = new FlightController();
	
	/**
	 * AirportController requires the PersonController class to query person
	 * data.
	 */
	private PersonController pc = new PersonController();
	
	/**
	 * AirportController requires the ServiceController class to query service
	 * data.
	 */
	private ServiceController sc = new ServiceController();
	
	/**
	 * AirportController requires the ProfileController class to query profile
	 * and customer data.
	 */
	private ProfileController pfc = new ProfileController();
	
	/**
	 * The default discount ratio is set to 1. So one frequent flier point
	 * is one dollar.
	 */
	private double discountRatio = 1; //default
	
	/**
	 * The default cancellation fee is set to $10.00.
	 */
	private double cancellationFee = 10; //default
	
	/**
	 * Creates a BookingController.
	 * Initializes the discount ratio and cancellation fee from database.
	 */
	public BookingController(){
		discountRatio = be.getDiscountRatio();
		cancellationFee = be.getCancellationFee();
	}
	
	/**
	 * Method for normal staff to make a booking on behalf of other customers.
	 */
	public void makeBooking(){
		setUsername(pfc.enterUsername());
		makeBooking("CUS");
		setUsername(null);
	}
	
	/**
	 * The method that is called to make a booking.
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
			AbstractMap.SimpleImmutableEntry<String, Double> seat;
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
				AbstractMap.SimpleImmutableEntry<String, Double> seat;
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
		
		AbstractMap.SimpleImmutableEntry<String, String> route_locations
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
			
			pfc.chargeAccount(customerUsername, total_price);
			
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
	 * The UI for users to choose a flight.
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
			System.out.print("\nPlease enter the origin airport of choice (airport name): ");
			origin = in.nextLine();

			System.out.print("Please enter the destination airport of choice (airport name): ");
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
	 * Called to add Customers to the booking.
	 * @return String array of customer usernames
	 */
	private String[] addCustomers(){
		String[] customer_usernames;
		boolean areUsernamesOkay;
		
		do {
			areUsernamesOkay = true;
			customer_usernames = pfc.enterUsernames();
			
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
		if (customerUsername == null) { //if it is not a customer
			cancelBooking(pfc.enterUsername());
		} else{
			cancelBooking(customerUsername);
			
			pfc.chargeAccount(customerUsername, cancellationFee);
			
			System.out.printf("A cancellation fee of $%.2f has been charged to your account.\n", cancellationFee);
		}
	}
	
	/**
	 * Called to cancel a booking.
	 * @param username Used to find the bookings for this user
	 */
	private void cancelBooking(String username){
		List<Booking> bookings = be.getBookings(username);
		int i = 1;
		int choice = 0;
		boolean isOkay;
		
		if (!viewBookings(bookings)) {
			return;
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
		
		be.cancelBooking(choice); 
	}
	
	/**
	 * For external classes to call.
	 * Displays all bookings for a customer.
	 * @return False if no bookings available
	 */
	public boolean viewBookings(){
		return viewBookings(be.getBookings(customerUsername));
	}
	
	/**
	 * For the cancelBooking method to call straightaway.
	 * Is called only within this function.
	 * @param customer_bookings List of customer bookings to display
	 * @return False if no bookings available
	 */
	private boolean viewBookings(List<Booking> customer_bookings){
		List<Booking> bookings = customer_bookings;
		int i = 1;
		
		if (bookings == null) {
			System.out.println("\nYou have no bookings.\n");
			return false;
		}
		
		System.out.printf("\n%-4s%-15s%-15s%-15s%-15s\n", "#", "Booking ID", "Flight ID", "Status", "Total Cost");
		for (Booking booking : bookings) {
			System.out.printf("%-4s", i + ". ");
			System.out.println(booking.toString());
			i++;
		}
		return true;
	}
	
	/**
	 * Method for normal staff to edit services on behalf of other customers.
	 */
	public void editServices(){
		if (customerUsername == null) {
			setUsername(pfc.enterUsername());
		} else{
			setUsername(customerUsername);
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
		
		if (!viewBookings(bookings)) { //if there are no bookings, return.
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
			System.out.println("Please select an option: ");
			
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
		
		in.nextLine();
		
		System.out.println("\nCurrent Services");
		System.out.printf("%-4s%-20s%-14s\n", "#", "Name", "Price (AUD)");
		for (int i = 0; i < services.size(); i++) {
			System.out.printf("%-4s", (i + 1) + ". ");
			System.out.println(services.get(i).getString());
		}
		
		switch (choice){
			case 1:
				addService(services_booked);
				break;
			case 2:
				deleteService(services_booked);
				break;
		}
		be.updateServicesBooked(services_booked);
	}
	
	/**
	 * Allows the user to add a service to a ticket
	 * @param services_booked List of services booked
	 */
	private void addService(List<ServiceBooking> services_booked){
		List<Service> services = sc.getServices(true);
		List<Integer> added_services = new ArrayList<>();
		String[] choices;
		boolean isOkay;
		int booking_id = services_booked.get(0).getBookingId();
		int ticket_id = services_booked.get(0).getServiceId();
		
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
	}
	
	/**
	 * Allows the user to delete a service from a ticket
	 */
	private void deleteService(List<ServiceBooking> services_booked){
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
			services_booked.remove(Integer.parseInt(choice) - 1);
			total_price += services.get(Integer.parseInt(choice) - 1).getCost();
		}
		
		System.out.printf("The services have been deleted from this ticket. $%.2f has been credited.\n", total_price);
	}
	
	/**
	 * Called to move passengers between flights by booking.
	 */
	public void movePassengers(){
		System.out.println("\nFlight to move from:");
		String current_flight_id = fc.enterFlightId(false);
		List<Booking> bookings = getBookingsForFlight(current_flight_id);
		Booking booking;
		int choice = 0;
		boolean isOkay;
		
		System.out.printf("%-4s%-15s%-15s%-15f\n", "#", "Booking ID", "Booking Status", "Total Price (AUD)");
		
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
	private List<ServiceBooking> getServicesBookedForBooking(int booking_id, List<Ticket> tickets){
		List<ServiceBooking> services_booked = new ArrayList<>();
		
		for (Ticket ticket: tickets) {
			List<ServiceBooking> sb = be.getServicesBooked(booking_id, ticket.getTicketId());
			services_booked.addAll(sb);
		}
		
		return services_booked;
	}
	
	/**
	 * Called to set the discount ratio of the frequent flier points.
	 */
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
	
	/**
	 * Allows the reservation system manager to set a cancellation fee.
	 */
	public void setCancellationFee(){
		double cancellation_fee = cancellationFee;
		boolean isOkay;
		
		do {
			isOkay = true;
			try {
				System.out.println("The current cancellation fee is " + cancellationFee);
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
	}
	
	/**
	 * Set the username for the customer when a CustomerRole instance is using
	 * this class.
	 * @param username 
	 */
	public void setUsername(String username){
		customerUsername = username;
	}
}
