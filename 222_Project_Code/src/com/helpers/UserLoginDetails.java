package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class UserLoginDetails {

	private String title;
	private String firstName;
	private String lastName;
	private String username;
	private char[] password;
	private String gender;
	private String DOB;
	private String phoneNumber;
	private String email;
	private String street;
	private String state;
	private String city;
	private String country;
	private String creditCardType;
	private String creditCardNumber;
	private String hasPassport;
	private String role;
	
	public UserLoginDetails(String title, String firstName, String lastName, String username, char[] password,
			String gender, String DOB, String phoneNumber, String email,
			String street, String state, String city, String country,
			String creditCardType, String creditCardNumber, String hasPassport, String role){
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.DOB = DOB;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.street = street;
		this.state = state;
		this.city = city;
		this.country = country;
		this.creditCardType = creditCardType;
		this.creditCardNumber = creditCardNumber;
		this.hasPassport = hasPassport;
		this.role = role;
	}
	/*
	public void setTitle(String title){
		this.title = title;
	}
	
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String firstName){
		this.firstName = firstName;
	}
	*/
	
	public String getTitle(){
		return title;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return firstName;
	}
	
	public String getUsername(){
		return username;
	}
	
	public char[] getPassword(){
		return password;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getDOB(){
		return DOB;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getStreet(){
		return street;
	}
	
	public String getState(){
		return state;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getCountry(){
		return country;
	}
	
	public String getCreditCardType(){
		return creditCardType;
	}
	
	public String getCreditCardNumber(){
		return creditCardNumber;
	}
	
	public String hasPassport(){
		return hasPassport;
	}
	
	public String getRole(){
		return role;
	}
}
