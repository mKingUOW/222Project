/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package profile;

import com.helpers.Customer;

/**
 *
 * @author Michael Y.M. Kong
 */
public class CustomerController {

	private CustomerEntity ce = new CustomerEntity();
	
	public CustomerController() {
	}
	
	public Customer getCustomer(String username){
		return ce.getCustomer(username);
	}
	
	public int getFrequentFlierPoints(String username){
		return ce.getFrequentFlierPoints(username);
	}
	
	public void setFrequentFlierPoints(String username, int points){
		ce.setFrequentFlierPoints(username, points);
	}
}
