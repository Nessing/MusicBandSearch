CREATE TABLE group_table (
  id uuid DEFAULT uuid_generate_v4 (),
  title VARCHAR(127) NOT NULL,
  id_style SERIAL NOT NULL,
  price decimal NOT NULL,
  program_duration decimal NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_style) REFERENCES music_style_table (id) ON DELETE SET NULL
  );
CREATE TABLE musician_finder_table (
  id uuid DEFAULT uuid_generate_v4 (),
  id_role SERIAL NOT NULL,
  id_group uuid NOT NULL,
  id_instrument SERIAL NOT NULL,
  id_style SERIAL NOT NULL,
  description VARCHAR(255),
  price decimal ,
  location VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (id_style) REFERENCES music_style_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_instrument) REFERENCES music_instrument_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_group) REFERENCES group_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_role) REFERENCES roles_table (id) ON DELETE SET NULL
  );