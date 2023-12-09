CREATE TABLE IF NOT EXISTS specialties
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
    );

CREATE TABLE IF NOT EXISTS developers
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(128)  NOT NULL,
    last_name    VARCHAR(128) NOT NULL,
    specialty VARCHAR(128)
    );

CREATE TABLE IF NOT EXISTS skills
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
    );

CREATE TABLE IF NOT EXISTS developer_skills
(
    developer_id BIGSERIAL PRIMARY KEY,
    skill_id BIGSERIAL,

    FOREIGN KEY (developer_id) REFERENCES developers(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id),
    UNIQUE (developer_id,skill_id)
    );

INSERT INTO specialties (name)
VALUES ('developer');

INSERT INTO developers (first_name, last_name, specialty)
VALUES ('Petr', 'Petrov', 'java');


SELECT d.id, d.first_name, d.last_name,d.specialty,
       s.name as skill_name
FROM developers d
         LEFT JOIN developer_skills ds ON d.id = ds.developer_id
         LEFT JOIN skills s ON ds.skill_id = s.id
         LEFT JOIN specialties sp ON sp.name = d.specialty;