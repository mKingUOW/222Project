/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpers;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * This test is used to test the ServiceBooking class.
 * @author Michael Y.M. Kong
 */
public class ServiceBookingTest {
	
	private static ServiceBooking instance1;
	private static ServiceBooking instance2;
	
	public ServiceBookingTest() {
	}
	
	@Before
	public void setUp() {
		instance1 = new ServiceBooking();
		instance2 = new ServiceBooking();
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of TicketId methods, of class ServiceBooking.
	 */
	@Test
	public void testTicketId() {
		System.out.println("Ticket Id");
		
		int sample_ticket_id = 100;
		
		//set the ticket ID
		instance1.setTicketId(sample_ticket_id);
		instance2.setTicketId(sample_ticket_id);
		
		//check that the value put in should be the same
		assertTrue(instance1.getTicketId() == sample_ticket_id);
		assertTrue(instance2.getTicketId() == sample_ticket_id);
		
		//assert that what is returned should be the same
		assertEquals(instance1.getTicketId(), instance2.getTicketId());
		assertEquals(instance2.getTicketId(), instance1.getTicketId());
	}

	/**
	 * Test of BookingId methods, of class ServiceBooking.
	 */
	@Test
	public void testBookingId() {
		System.out.println("Booking Id");
		
		int sample_booking_id = 5;
		
		//set the booking ID
		instance1.setBookingId(sample_booking_id);
		instance2.setBookingId(sample_booking_id);
		
		//check that the value put in should be the same
		assertTrue(instance1.getBookingId() == sample_booking_id);
		assertTrue(instance2.getBookingId() == sample_booking_id);
		
		//assert that what is returned should be the same
		assertEquals(instance1.getBookingId(), instance2.getBookingId());
		assertEquals(instance2.getBookingId(), instance1.getBookingId());
	}

	/**
	 * Test of ServiceId methods, of class ServiceBooking.
	 */
	@Test
	public void testServiceId() {
		System.out.println("Service Id");
		
		int sample_service_id = 38;
		
		//set the service ID
		instance1.setServiceId(sample_service_id);
		instance2.setServiceId(sample_service_id);
		
		//check that the value put in should be the same
		assertTrue(instance1.getServiceId() == sample_service_id);
		assertTrue(instance2.getServiceId() == sample_service_id);
		
		//assert that what is returned should be the same
		assertEquals(instance1.getServiceId(), instance2.getServiceId());
		assertEquals(instance2.getServiceId(), instance1.getServiceId());
	}
}