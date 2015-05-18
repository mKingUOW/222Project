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
			String creditCardType, String creditCardNumber,
			String hasPassport){
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String DOB) {
		this.DOB = DOB;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String hasPassport() {
		return hasPassport;
	}

	public void setHasPassport(String hasPassport) {
		this.hasPassport = hasPassport;
	}
	
	public String[] toArray(){
		String[] values = {title, firstName, lastName, gender, DOB,
			phoneNumber, email, street, state, city, country,
			creditCardType + ": " + creditCardNumber, hasPassport};
		return values;
	}
}
