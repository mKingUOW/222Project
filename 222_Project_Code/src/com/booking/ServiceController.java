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
	
	private ServiceEntity se = new ServiceEntity();
	
	public ServiceController(){
		
	}
	
	public List<Service> getServices(boolean isInternational){
		return se.getServices(isInternational);
	}
	
	public Service getService(int serviceId){
		return se.getService(serviceId);
	}
}
