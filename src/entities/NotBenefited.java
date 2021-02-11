package entities;

import enums.Category;

import java.util.Date;

public class NotBenefited extends User{

    public NotBenefited(String name, Date bornDate, String state) {
        super(name, bornDate, state, Category.NONE);
    }

    @Override
    public double calculateBenefitValue() {
        return 0;
    }

    @Override
    public double calculateBenefitDuration() {
        return 0;
    }
}
