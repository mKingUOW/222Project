/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
import com.helpers.Staff;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ProfileEntity {
	/**
	 * A quick reference to the userAccount database file.
	 */
	private String accountFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userAccount.csv";
	
	/**
	 * A quick reference to the userDetail database file.
	 */
	private String detailsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userDetail.csv";

	/**
	 * A BufferedReader object that allows the class to read from files.
	 */
	private BufferedReader reader;
	
	/**
	 * A PrintWriter object that allows the class to write to files.
	 */
	private PrintWriter writer;
	
	/**
	 * Default constructor.
	 */
	public ProfileEntity(){
		
	}
	
	/**
	 * Allows the user to login.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return "loginFail" if the login failed. Else returns the role of this user.
	 */
	public String login(String username, char[] password){
		String passwd = new String(password);
		String oneLine = "";
		String role = "";
		String passwdInDB;
		boolean existed = false;
		
		try{
			reader = new BufferedReader(new FileReader(accountFile));
			while(!existed && ((oneLine = reader.readLine()) != null)){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					existed = true;
					passwdInDB = words[1];
					
					if(passwd.equals(passwdInDB)){
						role = words[2];	//If username and password are correct,return role;	
					}else{
						role = "loginFail";	//If username exists, but password is wrong,login fails;
					}	
				}
				
            }
			
			if(!existed){
				role = "loginFail";		//If the username doesn't exist, login fails;
			}
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return role;
	}
	
	/**
	 * Saves a new Customer object to the database.
	 * @param user The Customer object that is saved to the database.
	 * @return True if the signup is successful. False if the username already
	 * exists in the system.
	 */
	public boolean signUp(Customer user){
		File accfile;
		File usrfile;
		String passwd = new String(user.getPassword());
		String oneLine = "";
		boolean existed = false;
		
		//At first, check whether the account exists already in the DB;
		try{
			reader = new BufferedReader(new FileReader(accountFile));
			while(!existed && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(user.getUsername().equals(words[0])){
					existed = true;
					return false;	
				}
				
            }
			
            reader.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//username + title + firstName + lastName + gender + DOB + phone# + email + address(street, state, city, country) +
		//creditCardType + creditCardNumber + FFP + hasPassport + flyStatus + accountCharged
		
		//If the account doesn't exist in the DB, then insert the username/password/role into the DB;
		try{
			accfile = new File(accountFile);
			usrfile = new File(detailsFile);
			writer = new PrintWriter(new FileOutputStream(accfile,true));		//To append to the file using "true";
			writer.println(user.getUsername() + "," + passwd + "," + user.getRole());
            writer.close();	

			writer = new PrintWriter(new FileOutputStream(usrfile,true));		//To append to the file using "true";
			writer.println(user.getUsername() + "," + user.getTitle() + "," + user.getFirstName() + "," 
							  + user.getLastName() + "," + user.getGender() + "," + user.getDOB() + "," 
							  + user.getPhoneNumber() + "," + user.getEmail() + "," + user.getStreet() + "," 
							  + user.getState() + "," + user.getCity() + "," + user.getCountry() + ","
							  + user.getCreditCardType() + "," + user.getCreditCardNumber() + "," + user.getFrequentFlierPoints()
							  + "," + user.hasPassport() + "," + user.getWatchOrNoFly() + ",0");
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Checks an array of usernames against the existing usernames in the system.
	 * @param usernames A String array of usernames to check.
	 * @return True if all usernames in the given String array exist in the system.
	 * False otherwise.
	 */
	public boolean checkUsernames(String usernames[]){
		int len = usernames.length;
		boolean allExisted = true;
		String oneLine="";
		
		try{
			for(int i=0;i<len && allExisted;i++){
				String username = usernames[i];
				reader = new BufferedReader(new FileReader(accountFile));
				boolean existed = false;
				while(!existed && ((oneLine = reader.readLine()) != null)){
					String[] words = oneLine.split(",");
					
					if(username.equals(words[0])){
						existed = true;		
					}
				}
				
				if(!existed){
					allExisted = false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return allExisted;
	}
	
	/**
	 * Gets the password for a particular user.
	 * @param username The username to get the password of.
	 * @return A char array representation of the password.
	 */
	public char[] getPassword(String username){
		String passwdStr = "";
		char [] passwd = null;
		
		String oneLine = "";
		boolean existed = false;
		
		try{
			reader = new BufferedReader(new FileReader(accountFile));
			while(!existed && ((oneLine = reader.readLine()) != null)){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					existed = true;
					passwdStr = words[1];
				}
				
            }
			
			passwd = passwdStr.toCharArray();
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return passwd;
	}
	
	/**
	 * Saves a password for a particular user.
	 * @param username The username to save the password of.
	 * @param password The password to save for the given username.
	 */
	public void savePassword(String username, char[] password){
		File accfile;
		String passwd = new String(password);
		String oneLine;
		String data = "";
		String updatedLine = "";
		
		//Read in the records, save them in one String, modify the corresponding information;
		//Then write the String(which contains all the updated information of database) back into the Database;
		try{
			reader = new BufferedReader(new FileReader(accountFile));
			while((oneLine = reader.readLine()) != null){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					updatedLine +=  words[0];
					updatedLine += ",";
					updatedLine +=  passwd;
					updatedLine += ",";
					updatedLine +=  words[2];
					data += updatedLine + "\n";
				}else{
					data += oneLine + "\n";
				}	
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			accfile = new File(accountFile);
			writer = new PrintWriter(new FileOutputStream(accfile));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the frequent flier points for a particular user.
	 * @param username The username to get the frequent flier points of.
	 * @return The number of frequent flier points for the given user.
	 */
	public int getFrequentFlierPoints(String username){
		String oneLine = "";
		boolean isFound = false;
		int ffp = 0;
		
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while(!isFound && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					isFound = true;
					ffp = Integer.parseInt(words[14]);	
				}
				
            }
			
            reader.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ffp;
	}
	
	/**
	 * Sets the frequent flier points for a given user.
	 * @param username The username of the user to set the frequent flier
	 * points of.
	 * @param points The points to set the frequent flier points to.
	 */
	public void setFrequentFlierPoints(String username, int points){
		File usrfile;
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		String ffp = new Integer(points).toString();
		
		//Read in the records, save them in one String, modify the corresponding information;
		//Then write the String(which contains all the updated information of database) back into the Database;
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while((oneLine = reader.readLine()) != null){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					for(int i=0;i<14;i++){
						updatedLine += words[i];
						updatedLine += ",";
					}
					updatedLine +=  ffp;
					updatedLine += ",";
					updatedLine +=  words[15];
					updatedLine += ",";
					updatedLine +=  words[16];
					data += updatedLine + "\n";
				}else{
					data += oneLine + "\n";
				}	
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			usrfile = new File(detailsFile);
			writer = new PrintWriter(new FileOutputStream(usrfile));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the account/profile of the given username.
	 * @param username The username of the profile to close.
	 */
	public void closeAccount(String username){
		File accfile;
		File usrfile;
		String oneLine;
		String accData = "";
		String usrData = "";
		
		//delete the login credentials in userAccount.csv
		try{
			reader = new BufferedReader(new FileReader(accountFile));
			while((oneLine = reader.readLine()) != null){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(!username.equals(words[0])){
					accData += oneLine + "\n";
				}	
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			accfile = new File(accountFile);
			writer = new PrintWriter(new FileOutputStream(accfile));	
			writer.print(accData);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//delete the user details in userDetail.csv
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while((oneLine = reader.readLine()) != null){
			// oneLine represents one line in the DB; Recursively check the DB;	
                String[] words = oneLine.split(",");
				
				if(!username.equals(words[0])){
					usrData += oneLine + "\n";
				}	
            }
		     reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			usrfile = new File(detailsFile);
			writer = new PrintWriter(new FileOutputStream(usrfile));	
			writer.print(usrData);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the account details for a particular username.
	 * @param username
	 * @return A Person object because we only need to modify the
	 * basic details of this user
	 */
	public Person getAccountDetails(String username){
		String oneLine = "";
		
		String title;
		String firstName;
		String lastName;
		String gender;
		String DOB;
		String phoneNumber;
		String email;
		String street;
		String state;
		String city;
		String country;
		String creditCardType;
		String creditCardNumber;
		String hasPassport;
//		int frequentFlierPoints = 0;	
//		String watchOrNoFly;

		boolean found = false;
		Person person = null;			//Used for the return;
		
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while(!found && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					title = words[1];
					firstName = words[2];
					lastName = words[3];
					gender = words[4];
					DOB = words[5];
					phoneNumber = words[6];
					email = words[7];
					street = words[8];
					state = words[9];
					city = words[10];
					country = words[11];
					creditCardType = words[12];
					creditCardNumber = words[13];
//					frequentFlierPoints = Integer.parseInt(words[14]);
					hasPassport = words[15];
//					watchOrNoFly = words[16];
					
					found = true;
					person = new Person(title,firstName,lastName,gender,DOB,phoneNumber,email,street,
										state,city,country,creditCardType,creditCardNumber,hasPassport);
				}
				
            }
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return person;
	}
	
	/**
	 * Sets the account details for a particular Person.
	 * @param account The Person object to save.
	 */
	public void setAccountDetails(Person account){
		File usrfile;
		
		String title = account.getTitle();
		String firstName = account.getFirstName();
		String lastName = account.getLastName();
		String gender = account.getGender();
		String DOB = account.getDOB();
		String phoneNumber = account.getPhoneNumber();
		String email = account.getEmail();
		String street = account.getStreet();
		String state = account.getState();
		String city = account.getCity();
		String country = account.getCountry();
		String creditCardType = account.getCreditCardType();
		String creditCardNumber = account.getCreditCardNumber();
		String hasPassport = account.hasPassport();
		
		boolean found = false;
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while(!found && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				//If the first name , last name and DOB are all matched, then it is the person to be modified;
				if(firstName.equals(words[2]) && lastName.equals(words[3]) && DOB.equals(words[5]) ){
					updatedLine +=  words[0];
					updatedLine += ",";
					updatedLine +=  title;
					updatedLine += ",";
					updatedLine +=  firstName;
					updatedLine += ",";
					updatedLine +=  lastName;
					updatedLine += ",";
					updatedLine +=  gender;
					updatedLine += ",";
					updatedLine +=  DOB;
					updatedLine += ",";
					updatedLine +=  phoneNumber;
					updatedLine += ",";
					updatedLine +=  email;
					updatedLine += ",";
					updatedLine +=  street;
					updatedLine += ",";
					updatedLine +=  state;
					updatedLine += ",";
					updatedLine +=  city;
					updatedLine += ",";
					updatedLine +=  country;
					updatedLine += ",";
					updatedLine +=  creditCardType;
					updatedLine += ",";
					updatedLine +=  creditCardNumber;
					updatedLine += ",";
					updatedLine +=  words[14];
					updatedLine += ",";
					updatedLine +=  hasPassport;
					updatedLine += ",";
					updatedLine += words[16];
	
					data += updatedLine + "\n";
				}else{
					data += oneLine + "\n";
				}	
            }
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			usrfile = new File(detailsFile);
			writer = new PrintWriter(new FileOutputStream(usrfile));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the fly status of a particular user.
	 * @param username The username of the user to get the fly status of.
	 * @return Empty string, "watch" or "no fly".
	 */
	public String canUserFly(String username){		
		boolean found = false;
		String watchOrNoFly = "";
		String oneLine;
		
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while(!found && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
					watchOrNoFly = words[16];					
					found = true;
				}
				
            }
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return watchOrNoFly;
	}
	
	/**
	 * Allows the user to edit the watch and no fly list for a particular user.
	 * @param username The user to edit the fly status of.
	 * @param status The status to set the fly status to.
	 */
	public void editWatchAndNoFlyList(String username, String status){
		File usrfile;
		boolean found = false;
		String oneLine = "";
		String data = "";
		String updatedLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(detailsFile));
			while(!found && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				//If the first name , last name and DOB are all matched, then it is the person to be modified;
				if(username.equals(words[0])){
					for(int i=0;i<16;i++){
						updatedLine +=  words[i];
						updatedLine += ",";
					}
					updatedLine += status;			//Update the status;
	
					data += updatedLine + "\n";
				}else{
					data += oneLine + "\n";
				}	
            }
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			usrfile = new File(detailsFile);
			writer = new PrintWriter(new FileOutputStream(usrfile));	
			writer.print(data);
            writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves a new Staff to the database.
	 * @param staff The new Staff object representing the new staff profile.
	 * @return True if the saving is successful. False if the username already
	 * exists in the system.
	 */
	public boolean createStaffProfile(Staff staff){
		return true;
	}
	
	/**
	 * Saves an updated Staff object to the database.
	 * @param staff The staff object to save to the database.
	 */
	public void editStaffProfile(Staff staff){
		
	}
	
	/**
	 * Gets a Staff object based on the given username.
	 * @param username The username to get the Staff object by.
	 * @return The Staff object for the given username.
	 */
	public Staff getStaff(String username){
		/*
		 * Only search profiles which the role is not "CUS" or "TA"
		 */
		
		return null;
	}
	
	/**
	 * Charges the price to the user's account.
	 * @param username The username to charge the price to.
	 * @param price The price charged to the user's account
	 */
	public void chargeAccount(String username, double price){
		
	}
}
