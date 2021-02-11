package entities;

import enums.Category;

import java.util.Date;

public class Unemployed extends User {

    private double unemployedMonths;

    public Unemployed(String name, Date bornDate, String state, double unemployedMonths) {
        super(name, bornDate, state, Category.UNEMPLOYED);
        this.unemployedMonths = unemployedMonths;
    }

    public double getUnemployedMonths() {
        return unemployedMonths;
    }

    private void setUnemployedMonths(double unemployedMonths) {
        this.unemployedMonths = unemployedMonths;
    }

    @Override
    public double calculateBenefitValue() {
        double totalValue = 0.00;

        if(getUnemployedMonths() < 6){
            return calculateBenefitReduction();
        } else if(getUnemployedMonths() >= 6 || getUnemployedMonths() <= 12) {
            totalValue = 1800.00;
        } else if(getUnemployedMonths() > 12 && getUnemployedMonths() <= 18){
            totalValue = 2000.00;
        } else if(getUnemployedMonths() > 18){
            totalValue = 2300.00;
        }

        if(isAMState()){
            return additionAM(totalValue);
        }
        return totalValue;
    }

    @Override
    public double calculateBenefitDuration() {
        return 12.00;
    }

    //Regra de negocio especifica H
    private double calculateBenefitReduction(){
        final double REDUCTION = 0.1;
        if(isAMState()){
            double addition = additionAM(1800.00);
            return addition - (addition * REDUCTION);
        }
        return 1800.00 - (1800.00 * REDUCTION);
    }
}
