package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MagicNumber")
public class Task2 {
    private Task2() {
    }

    public static List<LocalDate> findFridayThe13th(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Invalid year");
        }

        List<LocalDate> fridayThe13ths = new ArrayList<>();
        LocalDate date = LocalDate.of(year, 1, 13);

        while (date.getYear() == year) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridayThe13ths.add(date);
            }
            date = date.plusMonths(1);
        }

        return fridayThe13ths;
    }

    public static LocalDate nextFridayThe13th(LocalDate date) {

        TemporalAdjuster temporalAdjuster = TemporalAdjusters.ofDateAdjuster(curDate -> {
            LocalDate newDate = curDate;

            while (newDate.getDayOfMonth() != 13) {
                newDate = newDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            }

            return newDate;
        });

        return date.with(temporalAdjuster);
    }
}
