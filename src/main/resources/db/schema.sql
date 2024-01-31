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
    start_time timestamp(6) not null
    constraint appointments_start_time_check
    check (EXTRACT(hour FROM start_time) >= (9)::numeric),
    end_time   timestamp(6) not null
    constraint appointments_end_time_check
    check (EXTRACT(hour FROM end_time) <= (18)::numeric),
    patient_id integer
    constraint foreign_key_patient references patients
);
