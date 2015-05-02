package role;

import java.util.*;

protected abstract class AbstractCustomerRole implements Role{

	private String[] choices = {"Make Booking", "Edit Services", "Close Account", "Cancel Booking", "Edit Account"};

	public void editServices(){

	}

	public void closeAccount(){

	}

	public void cancelBooking(){

	}

	public void editAccount(){

	}

	public void makeBooking(){

	}

	@Override
	public void displayChoices(String additionalChoices){
		List<String> allChoices = new ArrayList<String>();
		int userChoice;

		boolean isInputValid;
		
		if (additionalChoices != null) {
			allChoices.addAll(Arrays.asList(additionalChoices));
		}
		allChoices.addAll(Arrays.asList(choices));
		allChoices.addAll(Arrays.asList(standardChoices));

		do{	
			isInputValid = true;

			for (int i = 0; i < allChoices.size(); i++) {
				System.out.println((i + 1) + ": " + choices.get(i));
			}
			System.out.print("Your Choice: ");
			try{
				userChoice = in.nextInt();
			} catch(InputMismatchException ex){
				isInputValid = false;
			}

		} while(!isInputValid);
	}

	public String[] getChoices(){
		return choices;
	}
}