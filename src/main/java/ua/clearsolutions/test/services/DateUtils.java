package ua.clearsolutions.test.services;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {
    public static boolean isDateAgeOld(LocalDate date, int age) {
        LocalDate today = LocalDate.now();

        Period period = Period.between(date, today);

        int years = period.getYears();
        int months = period.getMonths();

        return years >= age && months >= 0;
    }

    public static boolean isAfter(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}
