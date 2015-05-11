/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.booking;

import com.helpers.Service;
import java.util.List;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceController {
	
	public ServiceController(){
		
	}
	
	public List<Service> getServices(boolean isInternational){
		ServiceEntity se = new ServiceEntity();
		
		return se.getServices(isInternational);
	}
}
