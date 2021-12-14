package jgbank;

//1.2.2 - Creation of the CurrentAccount and SavingsAccount

public class SavingsAccount extends Accounts {

	protected SavingsAccount(String label, Clients Client) {
		super(label, Client);
	}
}
