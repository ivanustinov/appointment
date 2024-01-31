-- Создание таблицы врачей
CREATE TABLE IF NOT EXISTS doctors
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    uuid      uuid         not null
);

-- Создание таблицы пациентов
CREATE TABLE IF NOT EXISTS patients
(
    id         SERIAL PRIMARY KEY,
    full_name  VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL,
    uuid       uuid         not null
);

-- Создание таблицы талонов (слотов времени)
CREATE TABLE IF NOT EXISTS appointments
(
    id serial primary key,
    doctor_id  integer not null
    constraint foreign_key_doctor references doctors,
    start_time timestamp not null
    constraint appointments_start_time_check
    check (cast(start_time as time) >= '9:00'),
    end_time   timestamp not null
    constraint appointments_end_time_check
    check (cast(end_time as time) <= '18:00'),
    patient_id integer
    constraint foreign_key_patient references patients
);
