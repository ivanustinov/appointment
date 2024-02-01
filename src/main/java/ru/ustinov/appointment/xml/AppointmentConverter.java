package ru.ustinov.appointment.xml;

import appointment.schedule_web_service.AppointmentType;
import appointment.schedule_web_service.CreateScheduleResponse;
import appointment.schedule_web_service.DoctorType;
import appointment.schedule_web_service.ScheduleType;
import ru.ustinov.appointment.model.Appointment;
import ru.ustinov.appointment.model.Doctor;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 31.01.2024
 */
public class AppointmentConverter {

    public static AppointmentType fromAppointmentToXmlType(Appointment appointment) {
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setStartTime(appointment.getStartTime().toLocalTime());
        appointmentType.setEndTime(appointment.getEndTime().toLocalTime());
        appointmentType.setId(appointment.getId());
        return appointmentType;
    }

    public static CreateScheduleResponse createAppointmentResponse(List<Appointment> schedule, Doctor doctor, LocalDate date) {
        final CreateScheduleResponse createScheduleResponse = new CreateScheduleResponse();
        final List<AppointmentType> appointmentTypes = createScheduleResponse.getAppointments();
        for (Appointment appointment : schedule) {
            final AppointmentType appointmentType = fromAppointmentToXmlType(appointment);
            appointmentTypes.add(appointmentType);
        }
        ScheduleType scheduleType = new ScheduleType();
        scheduleType.setScheduleDate(date);
        DoctorType doctorType = new DoctorType();
        doctorType.setDoctorName(doctor.getFullName());
        doctorType.setId(doctor.getId());
        scheduleType.setDoctor(doctorType);
        createScheduleResponse.setSchedule(scheduleType);
        return createScheduleResponse;
    }
}
