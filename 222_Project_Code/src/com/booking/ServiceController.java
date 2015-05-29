/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Service;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides methods pertaining to any required 
 * functionality and processing that is related to services.
 * @author Michael Y.M. Kong
 */
public class ServiceController {
	
	/**
	 * ServiceController requires the ServiceEntity class to write/read data
	 * to the database.
	 */
	private ServiceEntity se = new ServiceEntity();
	
	
	/**
	 * Scanner allows class to use the basic input from the console.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Default constructor
	 */
	public ServiceController(){
	}
	
	/**
	 * Gets the services offered by the airline based on whether the flight is
	 * an international flight or not.
	 * @param isInternational A boolean value showing whether the flight calling this
	 * function is an international flight or not.
	 * @return A list of all the services available for the given international status.
	 */
	public List<Service> getServices(boolean isInternational){
		return se.getServices(isInternational);
	}
	
	/**
	 * Gets the Service object for the given service ID.
	 * @param serviceId The service ID for the service to retrieve
	 * @return A Service object which corresponds to the given service ID
	 */
	public Service getService(int serviceId){
		return se.getService(serviceId);
	}
	
	/**
	 * Provides the interface to add new Service-s to the database.
	 */
	public void addServices(){
		List<Service> new_services = new ArrayList<>();
		char choice;
		
		System.out.println("\nADD SERVICES");
		
		do {
			Service service = new Service();
			
			System.out.println();
			System.out.print("Please enter the name for this service: ");
			service.setName(in.nextLine().trim());

			service.setCost(enterPrice());
			in.nextLine();
			
			service.setAvailability(enterAvailability());

			new_services.add(service);
			
			System.out.print("Do you want to add another service? (Y/N): ");
			choice = in.nextLine().charAt(0);
		} while (choice == 'Y' || choice == 'y');
		
		System.out.println(new_services.size() + " services have been added.");
		se.addServices(new_services);
	}
	
	/**
	 * Provides the interface to remove Services-s from the database.
	 */
	public void removeServices(){
		List<Service> services = se.getServices(true);
		boolean isOkay;
		String[] choices;
		int[] service_ids_to_remove;
		
		System.out.println("\nREMOVE SERVICES");
		
		displayServices(services);
		
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
		
		service_ids_to_remove = new int[choices.length];
		
		for (int i = 0; i < choices.length; i++) {
			service_ids_to_remove[i] = services.get(i).getServiceID();
		}
		
		se.removeService(service_ids_to_remove);
	}
	
	/**
	 * Provides the interface to edit the price of a service.
	 */
	public void editServicePrice(){
		List<Service> services = se.getServices(true);
		boolean isOkay;
		int choice;
		
		System.out.println("\nEDIT SERVICE PRICE");
		
		displayServices(services);
		
		do {			
			isOkay = true;
			System.out.print("Enter the number of the service to edit the price of: ");
			choice = in.nextInt();

			if (choice < 1 || choice > services.size()) {
				isOkay = false;
				System.out.println("The chosen option does not exist. Please try again!\n");
			} 
		} while (!isOkay);
		
		Service service = services.get(choice - 1);
		service.setCost(enterPrice());
		
		se.editService(service);
		System.out.println("The price for service \"" + service.getName() + "\" has been changed.\n");
	}
	
	/**
	 * Provides the interface to edit the availability of a service.
	 */
	public void editServiceAvailability(){
		List<Service> services = se.getServices(true);
		boolean isOkay;
		int choice;
		
		System.out.println("\nEDIT SERVICE AVAILABILITY");
		
		displayServices(services);
		
		do {			
			isOkay = true;
			System.out.print("Enter the number of the service to edit the availability of: ");
			choice = in.nextInt();

			if (choice < 1 || choice > services.size()) {
				isOkay = false;
				System.out.println("The chosen option does not exist. Please try again!\n");
			} 
		} while (!isOkay);
		
		Service service = services.get(choice - 1);
		service.setAvailability(enterAvailability());
		
		se.editService(service);
		System.out.println("The price for service \"" + service.getName() + "\" has been changed.\n");
	}
	
	/**
	 * An interface component to enter the price. Includes validation.
	 * @return The price that was entered.
	 */
	private double enterPrice(){
		double cost = 0;
		boolean isOkay;
		
		do {
			isOkay = true;

			System.out.print("Please enter the cost for this service: $");

			try {
				cost = in.nextDouble();
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Invalid price entered. Please try again!\n");
			}
		} while (!isOkay);
		
		return cost;
	}
	
	/**
	 * An interface component to enter the availability. Includes validation.
	 * @return The availability that was entered.
	 */
	private String enterAvailability(){
		String availability = "";
		boolean isOkay;
		
		do {
			isOkay = true;

			System.out.print("Please enter the availability of this service (All/International): ");

			availability = in.nextLine();

			if (!"All".equals(availability) && !"International".equals(availability)) {
				isOkay = false;
				System.out.println("Please enter only \"All\" or \"International\". Please try again!\n");
			}
		} while (!isOkay);
		
		return availability;
	}
	
	/**
	 * An interface component to display services.
	 * @param services The List<Service> object that is displayed.
	 */
	private void displayServices(List<Service> services){
		System.out.printf("%-4s%-20s%-14s%-15s\n", "#", "Name", "Cost (AUD)", "Availability");
		for (int i = 0; i < services.size(); i++) {
			System.out.printf("%-4s", (i + 1) + ". ");
			System.out.println(services.get(i).toString());
		}
	}
}
