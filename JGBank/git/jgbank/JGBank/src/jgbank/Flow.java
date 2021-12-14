package jgbank;

import java.time.LocalDate;
import java.util.Date;

//1.3.2 Creation of the Flow class

public abstract class Flow {
	private String	comment;
	private int		identifier;
	private double	amount;
	private int		target; //target account number
	private boolean	effect;
	private LocalDate	dateOfFlow;
	
	//constructor
	public Flow() {
	
	}
	
	//constructor
	public Flow(String comment, int identifier, double amount, int target, boolean effect, LocalDate dateOfFlow) {
		this.comment = comment;
		this.identifier = identifier;
		this.amount = amount;
		this.target = target;
		this.effect = effect;
		this.dateOfFlow = dateOfFlow;
	}

	//getters and setters
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
		
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public boolean isEffect() {
		return effect;
	}
	public void setEffect(boolean effect) {
		this.effect = effect;
	}
	public LocalDate getDateOfFlow() {
		return dateOfFlow;
	}
	public void setDateOfFlow(LocalDate dateOfTransf) {
		this.dateOfFlow = dateOfTransf;
	}
	
}
