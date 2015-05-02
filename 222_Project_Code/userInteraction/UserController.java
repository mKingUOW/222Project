package userInteraction;

import java.util.*;
import role.*;

public class UserController{
	private Role role;
	private UserEntity ue = new UserEntity();

	public UserController(){} //default constructor

	public boolean login(String username, char[] password){
		String roleName = ue.login(username, password);
		
		if ("loginFail".equals(roleName)) {
			return false;
		}

		setRole(roleName);

		return true;
	}

	public displayChoices



	private void setRole(String roleName){
		role = RoleFactory.getRole(roleName);
	}

}
