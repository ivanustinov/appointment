package ru.ustinov.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ustinov.appointment.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor.id=:doctorId and function('date_trunc', 'day', a.startTime)=:date and a.patient is null")
    List<Appointment> getFreeAppointments(Long doctorId, LocalDateTime date);

    @Query("select a from Appointment a where a.patient.id=:patientId")
    List<Appointment> getPatientAppointments(Long patientId);

    @Query("select a from Appointment a where a.startTime<:endTime and a.endTime>:start and a.patient is not null and a.patient.id<>:patientId")
    List<Appointment> getAppointmentOnDurationByPatient(LocalDateTime start, LocalDateTime endTime, Long patientId);

    @Query("select a from Appointment a where a.startTime<:endTime and a.endTime>:start and a.doctor.id=:doctorId")
   List<Appointment> checkExistedAppointment(LocalDateTime start, LocalDateTime endTime, Long doctorId);
}
