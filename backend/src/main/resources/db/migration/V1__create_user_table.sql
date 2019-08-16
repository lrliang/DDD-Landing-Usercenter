CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(200),
  `password` VARCHAR(200),
  `email` VARCHAR(200),
  `phone` VARCHAR(200),
  `name` VARCHAR (200),
  `city` VARCHAR (200),
  `gender` VARCHAR (200),
  `createTime` TIMESTAMP default current_timestamp,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
