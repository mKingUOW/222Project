/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package profile;

import com.helpers.Customer;
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
							  + user.getCreditCardType() + "," + user.getCreditCardNumber() 
							  + "," + user.hasPassport() );
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
		return null;
	}
	
	public void savePassword(String username, char[] password){
		
	}
	
	public void closeAccount(String username){
		
	}
}
