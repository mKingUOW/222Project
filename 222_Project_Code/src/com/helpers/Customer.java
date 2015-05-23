package com.helpers;

import java.util.Formatter;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Customer extends Person{
	private String username;
	private char[] password;
	private String role;
	private int frequentFlierPoints;
	private String watchOrNoFly;
	
	public Customer(String title, String firstName, String lastName, String username, char[] password,
			String gender, String DOB, String phoneNumber, String email,
			String street, String state, String city, String country,
			String creditCardType, String creditCardNumber, String hasPassport, String role){
		super(title, firstName, lastName, gender, DOB, phoneNumber, email, street, state, city, country, creditCardType, creditCardNumber, hasPassport);
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getFrequentFlierPoints() {
		return frequentFlierPoints;
	}

	public void setFrequentFlierPoints(int frequentFlierPoints) {
		this.frequentFlierPoints = frequentFlierPoints;
	}
	
	public void setWatchOrNoFly(String wf){
		this.watchOrNoFly = wf;
	}
	
	public String getWatchOrNoFly(){
		return this.watchOrNoFly;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-24s%-15s%-15s", username, super.getFirstName(), super.getLastName());
		
		return sb.toString();
	}
}
