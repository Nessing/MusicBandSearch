CREATE TABLE USERS
(
    ID       bigserial PRIMARY KEY,
    LOGIN    VARCHAR(255),
    PASSWORD VARCHAR(255),
    EMAIL    VARCHAR(255) unique,
    NAME     VARCHAR(255),
    LASTNAME VARCHAR(255),
    NICKNAME VARCHAR(255),
    INFO     VARCHAR(255)
);

CREATE TABLE ROLES
(
    ID    bigserial PRIMARY KEY,
    TITLE VARCHAR(255)
);

CREATE TABLE USER_ROLE
(
    USER_ID bigint not null,
    ROLE_ID int    not null,
    primary key (USER_ID, ROLE_ID),
    foreign key (USER_ID) references USERS (ID),
    foreign key (ROLE_ID) references ROLES (ID)
);

CREATE TABLE INSTRUMENTS
(
    ID         bigserial PRIMARY KEY,
    INSTRUMENT VARCHAR(255)
);

CREATE TABLE USER_INSTRUMENT
(
    USER_ID       bigint not null,
    INSTRUMENT_ID bigint not null,
    primary key (USER_ID, INSTRUMENT_ID),
    foreign key (USER_ID) references USERS (ID),
    foreign key (INSTRUMENT_ID) references INSTRUMENTS (ID)
);

CREATE TABLE GENRES_MUSICAL
(
    ID    bigserial PRIMARY KEY,
    GENRE VARCHAR(255)
);

CREATE TABLE USER_GENRE
(
  USER_ID bigint not null,
  GENRE_ID bigint not null,
  primary key (USER_ID, GENRE_ID),
  foreign key (USER_ID) references USERS (ID),
  foreign key (GENRE_ID) references GENRES_MUSICAL (ID)
);

CREATE TABLE TOWNS (
  ID bigserial PRIMARY KEY,
  NAME VARCHAR(255)
);

CREATE TABLE USER_TOWN(
  USER_ID bigint not null,
  TOWN_ID bigint not null,
  primary key (USER_ID, TOWN_ID),
  foreign key (USER_ID) references USERS (ID),
  foreign key (TOWN_ID) references TOWNS (ID)
);
