package com.role;

public class RoleFactory{

	/**
	 * Private default constructor disallows instantiation of an instance
	 */
	private RoleFactory(){

	}

	/**
	 * This class is called with a role name to get a role class back
	 * @param roleName The name of the role.
	 * @return The Role class corresponding to the role name
	 */
	public static Role getRole(String roleName){

		Role role;

		switch(roleName){
			case "CUS": 
				role = new CustomerRole();
				break;
			case "TA":
				role = new TravelAgencyRole();
				break;
			case "NOR":
				role = new NormalStaffRole();
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