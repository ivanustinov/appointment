package ru.ustinov.appointment.web.soap;

import appointment.schedule_web_service.CreateScheduleRequest;
import appointment.schedule_web_service.CreateScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.ustinov.appointment.model.Appointment;
import ru.ustinov.appointment.model.Doctor;
import ru.ustinov.appointment.repository.DoctorRepository;
import ru.ustinov.appointment.service.AppointmentService;
import ru.ustinov.appointment.xml.AppointmentConverter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 29.01.2024
 */
@Endpoint
public class ScheduleService {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @PayloadRoot(namespace = "http://appointment/schedule-web-service", localPart = "createScheduleRequest")
    @ResponsePayload
    public CreateScheduleResponse createSchedule(@RequestPayload CreateScheduleRequest request) {
        final Duration duration = Duration.ofMinutes(request.getDuration());
        LocalDateTime timeStart = request.getStartTime();
        final int numberOfSlots = request.getNumberOfSlots();
        final long doctorId = request.getDoctorId();
        final Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id: " + doctorId));
        // создаем расписание
        final List<Appointment> schedule = appointmentService.createSchedule(numberOfSlots, duration, timeStart, doctor);
        // конвертируем в xml
        return AppointmentConverter.createAppointmentResponse(schedule, doctor, timeStart.toLocalDate());
    }

}

