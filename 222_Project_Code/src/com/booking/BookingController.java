/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Booking;
import com.helpers.Flight;
import com.helpers.Person;
import com.userInteraction.UserEntity;
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
		int choice;
		Booking booking;
		
		do {	
			isOkay = true;
			System.out.print("Please enter the flight origin of choice (city name): ");
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
		
		int i = 1;
		
		for (Flight flight: flights) {
			System.out.print(i + ". ");
			System.out.println(flight.toString());
			i++;
		}
		
		System.out.print("Please select your flight: ");
		choice = in.nextInt();
		
		//should do checking for range, but ignore for now
		
		booking = new Booking();
		
		switch (role){
			case "TA":
				addCustomers(booking);
				//no break because travel agencies should be able to add persons after customers
			case "CUS":
				addPersons(booking);
				break;
			default:
				break;
		}
	}
	
	public void addCustomers(Booking booking){
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
		
		booking.addCustomers(customer_usernames);
		
		System.out.println("Users have been added to the booking!\n");
	}
	
	public void addPersons(Booking booking){
		System.out.print("Do you want to add persons to the booking? (Y/N): ");
		char choice = in.next().charAt(0);
		
		if (choice == 'Y') {
			PersonEntity pe = new PersonEntity();
			
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
				
				pe.addPerson(person);
				
				System.out.print("Do you want to add another person? (Y/N): ");
				choice = in.next().charAt(0);
				
				if (choice == 'Y') {
					isDone = false;
				} else{
					isDone = true;
				}
				
			} while(!isDone);
		} //end if
	}
	
	public void cancelBooking(){
		
	}
}
