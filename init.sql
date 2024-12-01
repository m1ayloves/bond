CREATE SCHEMA bond;

USE bond;

CREATE TABLE IF NOT EXISTS Users
(
    id       int AUTO_INCREMENT
        PRIMARY KEY,
    username varchar(50)  NOT NULL,
    password varchar(255) NOT NULL,
    tags     text         NULL,
    CONSTRAINT username
        UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS Contacts
(
    no          int AUTO_INCREMENT
        PRIMARY KEY,
    user_id     int                 NOT NULL,
    name        varchar(50)         NOT NULL,
    tag         varchar(50)         NULL,
    sex         enum ('M', 'F', '') NULL,
    birthday    date                NULL,
    phone       varchar(20)         NULL,
    email       varchar(100)        NULL,
    address     varchar(255)        NULL,
    description text                NULL,
    CONSTRAINT contacts_ibfk_1
        FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE INDEX user_id
    ON Contacts (user_id);

