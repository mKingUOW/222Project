package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class UserLoginDetails extends Person{
	private String username;
	private char[] password;
	private String role;
	
	public UserLoginDetails(String title, String firstName, String lastName, String username, char[] password,
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
	
}
