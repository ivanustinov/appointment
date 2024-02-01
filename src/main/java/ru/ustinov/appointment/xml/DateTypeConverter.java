package ru.ustinov.appointment.xml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 31.01.2024
 */
public class DateTypeConverter {

    public static LocalDateTime parseDateTime(String inputDate)  {
        return inputDate != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").parse(inputDate, LocalDateTime::from) : null;
    }

    public static String printDateTime(LocalDateTime inputDate) {
        return inputDate != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").format(inputDate) : null;
    }

    public static LocalDate parseDate(String inputDate)  {
        return inputDate != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(inputDate, LocalDate::from) : null;
    }

    public static String printDate(LocalDate inputDate) {
        return inputDate != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd").format(inputDate) : null;
    }

    public static LocalTime parseTime(String inputTime)  {
        return inputTime != null ? DateTimeFormatter.ofPattern("HH:mm").parse(inputTime, LocalTime::from) : null;
    }

    public static String printTime(LocalTime inputTime) {
        return inputTime != null ? DateTimeFormatter.ofPattern("HH:mm").format(inputTime) : null;
    }
}
