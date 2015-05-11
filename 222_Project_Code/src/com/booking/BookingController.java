/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Booking;
import com.helpers.Flight;
import com.helpers.Person;
import com.userInteraction.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class BookingController {
	private Scanner in = new Scanner(System.in);
	private FlightController fc = new FlightController();
	
	public BookingController(){
		
	}
	
	public void makeBooking(String role){
		String origin;
		String destination;
		boolean isOkay;
		List<Flight> flights = null;
		Flight flight_choice;
		int numberOfPeople = 0;
		Booking booking = new Booking();
		
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
		System.out.print("Arrival Time                     ");
		System.out.print("Departure Time                ");
		System.out.println();
		
		int i = 1;
		for (Flight flight: flights) {
			System.out.print(i + ". ");
			System.out.println(flight.toString());
			i++;
		}
		/* End Display Flights Available */
		
		/* Start Select Flight */
		System.out.print("Please select your flight: "); //should do checking for range, but ignore for now
		flight_choice = flights.get(in.nextInt());
		/* End Select Flight */
		
		/* Start Add Customers and Persons */
		switch (role){
			case "TA":
				numberOfPeople = 0;
				numberOfPeople += addCustomers(booking);
				numberOfPeople += addPersons(booking);
				break;
			case "CUS":
				numberOfPeople = 1;
				numberOfPeople += addPersons(booking);
				break;
			default:
				break;
		}
		/* End Add Customers and Persons */
		
		/* Start Display Available Seats */
		int[] available_seats = flight_choice.getAvailableSeats();
		
		System.out.println("1. First class seats available: " + available_seats[0] + " seats");
		System.out.println("2. Business class seats available: " + available_seats[1] + " seats");
		System.out.println("3. Premium economy class seats available: " + available_seats[2] + " seats");
		System.out.println("4. Economy class seats available: " + available_seats[3] + " seats");
		/* End Display Available Seats */
		
		for (int j = 0; j < numberOfPeople; j++) {
			System.out.println("\nCustomers left to book for: " + (numberOfPeople - j));
			System.out.
		}
	}
	
	public int addCustomers(Booking booking){
		UserEntity ue = new UserEntity();
		String[] customer_usernames;
		boolean areUsernamesOkay;
		int numberOfCustomers = 0;
		
		do {
			in.nextLine();
			System.out.print("Please enter the usernames of existing customers separated by spaces: ");
			customer_usernames = in.nextLine().split(" ");
			
			numberOfCustomers = customer_usernames.length;

			areUsernamesOkay = ue.checkUsernames(customer_usernames);

			if (!areUsernamesOkay) {
				System.out.println("A username that was entered is not valid!\nPlease try again!\n");
			}
		} while (!areUsernamesOkay);
		
		booking.addCustomers(customer_usernames);
		
		System.out.println("Users have been added to the booking!\n");
		
		return numberOfCustomers;
	}
	
	public int addPersons(Booking booking){
		System.out.print("Do you want to add persons to the booking? (Y/N): ");
		char choice = in.next().charAt(0);
		int numberOfPersons = 0;
		
		if (choice == 'Y' || choice == 'y') {
			PersonEntity pe = new PersonEntity();
			ArrayList<Integer> person_ids = new ArrayList<>();
			
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
				
				numberOfPersons++;
			} while(!isDone);
			
			booking.addPersons(person_ids);
			
		} //end if
		
		return numberOfPersons;
	}
	
	public void cancelBooking(){
		
	}
	
}
