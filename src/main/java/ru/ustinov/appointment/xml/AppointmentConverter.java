package ru.ustinov.appointment.xml;

import appointment.schedule_web_service.AppointmentType;
import ru.ustinov.appointment.model.Appointment;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 31.01.2024
 */
public class AppointmentConverter {

    public static AppointmentType fromAppointmentToXmlType(Appointment appointment) {
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setStartTime(appointment.getStartTime());
        appointmentType.setEndTime(appointment.getEndTime());
        appointmentType.setDoctorId(appointment.getDoctor().getId());
        appointmentType.setId(appointment.getId());
        return appointmentType;
    }
}
