package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Person {

	private String title;
	private String firstName;
	private String lastName;
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
	
	public Person(String title, String firstName, String lastName,
			String gender, String DOB, String phoneNumber, String email,
			String street, String state, String city, String country,
			String creditCardType, String creditCardNumber, String hasPassport){
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
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
		return lastName;
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
}
