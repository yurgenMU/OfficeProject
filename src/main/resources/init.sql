CREATE DATABASE IF NOT EXISTS OFFICETASKDATABASE;
USE OFFICETASKDATABASE;

CREATE TABLE IF NOT EXISTS Rooms (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (Id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Users (
  id         INT          NOT NULL AUTO_INCREMENT,
  login      VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  role       VARCHAR(45)  NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name  VARCHAR(100) NOT NULL,
  email      VARCHAR(100) NOT NULL,
  room_id    INT,
  PRIMARY KEY (Id),
  FOREIGN KEY (room_Id) REFERENCES Rooms (Id)
)
  ENGINE = InnoDB;

# CREATE TABLE roles (
#   id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#   name VARCHAR(100) NOT NULL
# );


# -- Table for mapping user and roles: user_roles
# CREATE TABLE user_roles (
#   user_id INT NOT NULL,
#   role_id INT NOT NULL,
#
#   FOREIGN KEY (user_id) REFERENCES users (id),
#   FOREIGN KEY (role_id) REFERENCES roles (id),
#
#   UNIQUE (user_id, role_id)
# );


CREATE TABLE IF NOT EXISTS Projects (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (Id)
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS projects_users (
  project_id INT NOT NULL,
  user_id    INT NOT NULL,
  FOREIGN KEY (project_id) REFERENCES Projects (Id),
  FOREIGN KEY (user_id) REFERENCES Users (Id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Dates (
  id   INT  NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL UNIQUE ,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS users_dates (
  date_id INT NOT NULL,
  user_id INT NOT NULL,


  PRIMARY KEY (`date_id`, `user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (date_id) REFERENCES Dates (id)
)
  ENGINE = InnoDB;

INSERT INTO Users VALUES (0, 'administrator', '$2a$11$pcMQeOxvn7Scj3lt00B84.CYRvnxpVSL/JpvPdG9jKLJIFmsNARDO',
                          'ROLE_ADMIN', 'admin', 'admin', 'admin@admin.com', NULL);