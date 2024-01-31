package ru.ustinov.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ustinov.appointment.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor.id=:doctorId and function('date_trunc', 'day', a.startTime)=:date and a.patient is null")
    List<Appointment> getFreeAppointments(Long doctorId, LocalDate date);

    @Query("select a from Appointment a where a.patient=:patientId")
    List<Appointment> getPatientAppointments(Long patientId);

    @Query("select a from Appointment a where a.startTime>:endTime and a.endTime>:start and a.patient.id=:patientId")
    Optional<Appointment> getAppointmentOnDurationByPatient(LocalDateTime start, LocalDateTime endTime, Long patientId);

    @Query("select a from Appointment a where a.startTime>:endTime and a.endTime>:start and a.doctor.id=:doctorId")
    Optional<Appointment> getAppointmentOnDurationByDoctor(LocalDateTime start, LocalDateTime endTime, Long doctorId);
}
