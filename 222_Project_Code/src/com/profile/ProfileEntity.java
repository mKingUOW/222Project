/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
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
	private BufferedReader reader;	
	private PrintWriter accWriter;
	private PrintWriter usrWriter;
	private File accfile;
	private File usrfile;
	private String accountFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userAccount.csv";
	private String detailsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userDetail.csv";
	private String passwdInDB;
	private String role;
	
	public ProfileEntity(){
		
	}
	
	public String login(String username, char[] password){
		String passwd = new String(password);
		String oneLine = "";
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

	public boolean signUp(Customer user){
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
		
		//If the account doesn't exist in the DB, then insert the username/password/role into the DB;
		try{
			accfile = new File(accountFile);
			usrfile = new File(detailsFile);
			accWriter = new PrintWriter(new FileOutputStream(accfile,true));		//To append to the file using "true";
			accWriter.println(user.getUsername() + "," + passwd + "," + user.getRole());
            accWriter.close();	

			usrWriter = new PrintWriter(new FileOutputStream(usrfile,true));		//To append to the file using "true";
			usrWriter.println(user.getUsername() + "," + user.getTitle() + "," + user.getFirstName() + "," 
							  + user.getLastName() + "," + user.getGender() + "," + user.getDOB() + "," 
							  + user.getPhoneNumber() + "," + user.getEmail() + "," + user.getStreet() + "," 
							  + user.getState() + "," + user.getCity() + "," + user.getCountry() + ","
							  + user.getCreditCardType() + "," + user.getCreditCardNumber() + "," + user.getFrequentFlierPoints()
							  + "," + user.hasPassport() + "," + user.getWatchOrNoFly() );
			usrWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
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
	
	public void savePassword(String username, char[] password){
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
			accWriter = new PrintWriter(new FileOutputStream(accfile));	
			accWriter.print(data);
            accWriter.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
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
	
	public void setFrequentFlierPoints(String username, int points){
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
			usrWriter = new PrintWriter(new FileOutputStream(usrfile));	
			usrWriter.print(data);
            usrWriter.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void closeAccount(String username){
		
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
			accWriter = new PrintWriter(new FileOutputStream(accfile));	
			accWriter.print(accData);
            accWriter.close();	
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
			usrWriter = new PrintWriter(new FileOutputStream(usrfile));	
			usrWriter.print(usrData);
            usrWriter.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
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
	
	public void setAccountDetails(Person account){		
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
			usrWriter = new PrintWriter(new FileOutputStream(usrfile));	
			usrWriter.print(data);
            usrWriter.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String canUserFly(String username){		
		boolean found = false;
		String watchOrNoFly = "";
		
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
	
	public void editWatchAndNoFlyList(String username, String status){
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
			usrWriter = new PrintWriter(new FileOutputStream(usrfile));	
			usrWriter.print(data);
            usrWriter.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
