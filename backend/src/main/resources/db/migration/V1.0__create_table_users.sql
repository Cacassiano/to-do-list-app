CREATE TABLE IF NOT EXISTS users (
    id varchar(36) UNIQUE NOT NULL PRIMARY KEY,
    email varchar(255) UNIQUE NOT NULL,
    password text NOT NULL,
    username varchar(255) NOT NULL 
);