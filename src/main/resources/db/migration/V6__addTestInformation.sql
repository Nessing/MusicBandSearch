INSERT INTO music_style_table (title) VALUES ('DEFAULT');
INSERT INTO music_instrument_table (title) VALUES ('DEFAULT');
INSERT INTO musician_style_table (id_user,id_style) VALUES ((SELECT ut.id FROM user_table AS ut WHERE ut.email = 'test@mail.ru'),(SELECT ut.id FROM music_style_table AS ut WHERE ut.title = 'DEFAULT'));
INSERT INTO musician_instrument_table (id_user,id_instrument) VALUES ((SELECT ut.id FROM user_table AS ut WHERE ut.email = 'test@mail.ru'),(SELECT ut.id FROM music_instrument_table AS ut WHERE ut.title = 'DEFAULT'));
INSERT INTO group_table (title,id_style,price,program_duration) VALUES ('DEFAULT',(SELECT ut.id FROM music_style_table AS ut WHERE ut.title = 'DEFAULT'),1000.00,2.00);
INSERT INTO musician_group_table (id_user,id_group) VALUES ((SELECT ut.id FROM user_table AS ut WHERE ut.email = 'test@mail.ru'),(SELECT ut.id FROM group_table AS ut WHERE ut.title = 'DEFAULT'));