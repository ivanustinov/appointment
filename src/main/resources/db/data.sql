INSERT INTO doctors (full_name, uuid)
values ('Петров Семен Семенович', gen_random_uuid()),
       ('Иванов Сергей Сергеевич', gen_random_uuid()),
       ('Сидоров Андрей Геннадьевич', gen_random_uuid());

INSERT INTO patients (full_name, birth_date, uuid)
values ('Аарон Игоревич Пригожин', '1961-02-15', gen_random_uuid()),
       ('Устинов Дмитрий Федорович', '1936-06-25', gen_random_uuid());
