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


CREATE TABLE IF NOT EXISTS dea.admins(
    id ulid NOT NULL DEFAULT gen_ulid(),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_admins_email ON dea.admins (email);
CREATE INDEX IF NOT EXISTS idx_admins_username ON dea.admins (username);

CREATE TABLE IF NOT EXISTS dea.collections (
  id SERIAL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  description TEXT,

  PRIMARY KEY(id)
);
CREATE INDEX IF NOT EXISTS idx_collections_slug ON dea.collections (slug);

CREATE TABLE IF NOT EXISTS dea.products (
  id SERIAL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  description TEXT,

  PRIMARY KEY(id)
);
CREATE INDEX IF NOT EXISTS idx_products_slug ON dea.products (slug);


CREATE TABLE IF NOT EXISTS dea.product_collections (
  product_id INT NOT NULL,
  collection_id INT NOT NULL,

  PRIMARY KEY(product_id, collection_id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id),
  CONSTRAINT fk_product_collections_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(collection_id) REFERENCES dea.collections(id),
  CONSTRAINT fk_product_collections_collection_id FOREIGN KEY (collection_id)
    REFERENCES dea.collections(id)
);
CREATE INDEX IF NOT EXISTS idx_products_collections_product_id ON dea.product_collections (product_id);
CREATE INDEX IF NOT EXISTS idx_products_collections_collection_id ON dea.product_collections (collection_id);
