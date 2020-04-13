package ro.sd.a2.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ComputeAge {

    /**
     * @param dateOfBirth - self-explanatory
     * @return an integer representing the age computed from the dateOfBirth and the localDate.now()
     */
    public static Integer compute(String dateOfBirth){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        String[] dateParts = dateOfBirth.split("-");
        String day = dateParts[2];
        String month = dateParts[1];
        String year = dateParts[0];
        Month month1 = null;
        if (Integer.parseInt(month) == 1) month1 = Month.JANUARY;
        if (Integer.parseInt(month) == 2) month1 = Month.FEBRUARY;
        if (Integer.parseInt(month) == 3) month1 = Month.MARCH;
        if (Integer.parseInt(month) == 4) month1 = Month.APRIL;
        if (Integer.parseInt(month) == 5) month1 = Month.MAY;
        if (Integer.parseInt(month) == 6) month1 = Month.JUNE;
        if (Integer.parseInt(month) == 7) month1 = Month.JULY;
        if (Integer.parseInt(month) == 8) month1 = Month.AUGUST;
        if (Integer.parseInt(month) == 9) month1 = Month.SEPTEMBER;
        if (Integer.parseInt(month) == 10) month1 = Month.OCTOBER;
        if (Integer.parseInt(month) == 11) month1 = Month.NOVEMBER;
        if (Integer.parseInt(month) == 12) month1 = Month.DECEMBER;
        LocalDate birthday = LocalDate.of(Integer.parseInt(year), Objects.requireNonNull(month1), Integer.parseInt(day));  //Birth date
        Period p = Period.between(birthday, localDate);
        return p.getYears();
    }

}
