/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Plane;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class FleetController {
	
	private FleetEntity fe = new FleetEntity();
	private Scanner in = new Scanner(System.in);
	
	public FleetController(){
		
	}
	
	public void editFleetOption(){
		boolean isOkay;
		int choice = 0;
		String[] choices = {"Add Plane", "Edit Plane", "Delete Plane", "Cancel"};
		
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
		
		switch (choice){
			case 1:
				addPlane();
				break;
			case 2:
				editPlane();
				break;
			case 3:
				deletePlane();
				break;
		}
	}
	
	private void addPlane() {
		Plane plane = new Plane();
		
		System.out.print("Please key in the model of the plane you want to add: ");
		plane.setPlaneModel(in.nextLine());
		
		if (!doesModelExist(plane.getPlaneModel())) {
			System.out.println("A new plane model has been entered.");
			System.out.println("Please enter the following details for this new model.\n");
			
			plane.setFirstClassSeats(enterSeats(0));
			plane.setBusinessClassSeats(enterSeats(1));
			plane.setPremiumEconomyClassSeats(enterSeats(2));
			plane.setEconomyClassSeats(enterSeats(3));
			
			System.out.println("A new plane of model " + plane.getPlaneModel() + " has been added to the fleet.\n");
			
		} else{ //plane model already exists
			System.out.println("Another " + plane.getPlaneModel() + " has been added to the fleet.\n");
		}
		
		fe.addPlane(plane);
	}
	
	private void editPlane() {
		boolean isOkay;
		String plane_model;
		Plane plane;
		int option = 0;
		
		do {
			isOkay = true;
			
			System.out.print("Please key in the model of the plane you want to edit: ");
			plane_model = in.nextLine();

			if (!doesModelExist(plane_model)) {
				isOkay = false;
				System.out.println("The plane with this model does not exist in the fleet. Please try again!\n");
			}
		} while (!isOkay);
		
		plane = fe.getPlane(plane_model);
		System.out.println("Plane Model: " + plane_model);
		System.out.println("1. First class seats: " + plane.getFirstClassSeats());
		System.out.println("2. Business class seats: " + plane.getBusinessClassSeats());
		System.out.println("3. Premium economy class seats: " + plane.getPremiumEconomyClassSeats());
		System.out.println("4. Economy class seats: " + plane.getEconomyClassSeats());
		
		do {
			isOkay = true;
			
			System.out.print("Please select an option to edit: ");
		
			try {
				option = in.nextInt(0);
				
				if (option < 1 || option > 4) {
					isOkay = false;
					System.out.println("That option doesn't exist. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("That option is not valid. Please try again!\n");
			}
		} while (!isOkay);
		
		switch (option){
			case 1:
				plane.setFirstClassSeats(enterSeats(option - 1));
				break;
			case 2:
				plane.setBusinessClassSeats(enterSeats(option - 1));
				break;
			case 3:
				plane.setPremiumEconomyClassSeats(enterSeats(option - 1));
				break;
			case 4:
				plane.setEconomyClassSeats(enterSeats(option - 1));
				break;
		}
		
		fe.editPlane(plane);
		
		System.out.println("The plane model \"" + plane_model + "\" has been edited.\n");
	}

	private void deletePlane() {
		boolean isOkay;
		String plane_model = "";
		
		do {			
			isOkay = true;
			
			System.out.print("Please key in the model of a plane you want to delete: ");
			plane_model = in.nextLine();

			if (doesModelExist(plane_model)) {
				fe.deletePlane(plane_model);
			} else{
				isOkay = false;
				System.out.println("The plane with this model does not exist in the fleet. Please try again!\n");
			}
		} while (!isOkay);
		
		System.out.println("A plane of model " + plane_model + " has been deleted from the database.");
	}
	
	private int enterSeats(int seat_class){
		boolean isOkay;
		int numberOfSeats = 0;
		String[] seatClass = {"first", "business", "premium economy", "economy"};
		
		do {
			isOkay = true;
			
			try {
				System.out.print("Please key in the number of " + seatClass[seat_class] + " seats : ");
				numberOfSeats = in.nextInt();
				
				if (numberOfSeats < 0) {
					isOkay = false;
					System.out.println("Please enter a positive number. Please try again!\n");
				}
				
			} catch (InputMismatchException e) {
				isOkay = false;
				System.out.println("Please enter a valid number. Please try again!\n");
			}
		} while (!isOkay);
		
		return numberOfSeats;
	}
	
	public Plane getPlane(String model){
		return fe.getPlane(model);
	}
	
	public boolean doesModelExist(String model){
		return fe.getPlane(model) != null;
	}
	
	public int[] getSeatsForPlane(int planeId){
		return fe.getSeatsForPlane(planeId);
	}

}
