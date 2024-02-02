package ru.ustinov.appointment.service;

import lombok.NoArgsConstructor;
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
 * Сервис работы с талонами(слотами времени)
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

    /**
     * Запись на прием
     *
     * @param appointmentId id талона
     * @param patientId     id пациента
     * @return талон на прием
     */
    @Transactional
    public Appointment takeAppointment(Long appointmentId, Long patientId) {
        final Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        final LocalDateTime startTime = appointment.getStartTime();
        final LocalDateTime endTime = appointment.getEndTime();
        // проверяем, что талон свободен
        final Appointment ifTaken = appointmentRepository.checkIfAlreadyTaken(appointmentId, patientId);
        // проверяем, что пациент уже не записан на это время по другому талону
        final Appointment ifExist = appointmentRepository.checkIfExist(startTime, endTime, patientId, appointmentId);
        if (ifTaken == null && ifExist == null) {
            final Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + patientId));
            appointment.setPatient(patient);
            final Appointment save = appointmentRepository.save(appointment);
            return appointmentRepository.getWithDoctorAndPatient(save.getId().longValue());
        } else {
            throw new AppException(HttpStatus.CONFLICT, ErrorAttributeOptions.of(MESSAGE), "This entry is already taken!");
        }
    }

    /**
     * Создание расписания врача на дату
     *
     * @param numberOfSlots количество талонов
     * @param duration      продолжительность приема в минутах
     * @param timeStart     дата и время начала первого приема
     * @param doctor        врач, для которого создается расписание
     * @return Список созданных талонов(appointments) врача на указанную дату
     */
    @Transactional
    public List<Appointment> createSchedule(int numberOfSlots, Duration duration, LocalDateTime timeStart, Doctor doctor) {
        LocalDateTime endTime;
        Appointment saved;
        final List<Appointment> savedAppointments = new ArrayList<>();
        for (int i = 0; i < numberOfSlots; i++) {
            endTime = timeStart.plusMinutes(duration.toMinutes());
            // проверка на наличие талона на это время во избежание пересеченя по времени
            final List<Appointment> appointmentOnDurationByDoctor = appointmentRepository
                    .checkExistedAppointment(timeStart, endTime, doctor.getId());
            // если талоны на это время для этого врача не созданы, создаем талон
            if (appointmentOnDurationByDoctor.isEmpty()) {
                saved = appointmentRepository.save(new Appointment(timeStart, endTime, doctor));
                savedAppointments.add(saved);
            }
            timeStart = endTime;
        }
        return savedAppointments;
    }

}
