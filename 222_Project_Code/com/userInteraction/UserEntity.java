package com.userInteraction;

import java.util.*;
import java.io.*;

public class UserEntity{
	
	private BufferedReader reader;	
	private PrintWriter writer;
	private File file;
	private String dbfile = new String(System.getProperty("user.dir") + File.separator + "database" + File.separator + "userAccount.csv");
	private String passwdInDB;
	private String role;
	
	public UserEntity(){
		
	}
	
	public String login(String username, char[] password){
		String passwd = new String(password);
		String oneLine = "";
		boolean existed = false;
		
		try{
			reader = new BufferedReader(new FileReader(dbfile));
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

	public boolean signUp(String username, char[] password,String role){
		String passwd = new String(password);
		String oneLine = "";
		boolean existed = false;
		
		//At first, check whether the account exists already in the DB;
		try{
			reader = new BufferedReader(new FileReader(dbfile));
			while(!existed && ((oneLine = reader.readLine()) != null)){
                String[] words = oneLine.split(",");
				
				if(username.equals(words[0])){
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
			writer = new PrintWriter(file);
			writer.println(username + "," + passwd + "," + role);
            writer.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
};