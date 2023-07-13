CREATE DATABASE practic2023;

CREATE TABLE region_list(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE user_(
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    region_id INT REFERENCES region_list(id),
    first_name VARCHAR(100),
    second_name VARCHAR(100),
    is_admin BOOLEAN
);

CREATE TABLE service(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE template(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES user_(id)
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
    frequency BIGINT NOT NULL,
    start_date DATE,
    end_date DATE,
    active_days INT,
    comment TEXT,
    is_active BOOLEAN,
    is_completed BOOLEAN
);

CREATE TABLE user_report(
    id SERIAL PRIMARY KEY,
    user_id int REFERENCES user_(id),
    report_id int REFERENCES report(id)
);

CREATE TABLE report_data(
    id SERIAL PRIMARY KEY,
    service_id INT REFERENCES service(id),
    report_id INT REFERENCES report(id),
    count_1 INT,
    count_2 INT,
    percent_1 NUMERIC(3,1),
    percent_2 NUMERIC(3,1),
    regular_act TEXT
);
