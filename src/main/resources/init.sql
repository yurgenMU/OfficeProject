CREATE DATABASE IF NOT EXISTS vityaAK;

USE vityaAK;

CREATE TABLE IF NOT EXISTS vityaAK.users (
  `id`          INT         NOT NULL AUTO_INCREMENT,
  `firstname`   VARCHAR(45) NOT NULL,
  `lastname`    VARCHAR(45) NOT NULL,
  `speciality`  VARCHAR(45),
  `email`       VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
  COMMENT ''
);
