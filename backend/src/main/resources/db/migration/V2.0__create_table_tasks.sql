CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    status varchar(15) NOT NULL,
    description text,
    title varchar(127) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    owner_id varchar(36) NOT NULL
);

ALTER TABLE tasks
ADD CONSTRAINT FK_owner
FOREIGN KEY (owner_id)
REFERENCES users (id)
ON DELETE CASCADE;