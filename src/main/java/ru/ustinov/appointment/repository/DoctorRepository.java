package ru.ustinov.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ustinov.appointment.model.Doctor;
import ru.ustinov.appointment.model.Patient;

/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
