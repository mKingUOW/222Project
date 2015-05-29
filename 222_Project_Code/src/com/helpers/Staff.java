/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpers;

/**
 *	Allows a information of a staff to be passed from class to class in a
 * centralized manner.
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
	
	/**
	 * Constructor with 3 arguments.
	 * @param uname The username
	 * @param passwd The password
	 * @param role The role
	 */
	public Staff(String uname,char[] passwd,String role){
		this.username = uname;
		this.password = passwd;
		this.role = role;
	}
	
	/**
	 * Gets the username of this staff.
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of this staff.
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password of this staff.
	 * @return The password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * Sets the password of this staff.
	 * @param passwd The password
	 */
	public void setPassword(char[] passwd) {
		this.password = passwd;
	}
	
	/**
	 * Gets the role of this staff.
	 * @return The role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role of this staff.
	 * @param role The role
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
