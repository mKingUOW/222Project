/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Airport;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class AirportController {
	private AirportEntity ae = new AirportEntity();
	private Scanner in = new Scanner(System.in);
	
	public AirportController(){
		
	}
	
	public void editAirportsOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Airport", "Edit Airport", "Delete Airport", "Cancel"};
		
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
		
		in.nextLine();
		
		switch (choice){
			case 1:
				addAirport();
				break;
			case 2:
				editAirport();
				break;
			case 3:
				deleteAirport();
				break;
		}
	}
	
	private void addAirport() {
		Airport airport = new Airport();
		
		airport.setAirportName(enterAirportName(false));
		airport.setCity(enterCity());
		airport.setCountry(enterCountry());
		airport.setIATA(enterIATA());
		airport.setLatitude(enterLatitude());
		airport.setLongitude(enterLongitude());
		airport.setAltitude(enterAltitude());
		airport.setTimezone(enterTimezone());
		airport.setDST(enterDST());
		airport.setTzDatabaseTimezone(enterTzDatabaseTimezone());
		
		ae.addAirport(airport);
		
		System.out.println("Airport " + airport.getAirportName() + " has been saved.\n");
	}

	private void editAirport() {
		String IATA;
		boolean isOkay;
		int option = 0;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the airport's IATA: ");
			IATA = in.nextLine();

			if (!doesIATAExist(IATA)) {
				isOkay = false;
				System.out.println("This IATA does not exist. Please try again!\n");
			} 
		} while (!isOkay);
		
		Airport airport = ae.getAirport(IATA);
		
		System.out.println("Airport ID: " + airport.getAirportId());
		System.out.println("IATA: " + airport.getIATA());
		System.out.println("1. Name: " + airport.getAirportName());
		System.out.println("2. City: " + airport.getCity());
		System.out.println("3. Country: " + airport.getCountry());
		System.out.println("4. Latitide: " + airport.getLatitude());
		System.out.println("5. Longitude: " + airport.getLongitude());
		System.out.println("6. Altitude: " + airport.getAltitude());
		System.out.println("7. Timezone: " + airport.getTimezone());
		System.out.println("8. Daylight savings time (DST): " + airport.getDST());
		System.out.println("9. TZ database timezone: " + airport.getTzDatabaseTimezone());
		
		do {
			isOkay = true;
			
			System.out.print("Please select an option to edit: ");
		
			try {
				option = in.nextInt();
				
				if (option < 1 || option > 9) {
					isOkay = false;
					System.out.println("That option doesn't exist. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("That option is not valid. Please try again!\n");
			}
		} while (!isOkay);
		
		do {
			switch (option){
				case 1:
					airport.setAirportName(enterAirportName(false));
					break;
				case 2:
					airport.setCity(enterCity());
					break;
				case 3:
					airport.setCountry(enterCountry());
					break;
				case 4:
					airport.setLatitude(enterLatitude());
					break;
				case 5:
					airport.setLongitude(enterLongitude());
					break;
				case 6:
					airport.setAltitude(enterAltitude());
					break;
				case 7:
					airport.setTimezone(enterTimezone());
					break;
				case 8:
					airport.setDST(enterDST());
					break;
				case 9:
					airport.setTzDatabaseTimezone(enterTzDatabaseTimezone());
					break;
			}
		} while (!isOkay);
		
		ae.editAirport(airport);
		
		System.out.println("Airport with IATA " + IATA + " has been deleted.\n");
	}

	private void deleteAirport() {
		String IATA;
		boolean isOkay;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the airport's IATA: ");
			IATA = in.nextLine();

			if (!doesIATAExist(IATA)) {
				isOkay = false;
				System.out.println("This IATA does not exist. Please try again!\n");
			} 
		} while (!isOkay);
		
		ae.deleteAirport(IATA);
		
		System.out.println("Airport with IATA " + IATA + " has been deleted.\n");
	}
	
	private String enterAirportName(boolean check){
		String airport_name;
		boolean isOkay;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the airport name: ");
			airport_name = in.nextLine();

			if (check) {
				isOkay = doesAirportExist(airport_name);
				
				if (!isOkay) {
					System.out.println(airport_name + " does not exist. Please try again!\n");
				}
			} else{ //for the add and edit airport
				if (doesAirportExist(airport_name)) {
					isOkay = false;
					System.out.println(airport_name + " already exists. Please try again!\n");
				}
			}
		} while (!isOkay);
		
		return airport_name;
	}
	
	private String enterCity(){
		System.out.print("Please enter the city name: ");
		return in.nextLine();
	}
	
	private String enterCountry(){
		System.out.print("Please enter the country name: ");
		return in.nextLine();
	}
	
	private String enterIATA(){
		String IATA;
		boolean isOkay;
		
		do {
			isOkay = true;
			
			System.out.print("Please enter the airport's IATA: ");
			IATA = in.nextLine();

			if (doesIATAExist(IATA)) {
				isOkay = false;
				System.out.println("IATA" + IATA + " already exists. Please try again!\n");
			} 
		} while (!isOkay);
		
		return IATA;
	}
	
	private double enterLatitude(){
		boolean isOkay;
		double latitude = 0;
		
		do {
			isOkay = true;
			System.out.print("Please enter the latitude of this airport: ");
			
			try {
				latitude = in.nextDouble();
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid latitude. Please try again!\n");
			}
			
		} while (!isOkay);
		
		return latitude;
	}
	
	private double enterLongitude(){
		boolean isOkay;
		double longitude = 0;
		
		do {
			isOkay = true;
			System.out.print("Please enter the longitude of this airport: ");
			
			try {
				longitude = in.nextDouble();
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid longitude. Please try again!\n");
			}
			
		} while (!isOkay);
		
		return longitude;
	}
	
	private int enterAltitude(){
		boolean isOkay;
		int altitude = 0;
		
		do {
			isOkay = true;
			System.out.print("Please enter the altitude of this airport: ");
			
			try {
				altitude = in.nextInt();
				
				if (altitude < 0) {
					isOkay = false;
					System.out.println("Altitude must be a positive integer. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid altitude. Please try again!\n");
			}
			
		} while (!isOkay);
		
		return altitude;
	}
	
	private int enterTimezone(){
		boolean isOkay;
		int timezone = 0;
		
		do {
			isOkay = true;
			System.out.print("Please enter the timezone of this airport: ");
			
			try {
				timezone = in.nextInt();
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid timezone. Please try again!\n");
			}
			
		} while (!isOkay);
		
		return timezone;
	}
	
	private char enterDST(){
		boolean isOkay;
		char DST = 0;
		
		do {
			isOkay = true;
			System.out.print("Please enter the daylight savings time (DST) of this airport: ");
			
			try {
				DST = in.nextLine().charAt(0);
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid daylight savings time (DST). Please try again!\n");
			}
			
		} while (!isOkay);
		
		return Character.toUpperCase(DST);
	}
	
	private String enterTzDatabaseTimezone(){
		System.out.print("Please enter the Tz database time zone of this airport: ");
		return in.nextLine();
	}
	
	public boolean doesIATAExist(String IATA){
		return !"".equals(getAirportCity(IATA));
	}
	
	public boolean doesAirportExist(String airport_name){
		return !"".equals(getAirportCountry(airport_name));
	}
	
	public String getAirportCode(String airport_name){
		return ae.getAirportCode(airport_name); //if return null that means airport name doesn't exist
	}
	
	public String getAirportCountry(String airport_name){
		return ae.getAirportCountry(airport_name); //if return null that means airport name doesn't exist
	}
	
	public String getAirportCity(String IATA){
		return ae.getAirportCity(IATA);
	}
}
