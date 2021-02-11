package entities;

import java.util.Calendar;
import java.util.Date;

public final class BeneficiaryUtil {

    public static int calculateAge(Date bornDate){
        Calendar cBornYear = Calendar.getInstance();
        Calendar cActualYear = Calendar.getInstance();
        cBornYear.setTime(bornDate);
        cActualYear.set(Calendar.YEAR, cActualYear.get(Calendar.YEAR));
        int age = cActualYear.get(Calendar.YEAR) - cBornYear.get(Calendar.YEAR);
        if(age < 0){
            throw new RuntimeException();
        }
        return age;
    }

    public static boolean isSameState(String beneficiaryState, String state){
        if(beneficiaryState.equalsIgnoreCase(state.substring(0, 2))){
            return true;
        }
        return false;
    }

}
