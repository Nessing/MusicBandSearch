CREATE extension IF NOT EXISTS "uuid-ossp";
CREATE TABLE user_table (
  id uuid DEFAULT uuid_generate_v4 (),
  email VARCHAR(56) NOT NULL,
  telegram_id VARCHAR(56) NULL,
  phone VARCHAR(56) NULL,
  password varchar(128) NOT NULL,
  location VARCHAR(255),
  time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
  );
CREATE TABLE roles_table (
  id SERIAL,
  title varchar(56) not null,
  time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id));
CREATE TABLE user_roles (
  id_user uuid,
  id_role SERIAL,
  PRIMARY KEY (id_user,id_role),
  FOREIGN KEY (id_user) REFERENCES user_table (id) ON DELETE CASCADE,
  FOREIGN KEY (id_role) REFERENCES roles_table (id) ON DELETE SET NULL
  );
