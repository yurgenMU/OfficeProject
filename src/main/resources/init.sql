CREATE DATABASE IF NOT EXISTS OFFICETASKDATABASE;
USE OFFICETASKDATABASE;

CREATE TABLE IF NOT EXISTS Rooms (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS Users (
  id        INT          NOT NULL AUTO_INCREMENT,
  login     VARCHAR(100) NOT NULL UNIQUE,
  password  VARCHAR(100) NOT NULL,
  role      VARCHAR(45)  NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name  VARCHAR(100) NOT NULL,
  email     VARCHAR(100) NOT NULL,
  room_id    INT,
  PRIMARY KEY (Id),
  FOREIGN KEY (room_Id) REFERENCES Rooms (Id)
);

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
  id   INT NOT NULL ,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (Id)
);


CREATE TABLE IF NOT EXISTS ProjectsUsers (
  project_id INT NOT NULL,
  user_id    INT NOT NULL,
  FOREIGN KEY (project_id) REFERENCES Projects (Id),
  FOREIGN KEY (user_id) REFERENCES Users (Id)
);

# CREATE TABLE IF NOT EXISTS RoomsUsers (
#   room_id INT NOT NULL,
#   user_id INT NOT NULL,
#   FOREIGN KEY (room_id) REFERENCES Rooms (Id),
#   FOREIGN KEY (user_id) REFERENCES Users (Id)
# );

CREATE TABLE IF NOT EXISTS Dates (
  id   INT  NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS UsersDates (
  date_id INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (id),
  FOREIGN KEY (date_id) REFERENCES Dates (id)
);


# INSERT INTO roles VALUES (1, 'ROLE_USER');
# INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
#
# INSERT INTO user_roles VALUES (1, 2);
INSERT INTO Users VALUES (0, 'Admin')