CREATE TABLE music_style_table (
  id SERIAL,
  title VARCHAR(56) NOT NULL,
  PRIMARY KEY (id)
  );
CREATE TABLE music_instrument_table (
  id SERIAL,
  title VARCHAR(56) NOT NULL,
  PRIMARY KEY (id)
  );
CREATE TABLE musician_style_table (
  id_user uuid NOT NULL,
  id_style SERIAL NOT NULL,
  PRIMARY KEY (id_user,id_style),
  FOREIGN KEY (id_user) REFERENCES user_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_style) REFERENCES music_style_table (id) ON DELETE SET NULL
  );
CREATE TABLE musician_instrument_table (
  id_user uuid NOT NULL,
  id_instrument SERIAL NOT NULL,
  PRIMARY KEY (id_user,id_instrument),
  FOREIGN KEY (id_user) REFERENCES user_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_instrument) REFERENCES music_instrument_table (id) ON DELETE SET NULL
  );