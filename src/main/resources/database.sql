

-- Initialize database tables:

use kindergarten_db;
CREATE TABLE IF NOT EXISTS feedback (
    id VARCHAR(36) PRIMARY KEY,
    content TEXT NOT NULL,
    submission_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `groups` (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL
);


CREATE TABLE IF NOT EXISTS staff (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    corresponding_group INT,
    description VARCHAR(65) NOT NULL,
    phone_number VARCHAR(45) NOT NULL,
    FOREIGN KEY (corresponding_group) REFERENCES `groups`(id) ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS parents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(65) NOT NULL,
    last_name VARCHAR(65) NOT NULL,
    personal_identity_code VARCHAR(45) NOT NULL,
    phone_number VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(65) NOT NULL,
    last_name VARCHAR(65) NOT NULL,
    age INT NOT NULL,
    group_id INT NOT NULL,
    parent_id_1 INT NOT NULL,
    parent_id_2 INT,
    FOREIGN KEY (group_id) REFERENCES `groups`(id) ON UPDATE CASCADE,
    FOREIGN KEY (parent_id_1) REFERENCES parents(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (parent_id_2) REFERENCES parents(id) ON UPDATE CASCADE ON DELETE SET NULL
);

