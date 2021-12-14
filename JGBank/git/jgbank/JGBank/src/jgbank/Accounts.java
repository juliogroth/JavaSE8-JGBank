package jgbank;

import java.util.Random;

//1.2.1 Creation of the account class

public abstract class Accounts{
	
	protected String label; //current or savings
	protected double balance = 0; //alterar no exercicio 1.3.5
	protected static int controlAccountNumber =0;
	protected int accountNumber;
	protected Clients Client = new Clients(); //how to declare a class Client as atributte?
	
	protected Flow 	transaction; //tentativa de usar flow como objeto de account;
	
	protected int 	toId;
	protected int	fromId;
	
	
	//constructor
	//changed to protected after commit
	public Accounts(String label, Clients Client) {

		this.label = label;
		this.Client = Client;
		this.accountNumber = ++controlAccountNumber;
//		Random r = new Random();			//para testar a ordenção
//		this.balance = 10* r.nextDouble(); //para testar a ordenção
	}
	
	public Accounts() {
	}

	@Override
	public String toString() {
		return label + "» balance= " + balance + " accountNumber= " + accountNumber +" "+ Client;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) { ////1.3.2 Creation of the Flow class »» ALTERAR PARA RECEBER OBJETO FLOW
		this.balance = balance;
	}

	public static int getControlAccountNumber() {
		return controlAccountNumber;
	}

	public static void setControlAccountNumber(int controlAccountNumber) {
		Accounts.controlAccountNumber = controlAccountNumber;
	}

	public int getAccountNumber() {
		return accountNumber;
	}
	
	public int getAccountNumber(String x) {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Clients getClient() {
		return Client;
	}

	public void setClient(Clients Client) {
		this.Client = Client;
	}
	
//tentativca de usar flow como objeto de accounts
	public Flow getTransaction() {
		return transaction;
	}

	public void setTransaction(Flow transaction) {
		this.transaction = transaction;
		this.fromId=this.transaction.getIdentifier();
		
		switch (this.transaction.getComment()) {
		case "debit":
			this.balance=this.balance - this.transaction.getAmount();
			break;
		case "credit":
			this.balance=this.balance + this.transaction.getAmount();
			break;
			
		default: System.out.println("CAPTURA ERRADA - ACCOUNTS");
		}
		
		
	}

}
