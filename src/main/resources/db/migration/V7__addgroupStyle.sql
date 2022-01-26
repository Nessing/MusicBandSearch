CREATE TABLE group_style_table (
  id_style SERIAL NOT NULL,
  id_group uuid NOT NULL,
  PRIMARY KEY (id_style,id_group),
  FOREIGN KEY (id_style) REFERENCES music_style_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_group) REFERENCES group_table (id) ON DELETE SET NULL
  );
 INSERT INTO group_style_table (id_style,id_group) VALUES ((SELECT ut.id FROM music_style_table AS ut WHERE ut.title = 'DEFAULT'),(SELECT ut.id FROM group_table AS ut WHERE ut.title = 'DEFAULT'));