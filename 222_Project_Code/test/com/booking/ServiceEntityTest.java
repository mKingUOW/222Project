/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.booking;

import com.helpers.Service;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Michael Y.M. Kong
 */
public class ServiceEntityTest {
	private ServiceEntity instance;
	
	public ServiceEntityTest() {
	}
	
	@Before
	public void setUp() {
		instance = new ServiceEntity();
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of getServices method, of class ServiceEntity.
	 */
	@Test
	public void testGetServices() {
		System.out.println("Get Services");
		
		int number_of_non_internation_services = 13;
		int total_number_of_services = 22;
		boolean isInternational;
		List<Service> result;
		
		//set up for non international services
		isInternational = false;
		result = instance.getServices(isInternational);
		
		//the number of services that are not internationl should be 13 for this test
		assertEquals(number_of_non_internation_services, result.size());
		
		//set up for international services
		isInternational = true;
		result = instance.getServices(isInternational);
		
		//the number of services that are internationl should be 22 for this test
		assertEquals(total_number_of_services, result.size());
	}

	/**
	 * Test of getService method, of class ServiceEntity.
	 */
	@Test
	public void testGetService() {
		System.out.println("Get Service");
		
		//the service to test for
		Service expected_service = new Service(1, "small meal", 15, "All");
		
		Service result = instance.getService(expected_service.getServiceID());
			
		//assert that these two objects should be the same
		assertEquals(expected_service, result);
	}

	/**
	 * Test of getPrice method, of class ServiceEntity.
	 */
	@Test
	public void testGetPrice() {
		System.out.println("Get Price");
		
		//according to the test data, this ID should return a 
		//value of 20
		int serviceId = 12;
		double expectedPrice = 20;
		
		double result = instance.getPrice(serviceId);
		
		//assert that these two prices should be equal
		assertEquals(expectedPrice, result, 0.0);
	}
}