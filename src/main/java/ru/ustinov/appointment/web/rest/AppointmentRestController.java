package ru.ustinov.appointment.web.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ustinov.appointment.model.Appointment;
import ru.ustinov.appointment.repository.AppointmentRepository;
import ru.ustinov.appointment.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
@Tag(name = "Appointment Controller")
@RestController
@RequestMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentRestController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/freeSlots")
    public List<Appointment> getFreeAppointments(@RequestParam Long doctorId, @RequestParam LocalDate date) {
        return appointmentRepository.getFreeAppointments(doctorId, date.atStartOfDay());
    }

    @PostMapping("/{appointmentId}")
    public ResponseEntity<Appointment> takeAppointmentByPatient(@PathVariable Long appointmentId, @RequestBody Long patientId) {
        final Appointment appointment = appointmentService.takeAppointment(appointmentId, patientId);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/{patientId}")
    public List<Appointment> getPatientAppointments(@PathVariable Long patientId) {
        return appointmentRepository.getPatientAppointments(patientId);
    }
}
