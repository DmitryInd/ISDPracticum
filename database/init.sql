CREATE USER IF NOT EXISTS 'web'@'%';
SET PASSWORD FOR 'web'@'%' = "root";

GRANT ALL PRIVILEGES ON *.* TO 'web'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS userbase;

CREATE TABLE IF NOT EXISTS userbase.files (
    filename VARCHAR(45) NOT NULL ,
    file_content LONGBLOB NOT NULL ,
    PRIMARY KEY (filename));

CREATE TABLE IF NOT EXISTS userbase.users (
    username VARCHAR(45) NOT NULL ,
    password VARCHAR(45) NOT NULL ,
    enabled TINYINT NOT NULL DEFAULT 1 ,
    PRIMARY KEY (username));

CREATE TABLE IF NOT EXISTS userbase.user_roles (
    user_role_id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_username_role (role,username),
    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE ON UPDATE CASCADE);

REPLACE INTO userbase.users(username,password,enabled)
VALUES ('user','userPass', true);
REPLACE INTO userbase.users(username,password,enabled)
VALUES ('dmitry','dmitryPass', true);

REPLACE INTO userbase.user_roles (username, role)
VALUES ('dmitry', 'ROLE_USER');
REPLACE INTO userbase.user_roles (username, role)
VALUES ('dmitry', 'ROLE_ADMIN');
REPLACE INTO userbase.user_roles (username, role)
VALUES ('user', 'ROLE_USER');