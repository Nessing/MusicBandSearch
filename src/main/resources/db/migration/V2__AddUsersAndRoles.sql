INSERT INTO roles_table (title) VALUES ('ADMIN');
INSERT INTO roles_table (title) VALUES ('MUSICIAN');
INSERT INTO roles_table (title) VALUES ('GROUP');
INSERT INTO roles_table (title) VALUES ('CUSTOMER');
INSERT INTO user_table (email,telegram_id,phone,password) VALUES ('test@mail.ru', 'N/A', 'N/A','$2a$12$e.zXKJXPYz.ypR8J0lfgmehp/SpkoBlUY0flPlSfw7jrLjC.S1XI.');
INSERT INTO user_roles (id_user,id_role) VALUES ((SELECT id FROM user_table where email = 'test@mail.ru'), 1);