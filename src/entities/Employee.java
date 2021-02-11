package entities;

import enums.Category;

import java.util.Date;

public class Employee extends User {

    private boolean retired;
    private final int AGE = BeneficiaryUtil.calculateAge(this.getBornDate());

    public Employee(String name, Date bornDate, String state, boolean retired) {
        super(name, bornDate, state, Category.EMPLOYEE);
        this.retired = retired;
    }

    public boolean isRetired() {
        return retired;
    }

    private void setRetired(boolean retired) {
        this.retired = retired;
    }

    @Override
    public double calculateBenefitValue() {
        double totalValue = 0.0;
        if(AGE >= 18 || AGE <= 20){
            totalValue = 1000.00;
        } else if(AGE > 20 || AGE <= 25){
            totalValue = 1400.00;
        } else if(AGE > 25){
            totalValue = 1800.00;
        } else {
            totalValue = 1000.00;
        }

        if (isAMState()){
            double addition = additionAM(totalValue);
            return addition;
        }
        return totalValue;
    }

    @Override
    public double calculateBenefitDuration() {
        double duration = 0.00;
        if (this.isRetired()) {
            return calculateRetiredBenefitDuration();
        } else if (AGE >= 18 || AGE <= 20){
            duration = 5.00;
        } else if(AGE > 20 || AGE <= 25){
            duration = 8.00;
        } else if(AGE > 25){
            duration = 12.00;
        }
        return duration;
    }

    //Regra de negocio especifica E
    private double calculateRetiredBenefitDuration(){
        return 6.00;
    }

}
