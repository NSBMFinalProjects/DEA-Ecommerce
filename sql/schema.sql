CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS "dea";

CREATE TABLE IF NOT EXISTS dea.users (
  ID uuid DEFAULT uuid_generate_v4(),
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE INDEX IF NOT EXISTS idx_user_email ON dea.users (email);
