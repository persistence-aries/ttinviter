use springbootmybatis; 

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `age` smallint DEFAULT NULL COMMENT '年齡',
  `account` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_by` varchar(50) NOT NULL COMMENT '建立人員',
  `create_dt` datetime NOT NULL COMMENT '建立時間',
  `modify_by` varchar(50) DEFAULT NULL COMMENT '修改人員',
  `modify_dt` datetime DEFAULT NULL COMMENT '修改時間',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
