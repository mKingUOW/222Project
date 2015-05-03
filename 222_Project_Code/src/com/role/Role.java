package com.role;

public interface Role{
	public String[] STANDARD_CHOICES = {"Change Password", "Logout"};

	public abstract void start();

	public abstract void displayChoices(String additionalChoices);
}