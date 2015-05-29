package com.helpers;

import java.util.Formatter;

/**
 * Allows a information of a customer to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Customer extends Person{
	/**
	 * Customer username
	 */
	private String username;
	/**
	 * Customer password
	 */
	private char[] password;
	/**
	 * Customer role
	 */
	private String role;
	/**
	 * Customer frequent flyer points balance
	 */
	private int frequentFlierPoints;
	/**
	 * Customer no-fly status
	 */
	private String watchOrNoFly;
	
	/**
	 * Constructor for customer class
	 * @param title The title of the customer e.g. Mr, Mrs etc
	 * @param firstName The first name of the customer
	 * @param lastName The last name of the customer
	 * @param username The username of the customer
	 * @param password The password of the customer
	 * @param gender The gender of the customer
	 * @param DOB The date of birth of the customer
	 * @param phoneNumber The phone number of the customer
	 * @param email The email address of the customer
	 * @param street The street address of the customer
	 * @param state The state of the customer
	 * @param city The city of the customer
	 * @param country The country of the customer
	 * @param creditCardType The credit card type for the customer
	 * @param creditCardNumber The credit card number of the customer
	 * @param hasPassport The passport holder status of the customer
	 * @param role The privileges of the customer
	 */
	public Customer(String title, String firstName, String lastName, String username, char[] password,
			String gender, String DOB, String phoneNumber, String email,
			String street, String state, String city, String country,
			String creditCardType, String creditCardNumber, String hasPassport, String role){
		super(title, firstName, lastName, gender, DOB, phoneNumber, email, street, state, city, country, creditCardType, creditCardNumber, hasPassport);
		this.username = username;
		this.password = password;
		this.role = role;
	}
	/**
	 * Gets the username of the customer
	 * @return username The username of the customer
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Sets the username of the customer
	 * @param username The value of username to be set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Gets the password of the customer
	 * @return password The password of the customer
	 */
	public char[] getPassword() {
		return password;
	}
	/**
	 * Sets the password of the customer
	 * @param password The value of password to be set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}
	/**
	 * Gets the role of the customer
	 * @return role The role of the customer
	 */
	public String getRole() {
		return role;
	}
	/**
	 * Sets the privileges of the customer
	 * @param role The value of role to be set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * Gets the frequent flyer point balance of the customer
	 * @return frequentFlierPoints The frequent flyer point balance of the customer
	 */
	public int getFrequentFlierPoints() {
		return frequentFlierPoints;
	}
	/**
	 * Sets the frequent flyer point balance of the customer
	 * @param frequentFlierPoints The value of frequent flyer point balance to be set
	 */
	public void setFrequentFlierPoints(int frequentFlierPoints) {
		this.frequentFlierPoints = frequentFlierPoints;
	}
	/**
	 * Sets the no-fly status of the customer
	 * @param watchOrNoFly The value of no-fly status to be set
	 */
	public void setWatchOrNoFly(String wf){
		this.watchOrNoFly = wf;
	}
	/**
	 * Gets the no-fly status of the customer
	 * @return watchOrNoFly The no-fly status of the customer
	 */
	public String getWatchOrNoFly(){
		return this.watchOrNoFly;
	}
	/**
	 * Returns a formatted string consisting of username, first name and last name
	 * @return a formatted string consisting of username, first name and last name
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-24s%-15s%-15s", username, super.getFirstName(), super.getLastName());
		
		return sb.toString();
	}
}
