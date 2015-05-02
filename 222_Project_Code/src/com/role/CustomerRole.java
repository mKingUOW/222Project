package com.role;

import java.util.*;

public class CustomerRole extends AbstractCustomerRole{

	private Scanner in = new Scanner(System.in);

	public CustomerRole(){

	}

	@Override
	public void start(){
		super.displayChoices(null);
	}
}