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


CREATE TABLE USERS_TABLE
(
    id             BIGSERIAL PRIMARY KEY,
    email          VARCHAR(150) NOT NULL,
    password       VARCHAR(100) NOT NULL,
    nickname       VARCHAR(75) NOT NULL,
    avatar         BOOLEAN NOT NULL,
    first_name     VARCHAR(75),
    last_name      VARCHAR(75),
    phone          VARCHAR(20),
    instrument     INT REFERENCES INSTRUMENTS (id),
    genre          INT REFERENCES GENRES (id),
    town           INT REFERENCES TOWNS (id),
    role           INT REFERENCES ROLES (id),
    about          VARCHAR(255)
);


CREATE TABLE USER_INSTRUMENTS
(
    user_id         BIGSERIAL REFERENCES USERS_TABLE (id),
    instrument_id   BIGSERIAL REFERENCES INSTRUMENTS (id)
);


CREATE TABLE USER_GENRES
(
    user_id         BIGSERIAL REFERENCES USERS_TABLE (id),
    genre_id        BIGSERIAL REFERENCES GENRES (id)
);


CREATE TABLE USER_TOWN
(
    user_id     BIGSERIAL REFERENCES USERS_TABLE (id),
    town_id     BIGSERIAL REFERENCES TOWNS (id)
);


CREATE TABLE USER_ROLE
(
    user_id     BIGSERIAL REFERENCES USERS_TABLE (id),
    role_id     BIGSERIAL REFERENCES ROLES (id)
);
