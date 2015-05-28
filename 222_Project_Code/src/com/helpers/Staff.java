/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *
 * @author Michael Y.M. Kong
 */
public class Staff {
	/**
	 * Username of staff.
	 */
	private String username;
	
	/**
	 * Password of staff.
	 */
	private char[] password;
	
	/**
	 * Role of staff.
	 */
	private String role;

	/**
	 * Default constructor.
	 */
	public Staff() {
	}
	
	public Staff(String uname,String passwd,String role){
		this.username = uname;
		this.password = passwd.toCharArray();
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

	public void setPassword(String passwd) {
		this.password = passwd.toCharArray();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
