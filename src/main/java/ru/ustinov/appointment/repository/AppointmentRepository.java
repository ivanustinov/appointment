package ru.ustinov.appointment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ustinov.appointment.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor.id=:doctorId and function('date_trunc', 'day', a.startTime)=:date and a.patient is null")
    List<Appointment> getFreeAppointments(Long doctorId, LocalDateTime date);

    @EntityGraph(attributePaths = {"doctor"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select a from Appointment a where a.patient.id=:patientId")
    List<Appointment> getPatientAppointments(Long patientId);

    @EntityGraph(attributePaths = {"doctor", "patient"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select a from Appointment a where a.id=:appointmentId")
    Appointment getWithDoctorAndPatient(Long appointmentId);

    // проверка, что талон не занят другим пациентом
    @Query("select a from Appointment a where a.id=:appointmentId and a.patient is not null and a.patient.id<>:patientId")
    Appointment checkIfAlreadyTaken(Long appointmentId, Long patientId);

    // проверка, что пациент не записался на это время по другому талону
    @Query("select a from Appointment a where a.startTime<:endTime and a.endTime>:start " +
            "and a.patient.id=:patientId and a.id<>:appointmentId")
    Appointment checkIfExist(LocalDateTime start, LocalDateTime endTime, Long patientId, Long appointmentId);

    // проверка наличия другого талона на это же время и дату у врача
    @Query("select a from Appointment a where a.startTime<:endTime and a.endTime>:start and a.doctor.id=:doctorId")
    List<Appointment> checkExistedAppointment(LocalDateTime start, LocalDateTime endTime, Integer doctorId);
}
