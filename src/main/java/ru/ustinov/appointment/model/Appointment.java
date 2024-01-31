package ru.ustinov.appointment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

/**
 * //TODO add comments.
 *
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

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "start_time", nullable = false)
    @Check(constraints = "EXTRACT(HOUR FROM start_time) >= 9")
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @Check(constraints = "EXTRACT(HOUR FROM end_time) <= 18")
    private LocalDateTime endTime;

}
