/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.report;

import com.role.Role;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ReportController {
	
	/**
	 * Scanner object to use the standard input console.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Holds the class name of the role.
	 */
	private Role role;
	
	/**
	 * Default constructor.
	 */
	public ReportController() {
	}
	
	/**
	 * Sets the role to displays the right reports.
	 * @param role 
	 */
	public void setRole(Role role){
		this.role = role;
	}
}
