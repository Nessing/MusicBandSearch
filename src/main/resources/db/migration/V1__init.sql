create table user_roles
(
    role_id    bigserial primary key,
    role_title character varying(255)
);
insert into user_roles (role_id, role_title)
values (1, 'Admin'),
       (2, 'Group'),
       (3, 'Musician'),
       (4, 'Customer');
create table genres
(
    genre_id    bigserial primary key,
    genre_title character varying(255)
);
insert into genres (genre_id, genre_title)
values (1, 'Rock'),
       (2, 'Pop');
create table instruments
(
    instrument_id    bigserial primary key,
    instrument_title character varying(255)
);
insert into instruments (instrument_id, instrument_title)
values (1, 'Piano'),
       (2, 'Guitar');
create table towns
(
    town_id    bigserial primary key,
    town_title character varying(255)
);
insert into towns (town_id, town_title)
values (1, 'Moscow'),
       (2, 'Saint Petersburg');
create table users_info
(
    info_id    bigserial primary key,
    user_name  character varying(255),
    user_email character varying(255),
    instruments bigserial,
    genres bigserial,
    towns bigserial,
    foreign key (instruments) references instruments (instrument_id),
    foreign key (genres) references genres (genre_id),
    foreign key (towns) references towns (town_id)
);
insert into users_info (info_id, user_name, user_email, instruments, genres, towns)
values (1, 'user1', 'user1@mail.ru', 1, 1, 1),
       (2, 'user2', 'user2@mail.ru', 1, 1, 2),
       (3, 'user3', 'user3@mail.ru', 1, 2, 2),
       (4, 'user3', 'user3@mail.ru', 2, 2, 2)
;
create table users
(
    user_id   bigserial primary key,
    user_info bigserial,
    user_role bigserial,
    foreign key (user_info) references users_info (info_id),
    foreign key (user_role) references user_roles (role_id),
);
insert into users (user_id, user_info, user_role)
values (1, 1, 2),
       (2, 2, 3),
       (3, 3, 4)
;