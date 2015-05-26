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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
