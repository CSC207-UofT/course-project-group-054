DROP DATABASE compound_db;
DROP USER group54;

CREATE USER group54 WITH PASSWORD 'password';
-- CREATE DATABASE compound_db WITH TEMPLATE=template0 OWNER=group54;
CREATE DATABASE compound_db OWNER=group54;
\CONNECT compound_db;
ALTER DEFAULT PRIVILEGES GRANT ALL ON tables TO group54;
ALTER DEFAULT PRIVILEGES GRANT ALL ON sequences TO group54;


CREATE TABLE users(
    uuid INTEGER PRIMARY KEY NOT NULL ,
    name VARCHAR(40) NOT NULL,
    email VARCHAR(50) NOT NULL,
    username VARCHAR(40),
    password TEXT NOT NULL,
    expenses integer[],
    balance NUMERIC(20, 2)
);

CREATE TABLE expenses(
    euid INTEGER PRIMARY KEY NOT NULL,
    title VARCHAR(50) NOT NULL,
    people INTEGER[] NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    creator_uuid INTEGER NOT NULL
);

ALTER TABLE expenses ADD CONSTRAINT expenses_foreign_key
FOREIGN KEY (creator_uuid) REFERENCES users(uuid);

CREATE TABLE groups(
    guid INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    members INTEGER[] NOT NULL,
    expenses INTEGER[],
    budgets INTEGER[],
    description TEXT
);

CREATE SEQUENCE users_seq increment 1 start 1;
CREATE SEQUENCE expenses_seq increment 1 start 1000;
CREATE SEQUENCE groups_seq increment 1 start 1;


CREATE TABLE budget(

);

CREATE TABLE items(

);