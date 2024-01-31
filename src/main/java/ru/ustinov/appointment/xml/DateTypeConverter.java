package ru.ustinov.appointment.xml;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 31.01.2024
 */
public class DateTypeConverter {

    public static LocalDateTime parseDateTime(String inputDate)  {
        return inputDate != null ? DateTimeFormatter.ofPattern("yyyy-MM-ddTHH.mm.ss.SSS").parse(inputDate, LocalDateTime::from) : null;
    }

    public static String printDateTime(LocalDateTime inputDate) {
        return inputDate != null ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(inputDate) : null;
    }
}
