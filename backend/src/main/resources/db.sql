\! chcp 1251
CREATE DATABASE practic2023;
\c practic2023

CREATE TABLE region_list(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE user_(
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    region_id INT REFERENCES region_list(id),
    surname VARCHAR(100),
    firstname VARCHAR(100),
    patronymic VARCHAR(100),
    is_admin BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE service(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE template(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    count_all_requests TEXT,
    count_epgu_requests TEXT,
    percent_epgu_requests TEXT,
    percent_not_violation_epgu_requests TEXT
);

CREATE TABLE template_data(
    id SERIAL PRIMARY KEY,
    template_id INT REFERENCES template(id),
    service_id INT REFERENCES service(id)
);

CREATE TABLE report(
    id SERIAL PRIMARY KEY,
    template_id INT REFERENCES template(id),
    region_id INT REFERENCES region_list(id),
    start_date DATE,
    end_date DATE,
    comment TEXT,
    is_active BOOLEAN
);

CREATE TABLE report_data(
    id SERIAL PRIMARY KEY,
    service_id INT REFERENCES service(id),
    report_id INT REFERENCES report(id),
    count_1 INT,
    count_2 INT,
    percent_1 NUMERIC(4,1),
    percent_2 NUMERIC(4,1),
    regular_act TEXT
);
