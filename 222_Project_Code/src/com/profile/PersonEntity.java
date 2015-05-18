/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Customer;
import com.helpers.Person;
import java.io.*;

/**
 *
 * @author Michael Y.M. Kong
 */
public class PersonEntity {
	
	private String filepath = System.getProperty("user.dir") + File.separator + "database" + File.separator + "otherPersons.csv";
	private File pfile;
	private BufferedReader reader;	
	private PrintWriter writer;
	
	public PersonEntity(){
		
	}
	
	/**
	 * 
	 * @param person
	 * @return the ID of this person
	 */
	public int addPerson(Person person){
		int pid = 0;		//If no record in the file, default value is 0; It will be incremented by 1 if a record is inserted;
		String oneLine = "";
		
		try{
			reader = new BufferedReader(new FileReader(filepath));			
			while(((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				pid = Integer.parseInt(words[0]); 				
            }	//Keep reading the file, and assign the person id to pid,until the last record;
			
            reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			pid++;		// Increment the person id by 1, for the next person record;
			pfile = new File(filepath);
			writer = new PrintWriter(new FileOutputStream(pfile,true));		//To append to the file using "true";
			writer.print(pid + ",");
			writer.println(  person.getTitle() + "," + person.getFirstName() + "," + person.getLastName() + "," 
						   + person.getGender() + "," + person.getDOB() + "," + person.getPhoneNumber() + ","
						   + person.getEmail() + "," + person.getStreet() + "," + person.getState() + ","
						   + person.getCity() + "," + person.getCountry() + "," + person.getCreditCardType() + ","
						   + person.getCreditCardNumber() + "," + person.hasPassport() 
						  );						   
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pid; 
	}
}
