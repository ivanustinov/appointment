package ru.ustinov.appointment.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import ru.ustinov.appointment.json.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 29.01.2024
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Appointment extends BaseEntity {

    public Appointment(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "start_time", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;

}
