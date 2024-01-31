package ru.ustinov.appointment.service;

import appointment.schedule_web_service.CreateScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.appointment.exception.AppException;
import ru.ustinov.appointment.model.Appointment;
import ru.ustinov.appointment.model.Doctor;
import ru.ustinov.appointment.model.Patient;
import ru.ustinov.appointment.repository.AppointmentRepository;
import ru.ustinov.appointment.repository.DoctorRepository;
import ru.ustinov.appointment.repository.PatientRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public Appointment takeAppointment(Long appointmentId, Long patientId) {
        final Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        final LocalDateTime startTime = appointment.getStartTime();
        final LocalDateTime endTime = appointment.getEndTime();
        final List<Appointment> onDuration = appointmentRepository.getAppointmentOnDurationByPatient(startTime, endTime, patientId);
        if (onDuration.isEmpty()) {
            final Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + patientId));
            appointment.setPatient(patient);
            return appointmentRepository.save(appointment);
        } else {
            throw new AppException(HttpStatus.CONFLICT, ErrorAttributeOptions.of(MESSAGE), "This entry is already taken!");
        }
    }

    @Transactional
    public List<Appointment> createSchedule(CreateScheduleRequest request) {
        final List<Appointment> savedAppointments = new ArrayList<>();
        final Duration duration = Duration.ofMinutes(request.getDuration());
        LocalDateTime timeStart = request.getStartTime();
        final int numberOfSlots = request.getNumberOfSlots();
        final long doctorId = request.getDoctorId();
        final Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id: " + doctorId));
        LocalDateTime endTime;
        for (int i = 0; i < numberOfSlots; i++) {
            endTime = timeStart.plusMinutes(duration.toMinutes());
            final List<Appointment> appointmentOnDurationByDoctor = appointmentRepository
                    .checkExistedAppointment(timeStart, endTime, doctorId);
            if (appointmentOnDurationByDoctor.isEmpty()) {
                final Appointment saved = appointmentRepository.save(new Appointment(timeStart, endTime, doctor));
                savedAppointments.add(saved);
            }
            timeStart = endTime;
        }
        return savedAppointments;
    }
}
