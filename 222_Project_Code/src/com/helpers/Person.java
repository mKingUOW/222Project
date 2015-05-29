package com.helpers;

import java.util.Formatter;

/**
 * Allows a information of a person to be passed from class to class in a
 * centralized manner.
 * @author Michael Y.M. Kong
 */
public class Person {
	/**
	 * Person ID number
	 */
	private int personId;
	/**
	 * Title of the person
	 */
	private String title;
	/**
	 * first name of the person
	 */
	private String firstName;
	/**
	 * last name of the person
	 */
	private String lastName;
	/**
	 * gender of the person
	 */
	private String gender;
	/**
	 * Date of birth of the person
	 */
	private String DOB;
	/**
	 * phone number of the person
	 */
	private String phoneNumber;
	/**
	 * Email address of the person
	 */
	private String email;
	/**
	 * Street address of the person
	 */
	private String street;
	/**
	 * State of the person
	 */
	private String state;
	/**
	 * City of the person
	 */
	private String city;
	/**
	 * Country of the person
	 */
	private String country;
	/**
	 * Credit card type of the person
	 */
	private String creditCardType;
	/**
	 * Credit card number of the person
	 */
	private String creditCardNumber;
	/**
	 * Passport holder status of the person
	 */
	private String hasPassport;
	
	/**
	 * Constructor for person class
	 * @param title The title of the person e.g. Mr, Mrs etc
	 * @param firstName The first name of the person
	 * @param lastName The last name of the person
	 * @param gender The gender of the person
	 * @param DOB The date of birth of the person
	 * @param phoneNumber The phone number of the person
	 * @param email The email address of the person
	 * @param street The street address of the person
	 * @param state The state of the person
	 * @param city The city of the person
	 * @param country The country of the person
	 * @param creditCardType The credit card type for the person
	 * @param creditCardNumber The credit card number of the person
	 * @param hasPassport The passport holder status of the person
	 */
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

	/**
	 * Gets the person ID for the person
	 * @return The person ID
	 */
	public int getPersonId() { //NOTE:-This method is ignored in the subclass
		return personId;
	}
	/**
	 * Sets the person ID for the person
	 * @parampersonId The person ID
	 */
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	/**
	 * Gets the title for the person
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Sets the title for the person
	 * @param title The value of title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Gets the first name for the person
	 * @return The first name of the person
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets the first name for the person
	 * @param firstName The value of first name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Gets the last name for the person
	 * @return The last name of the person
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets the last name for the person
	 * @param lastName The value of last name to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Gets the gender for the person
	 * @return The gender of the person
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * Sets the gender for the person
	 * @param gender The value of gender to be set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * Gets the date of birth for the person
	 * @return The date of birth of the person
	 */
	public String getDOB() {
		return DOB;
	}
	/**
	 * Sets the date of birth for the person
	 * @param DOB The value of date of birth to be set
	 */
	public void setDOB(String DOB) {
		this.DOB = DOB;
	}
	/**
	 * Gets the phone number for the person
	 * @return The phone number of the person
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Sets the phone number for the person
	 * @param phoneNumber The value of phone number to be set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * Gets the email address for the person
	 * @return The email address of the person
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email address for the person
	 * @param The value of email address to be set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Gets the street address for the person
	 * @return The street address of the person
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * Sets the street address for the person
	 * @param street The value of street address to be set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * Gets the state for the person
	 * @return The state of the person
	 */
	public String getState() {
		return state;
	}
	/**
	 * Sets the state for the person
	 * @param state The value of state to be set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * Gets the city for the person
	 * @return The city of the person
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Sets the city for the person
	 * @param city The value of city to be set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Gets the country for the person
	 * @return The country of the person
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Sets the country for the person
	 * @param country The value of country to be set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Gets the credit card type for the person
	 * @return The credit card type of the person
	 */
	public String getCreditCardType() {
		return creditCardType;
	}
	/**
	 * Sets the credit card type for the person
	 * @param The value of credit card type to be set
	 */
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	/**
	 * Gets the credit card number for the person
	 * @return The credit card number of the person
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	/**
	 * Sets the credit card number for the person
	 * @param creditCardNumber The value of credit card number to be set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	/**
	 * Gets the passport holder status for the person
	 * @return The passport holder status of the person
	 */
	public String hasPassport() {
		return hasPassport;
	}
	/**
	 * Sets the passport holder status for the person
	 * @param hasPassport The value of passport holder status to be set
	 */
	public void setHasPassport(String hasPassport) {
		this.hasPassport = hasPassport;
	}
	/**
	 * Gets the an array of personal details for the person
	 * @return An array of personal details for the person
	 */
	public String[] toArray(){
		String[] values = {title, firstName, lastName, gender, DOB,
			phoneNumber, email, street, state, city, country,
			creditCardType + ": " + creditCardNumber, hasPassport};
		return values;
	}
	/**
	 * Returns a formatted string consisting of personID, first name and last name
	 * @return a formatted string consisting of personID, first name and last name
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		fmt.format("%-24s%-15s%-15s", personId, firstName, lastName);
		
		return sb.toString();
	}
}
