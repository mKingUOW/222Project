package com.role;

public class RoleFactory{

	private RoleFactory(){

	}

	public static Role getRole(String roleName){

		Role role;

		switch(roleName){
			case "CUS": 
				role = new CustomerRole();
				break;
			case "TA":
				role = new TravelAgencyRole();
				break;
			case "FM":
				role = new FlightManagerRole();
				break;
			case "PSM":
				role = new ProfileSystemManagerRole();
				break;
			case "SSM":
				role = new ServiceSystemManagerRole();
				break;
			case "RVSM":
				role = new ReservationSystemManagerRole();
				break;
			case "RPSM":
				role = new ReportingSystemManagerRole();
				break;
			case "ADMIN":
				role = new SystemAdministratorRole();
				break;
			default:
				role = null;
				break;
		}
		return role;
	}
}