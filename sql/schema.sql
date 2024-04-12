CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
  ID uuid DEFAULT uuid_generate_v4(),
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE INDEX idx_user_email ON users (email);
