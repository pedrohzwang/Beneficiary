package entities;

import enums.Category;

import java.text.DecimalFormat;
import java.util.Date;

public class Employer extends User {

    private int employeeQuantity;

    public Employer(String name, Date bornDate, String state, int employeeQuantity) {
        super(name, bornDate, state, Category.EMPLOYER);
        this.employeeQuantity = employeeQuantity;
    }

    public int getEmployeeQuantity() {
        return employeeQuantity;
    }

    private void setEmployeeQuantity(int employeeQuantity) {
        this.employeeQuantity = employeeQuantity;
    }

    @Override
    public double calculateBenefitValue() {
        double totalValue = 200.00 * getEmployeeQuantity();
        if(getEmployeeQuantity() <= 40) {
            return Math.ceil(calculateValueEmployee(totalValue));
        }
        if (isAMState()){
            double addition = additionAM(totalValue);
            return Math.ceil(addition);
        }
        return Math.ceil(totalValue);
    }

    //Regra de negocio especifica O
    @Override
    public double calculateBenefitDuration() {
        return 7.00;
    }

    //Regra de negocio especifica D
    private double calculateValueEmployee(double totalValue){
        final double ADDITION = 0.11;
        if (isAMState()){
            double addition = additionAM(totalValue);
            return Math.ceil(addition + (addition * ADDITION));
        }
        return Math.ceil(totalValue + (totalValue * ADDITION));
    }

}
