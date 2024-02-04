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
    status VARCHAR(64) not NULL,
    specialty_id BIGINT,
    FOREIGN KEY (specialty_id) REFERENCES specialties (id)
);

CREATE TABLE IF NOT EXISTS skills
(
    id   BIGSERIAL primary key ,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS developer_skills
(
    developer_id BIGSERIAL,
    skill_id BIGSERIAL,

    FOREIGN KEY (developer_id) REFERENCES developers(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id),
    UNIQUE (developer_id,skill_id)
);

INSERT INTO specialties (name)
VALUES ('developer'),
       ('employee');

INSERT INTO developers (first_name, last_name, specialty_id, status)
VALUES ('Petr', 'Petrov', 1, 'Active' );

INSERT INTO skills(name)
values ('java'),
       ('sql');

insert into developer_skills(developer_id,skill_id)
values (1,1),
       (1,2);

--=============================

SELECT d.id,
       d.first_name,
       d.last_name,
       sp.id   as spec_id,
       sp.name as spec_name,
       s.id    as skill_id,
       s.name  as skill_name
FROM developers d
         LEFT JOIN developer_skills ds ON d.id = ds.developer_id
         LEFT JOIN skills s ON ds.skill_id = s.id
         LEFT JOIN specialties sp ON sp.id = d.specialty_id;
