CREATE TABLE `batch_user`
(
    `id`       int unsigned NOT NULL AUTO_INCREMENT,
    `birthday` timestamp                                                    DEFAULT NULL COMMENT '生日',
    `name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
    `age`      int                                                          DEFAULT NULL COMMENT '年龄',
    `addr`     varchar(64)  not null COMMENT '地址',
    PRIMARY KEY (`id`)
)
