CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS "dea";

CREATE TABLE IF NOT EXISTS dea.users (
  id uuid DEFAULT uuid_generate_v4(),
  email VARCHAR(255) NOT NULL UNIQUE,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,

  PRIMARY KEY(id)
);
CREATE INDEX IF NOT EXISTS idx_user_email ON dea.users (email);
CREATE INDEX IF NOT EXISTS idx_user_username ON dea.users (username);

CREATE TABLE IF NOT EXISTS dea.delivery_details (
  ID uuid DEFAULT uuid_generate_v4(),
  UserID uuid NOT NULL UNIQUE,
  Address VARCHAR(255) NOT NULL,
  City VARCHAR(100) NOT NULL,
  Province VARCHAR(100) NOT NULL,
  PostalCode VARCHAR(100) NOT NULL,

  PRIMARY KEY(ID),
  FOREIGN KEY(UserID) REFERENCES dea.users(ID),
  CONSTRAINT fk_delivery_details_users FOREIGN KEY (UserID)
    REFERENCES dea.users(ID)
);

