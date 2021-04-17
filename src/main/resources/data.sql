CREATE SCHEMA  IF NOT EXISTS WEATHERAPIDB;

use weatherapidb;
--drop table if exists userdetails;


CREATE TABLE if not exists userdetails (
    id serial PRIMARY KEY,
    username varchar(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    active boolean NOT NULL,
    roles TEXT NOT NULL,
    birthdate date 
);

--truncate table userdetails;
delete from userdetails where username in ('admin','testuser1', 'testuser2');

INSERT INTO userdetails (username,password,active,roles,birthdate) VALUES
 	('testuser1', 'pass1', true, 'ROLE_USER','1994-09-29'),
	('testuser2', 'pass2', true, 'ROLE_USER','1994-09-29'),
	('admin', 'admin', true, 'ROLE_ADMIN','1994-09-29');