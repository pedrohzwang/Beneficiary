package entities;

import enums.Category;

import java.util.Date;

public abstract class User implements interfaces.User {
	private String name;
	private Date bornDate;
	private String state;
	private Category category;

	public User(String name, Date bornDate, String state, Category category) {
		this.name = name;
		this.bornDate = bornDate;
		this.state = state;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Date getBornDate() {
		return bornDate;
	}

	private void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getState() {
		return state;
	}

	private void setState(String state) {
		this.state = state;
	}

	public Category getCategory() {
		return category;
	}

	protected void setCategory(Category category) {
		this.category = category;
	}

	public abstract double calculateBenefitValue();

	public abstract double calculateBenefitDuration();

	//Regra de negocio especifica P
	public double additionAM(double totalValue){
		final double ADDITION = 0.11;
		return totalValue + (totalValue * ADDITION);
	}

	//Regra de negocio especifica P
	public boolean isAMState(){
		return getState().substring(0, 2).equalsIgnoreCase("AM");
	}
}
