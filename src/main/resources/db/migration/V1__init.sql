CREATE TABLE user_table (
  id VARCHAR(36) NOT NULL,
  email VARCHAR(56) NOT NULL,
  telegram_id VARCHAR(56) NULL,
  phone VARCHAR(56) NULL,
  password varchar(128) NOT NULL,
  time_stamp TIMESTAMP DEFAULT NULL,
  updated_at TIMESTAMP DEFAULT NULL,
  deleted_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
  );
CREATE TABLE roles_table (
  id SERIAL,
  title varchar(56) not null,
  time_stamp TIMESTAMP DEFAULT NULL,
  updated_at TIMESTAMP DEFAULT NULL,
  deleted_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id));
CREATE TABLE user_roles (
  id_user VARCHAR(36),
  id_role SERIAL,
  PRIMARY KEY (id_user,id_role)
  );
