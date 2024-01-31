package ru.ustinov.appointment.xml;

import appointment.schedule_web_service.AppointmentType;
import appointment.schedule_web_service.CreateScheduleResponse;
import ru.ustinov.appointment.model.Appointment;

import java.util.List;

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
        appointmentType.setDoctorName(appointment.getDoctor().getFullName());
        appointmentType.setId(appointment.getId());
        return appointmentType;
    }

    public static CreateScheduleResponse createAppointmentResponse(List<Appointment> schedule) {
        final CreateScheduleResponse createScheduleResponse = new CreateScheduleResponse();
        final List<AppointmentType> appointmentTypes = createScheduleResponse.getAppointments();
        for (Appointment appointment : schedule) {
            final AppointmentType appointmentType = fromAppointmentToXmlType(appointment);
            appointmentTypes.add(appointmentType);
        }
        return createScheduleResponse;
    }
}
