/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Flight;
import com.helpers.Person;
import com.helpers.Service;
import com.helpers.ServiceBooking;
import com.helpers.Ticket;
import com.userInteraction.UserEntity;
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
	private FlightController fc = new FlightController();
	private ServiceController sc = new ServiceController();
	private BookingEntity be = new BookingEntity();
	
	public BookingController(){
		
	}
	
	public void makeBooking(String role){
		AbstractMap.SimpleImmutableEntry<Flight, Boolean> flight_choice;
		String[] customer_usernames = null;
		List<Integer> person_ids = null;
		List<Ticket> tickets = new ArrayList<>();
		List<ServiceBooking> services_booked = new ArrayList<>();
		
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
		int[] available_seats = flight_choice.getKey().getAvailableSeats();
		int current_ticket_id = 1;
		
		//System.out.println("Monkey");
		
		/* Start Customer Tickets */
		if ("TA".equals(role) && customer_usernames != null){ //extra computation for customers
			for (String username: customer_usernames) {
				
				System.out.println("\nTicket for Customer with username \"" + username + "\"");
				
				chooseSeat(available_seats);
				
				in.nextLine(); //clear buffer
				
				tickets.add(new Ticket(current_ticket_id, username, -1));
				
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
		
		/* IF SOLO CUSTOMER SHOULD BOOK TICKET FOR THEMSELF */
		
		/* Start Person Tickets */
		if (person_ids != null) {
			for (Integer person_id: person_ids) {
				
				System.out.println("\nTicket for Person with ID \"" + person_id + "\"");
				
				chooseSeat(available_seats);
				
				in.nextLine(); //clear buffer
				
				tickets.add(new Ticket(current_ticket_id, null, person_id));
				
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
		/* End Fil Ticket Details */
		
		/* Start Payment */
		
		
		/* End Payment */
		
		be.saveBooking(flight_choice.getKey().getFlightID(), tickets, services_booked);
	}
	
	private void chooseSeat(int[] available_seats){
		boolean isOkay;
		int choice;
		
		System.out.println("1. First class (" + available_seats[0] + " seats available)");
		System.out.println("2. Business class (" + available_seats[1] + " seats available)");
		System.out.println("3. Premium economy class (" + available_seats[2] + " seats available)");
		System.out.println("4. Economy class (" + available_seats[3] + " seats available)");

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
						System.out.println("Seat saved.\n");
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
	}
	
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
		System.out.print("Flight ID  ");
		System.out.print("Departure Time                ");
		System.out.print("Arrival Time                     ");
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
	
	private String[] addCustomers(){
		UserEntity ue = new UserEntity();
		String[] customer_usernames;
		boolean areUsernamesOkay;
		
		do {
			System.out.print("Please enter the usernames of existing customers separated by spaces: ");
			customer_usernames = in.nextLine().split(" ");

			areUsernamesOkay = ue.checkUsernames(customer_usernames);

			if (!areUsernamesOkay) {
				System.out.println("A username that was entered is not valid!\nPlease try again!\n");
			}
		} while (!areUsernamesOkay);
		
		return customer_usernames;
	}
	
	private List<Integer> addPersons(){
		System.out.print("Do you want to add persons to the booking? (Y/N): ");
		char choice = in.nextLine().charAt(0);
		List<Integer> person_ids = null;
		
		if (choice == 'Y' || choice == 'y') {
			PersonEntity pe = new PersonEntity();
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
				
				person_ids.add(pe.addPerson(person));
				
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
	
	private List<Integer> bookServices(boolean is_international_flight){
		List<Service> services = sc.getServices(is_international_flight);
		List<Integer> booked_services = new ArrayList<>();
		String format_string = "%-5s%-25s%-10s\n";
		String[] choices;
		boolean isOkay;
		
		System.out.println("Services available:");
		System.out.printf(format_string, "#", "Service Name", "Price (AUD)");
		
		int i = 1;
		for (Service service : services) {
			System.out.printf(format_string, (i + ". "), service.getName(), ("$" + service.getCost()));
			i++;
		}
		
		System.out.print("Enter the numbers of the services separated by a space: ");
		
		do {			
			isOkay = true;
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
		
	}
	
}
