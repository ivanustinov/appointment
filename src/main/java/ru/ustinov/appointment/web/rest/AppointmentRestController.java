package ru.ustinov.appointment.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ustinov.appointment.model.Appointment;
import ru.ustinov.appointment.repository.AppointmentRepository;
import ru.ustinov.appointment.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;

/**
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

    @Operation(summary = "Cвободные талоны к врачу на дату")
    @GetMapping("/freeSlots")
    public List<Appointment> getFreeAppointments(@Parameter(description = "Id врача") @RequestParam Long doctorId,
        @Parameter(description = "Число в формате yyyy-MM-dd") @RequestParam LocalDate date) {
        return appointmentRepository.getFreeAppointments(doctorId, date.atStartOfDay());
    }

    @Operation(summary = "Запись пациента на прием")
    @PostMapping("/{appointmentId}")
    public ResponseEntity<Appointment> takeAppointmentByPatient(
            @Parameter(description = "Id талона") @PathVariable Long appointmentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Id пациента") @RequestBody Long patientId) {
        final Appointment appointment = appointmentService.takeAppointment(appointmentId, patientId);
        return ResponseEntity.ok(appointment);
    }

    @Operation(summary = "Талоны пациента")
    @GetMapping("/{patientId}")
    public List<Appointment> getPatientAppointments(@Parameter(description = "Id пациента") @PathVariable Long patientId) {
        return appointmentRepository.getPatientAppointments(patientId);
    }
}
