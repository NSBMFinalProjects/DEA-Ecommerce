CREATE EXTENSION IF NOT EXISTS "ulid";
CREATE SCHEMA IF NOT EXISTS "dea";

CREATE TABLE IF NOT EXISTS dea.users (
  id ulid NOT NULL DEFAULT gen_ulid(),
  email VARCHAR(255) NOT NULL UNIQUE,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  photo_url VARCHAR(255) NOT NULL,

  PRIMARY KEY(id)
);
CREATE INDEX IF NOT EXISTS idx_user_email ON dea.users (email);
CREATE INDEX IF NOT EXISTS idx_user_username ON dea.users (username);

CREATE TABLE IF NOT EXISTS dea.delivery_details (
  id ulid NOT NULL DEFAULT gen_ulid(),
  user_id ulid NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  province VARCHAR(100) NOT NULL,
  postal_code VARCHAR(100) NOT NULL,

  PRIMARY KEY(id),
  FOREIGN KEY(user_id) REFERENCES dea.users(id),
  CONSTRAINT fk_delivery_details_users FOREIGN KEY (user_id)
    REFERENCES dea.users(id)
);
CREATE INDEX IF NOT EXISTS idx_delivery_details_user_id ON dea.delivery_details (user_id);

CREATE INDEX IF NOT EXISTS idx_user_email ON dea.users (email);

CREATE TABLE IF NOT EXISTS dea.category(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) UNIQUE,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS dea.subcategory(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) UNIQUE,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS dea.admin(
    id ulid NOT NULL DEFAULT gen_ulid(),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_admin_email ON dea.admin (email);
CREATE INDEX IF NOT EXISTS idx_admin_username ON dea.admin (username);
