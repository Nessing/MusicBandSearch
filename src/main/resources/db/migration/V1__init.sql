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
('New York'),
('Los Angeles');


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

INSERT INTO USERS_TABLE (email, password, nickname, phone, about, role, avatar)VALUES
('graf5@mail.com', '$2a$12$e.zXKJXPYz.ypR8J0lfgmehp/SpkoBlUY0flPlSfw7jrLjC.S1XI.', 'graf', '953-875-36-42', 'заканчивал музыкальную школу по классу фортепиано. Так же имею опыт игры на гитаре (2 года). ' ||
                                                    'ищу группу, которая будет играет музыку похожую на Dragonforce, Dream Theater', 1, true),
('stieve@gmail.com', 'gt23s', 'stieve', null, null, 1, false),
('farcry@gmail.com', 'gt23s', 'farcry', null, null, 2, false),
('vandal@gmail.com', 'gt23s', 'vandal', null, null, 2, false);


CREATE TABLE USER_INSTRUMENTS
(
    user_id         BIGSERIAL REFERENCES USERS_TABLE (id),
    instrument_id   BIGSERIAL REFERENCES INSTRUMENTS (id)
);

INSERT INTO USER_INSTRUMENTS (user_id, instrument_id) VALUES
(1, 3),
(1, 1),
(2, 1),
(3, 2),
(3, 1),
(4, 3);


CREATE TABLE USER_GENRES
(
    user_id         BIGSERIAL REFERENCES USERS_TABLE (id),
    genre_id        BIGSERIAL REFERENCES GENRES (id)
);

INSERT INTO USER_GENRES (user_id, genre_id) VALUES
(1, 2),
(1, 1),
(2, 3),
(3, 2),
(3, 3),
(4, 2);


CREATE TABLE USER_TOWN
(
    user_id     BIGSERIAL REFERENCES USERS_TABLE (id),
    town_id     BIGSERIAL REFERENCES TOWNS (id)
);

INSERT INTO USER_TOWN (user_id, town_id) VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 3);


CREATE TABLE USER_ROLE
(
    user_id     BIGSERIAL REFERENCES USERS_TABLE (id),
    role_id     BIGSERIAL REFERENCES ROLES (id)
);

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 1);
