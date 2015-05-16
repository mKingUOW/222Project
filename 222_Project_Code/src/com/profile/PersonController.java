/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profile;

import com.helpers.Person;

/**
 *
 * @author Michael Y.M. Kong
 */
public class PersonController {
	private PersonEntity pe = new PersonEntity();
	
	public int addPerson(Person person){
		return pe.addPerson(person);
	}
}
