CREATE TABLE `t_author` (
  `id` bigint(32) NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `nick_name` varchar(32) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;