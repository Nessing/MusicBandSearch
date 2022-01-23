CREATE TABLE musician_group_table (
  id_user uuid NOT NULL,
  id_group uuid NOT NULL,
  PRIMARY KEY (id_user,id_group),
  FOREIGN KEY (id_user) REFERENCES user_table (id) ON DELETE SET NULL,
  FOREIGN KEY (id_group) REFERENCES group_table (id) ON DELETE SET NULL
  );