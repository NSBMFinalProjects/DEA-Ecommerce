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
    ID uuid DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_no VARCHAR(20),
    photo VARCHAR(255),
    PRIMARY KEY (admin_id)
);

CREATE INDEX IF NOT EXISTS idx_admin_email ON dea.admin(email);


CREATE TABLE IF NOT EXISTS dea.products(
  ID INT NOT NULL AUTO_INCREMENT,
  slug VARCHAR(50) UNIQUE,
  name VARCHAR(255) NOT NULL,
  price decimal(10,2) NOT NULL,
  category_id INT,
  subcategory_id INT,
  size ENUM('S','M','L','XL'),
  description TEXT,
  quantity INT,
  admin_id INT,
  PRIMARY KEY (ID),
  FOREIGN KEY (category_id) REFERENCES dea.category(ID),
  FOREIGN KEY (subcategory_id) REFERENCES dea.subcategory(ID),
  FOREIGN key (admin_id) REFERENCES dea.admin(ID)
);
