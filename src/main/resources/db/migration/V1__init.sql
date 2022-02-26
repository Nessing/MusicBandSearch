CREATE TABLE ROLES
(
    id      SERIAL PRIMARY KEY,
    role    VARCHAR(20)
);

INSERT INTO ROLES (role) VALUES
('musician'),
('band'),
('costumer');


CREATE TABLE INSTRUMENTS
(
    id         BIGSERIAL PRIMARY KEY,
    instrument VARCHAR(150)
);

INSERT INTO INSTRUMENTS (instrument) VALUES
('guitar'),
('drums'),
('keyboard');


CREATE TABLE GENRES
(
    id    BIGSERIAL PRIMARY KEY,
    genre VARCHAR(50)
);

INSERT INTO GENRES (genre) VALUES
('metal'),
('rock'),
('glam-metal');


CREATE TABLE TOWNS (
    id      BIGSERIAL PRIMARY KEY,
    town    VARCHAR(150)
);

INSERT INTO TOWNS (town) VALUES
('San-Francisco'),
('Los Angeles');


CREATE TABLE USERS
(
    id             BIGSERIAL PRIMARY KEY,
    email          VARCHAR(150) NOT NULL,
    password       VARCHAR(100) NOT NULL,
    nickname       VARCHAR(75) NOT NULL,
    first_name     VARCHAR(75),
    last_name      VARCHAR(75),
    phone          VARCHAR(20),
    instrument     INT REFERENCES INSTRUMENTS (id),
    genre          INT REFERENCES GENRES (id),
    town           INT REFERENCES TOWNS (id),
    role           INT REFERENCES ROLES (id),
    info           VARCHAR(255)
);

INSERT INTO USERS (email, password, nickname) VALUES
('graf5@mail.com', '2321', 'graf'),
('motleycrue@gmail.com', 'gt23s', 'Motley Crue');


CREATE TABLE USER_INSTRUMENTS
(
    user_id         BIGSERIAL REFERENCES USERS (id),
    instrument_id   BIGSERIAL REFERENCES INSTRUMENTS (id)
);

INSERT INTO USER_INSTRUMENTS (user_id, instrument_id) VALUES
(1, 3),
(2, 1);


CREATE TABLE USER_GENRES
(
    user_id         BIGSERIAL REFERENCES USERS (id),
    genre_id        BIGSERIAL REFERENCES GENRES (id)
);

INSERT INTO USER_GENRES (user_id, genre_id) VALUES
(1, 2),
(2, 3);


CREATE TABLE USER_TOWN
(
    user_id     BIGSERIAL REFERENCES USERS (id),
    town_id     BIGSERIAL REFERENCES TOWNS (id)
);

INSERT INTO USER_TOWN (user_id, town_id) VALUES
(1, 2),
(2, 1);


CREATE TABLE USER_ROLE
(
    user_id     BIGSERIAL REFERENCES USERS (id),
    role_id     BIGSERIAL REFERENCES ROLES (id)
);

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1, 1),
(2, 2);
