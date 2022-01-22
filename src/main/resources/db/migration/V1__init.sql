CREATE TABLE `user_table` (
  `id` BINARY(36) NOT NULL DEFAULT (uuid()),
  `email` VARCHAR(56) NOT NULL,
  `telegram_id` VARCHAR(56) NULL,
  `phone` VARCHAR(56) NULL,
  `password` varchar(128) NOT NULL,
  `time_stamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
  );
CREATE TABLE `roles_table` (
  id int not null AUTO_INCREMENT  ,
  title varchar(56) not null,
  `time_stamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (id));
CREATE TABLE user_roles (
  id_user BINARY(36),
  id_role int,
  PRIMARY KEY (`id_user`,`id_role`),
  KEY `fk_role_user_idx` (`id_user`),
  CONSTRAINT `fk_role_user` FOREIGN KEY (`id_role`) REFERENCES `roles_table` (`id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`id_user`) REFERENCES `user_table` (`id`)
  );
