CREATE EXTENSION IF NOT EXISTS "ulid";
CREATE SCHEMA IF NOT EXISTS "dea";

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
  IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
    NEW.modified = now();
    RETURN NEW;
  ELSE
    RETURN OLD;
  END IF;
END;
$$ language 'plpgsql';

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
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  description TEXT,
  created TIMESTAMP default now(), 
  modified TIMESTAMP default now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id),
  CONSTRAINT fk_collections_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_collections_slug ON dea.collections (slug);
CREATE INDEX IF NOT EXISTS idx_collections_created_by ON dea.collections (created_by);

DROP TRIGGER IF EXISTS update_collections_modtime on dea.collections;
CREATE TRIGGER update_collections_modtime BEFORE UPDATE ON dea.collections FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

CREATE TABLE IF NOT EXISTS dea.tags (
  id SERIAL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  created TIMESTAMP default now(), 
  modified TIMESTAMP default now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id),
  CONSTRAINT fk_tags_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_tags_slug ON dea.tags (slug);
CREATE INDEX IF NOT EXISTS idx_tags_created_by ON dea.tags (created_by);

DROP TRIGGER IF EXISTS update_tags_modtime on dea.tags;
CREATE TRIGGER update_tags_modtime BEFORE UPDATE ON dea.tags FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

CREATE TABLE IF NOT EXISTS dea.products (
  id SERIAL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  description TEXT,
  created TIMESTAMP default now(),
  modified TIMESTAMP default now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id),
  CONSTRAINT fk_products_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_products_slug ON dea.products (slug);
CREATE INDEX IF NOT EXISTS idx_products_created_by ON dea.products (created_by);

DROP TRIGGER IF EXISTS update_products_modtime on dea.products;
CREATE TRIGGER update_products_modtime BEFORE UPDATE ON dea.products FOR EACH ROW EXECUTE PROCEDURE update_modified_column();


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
CREATE INDEX IF NOT EXISTS idx_product_collections_product_id ON dea.product_collections (product_id);
CREATE INDEX IF NOT EXISTS idx_product_collections_collection_id ON dea.product_collections (collection_id);

CREATE TABLE IF NOT EXISTS dea.product_tags (
  product_id INT NOT NULL,
  tag_id INT NOT NULL,

  PRIMARY KEY(product_id, tag_id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id),
  CONSTRAINT fk_product_tags_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(tag_id) REFERENCES dea.tags(id),
  CONSTRAINT fk_product_tags_tag_id FOREIGN KEY (tag_id)
    REFERENCES dea.tags(id)
);
CREATE INDEX IF NOT EXISTS idx_product_tags_product_id ON dea.product_tags (product_id);
CREATE INDEX IF NOT EXISTS idx_product_tags_tag_id ON dea.product_tags (tag_id);

CREATE TABLE IF NOT EXISTS dea.categories (
  id SERIAL,
  created_by ulid NOT NULL,
  product_id INT NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  created TIMESTAMP default now(),
  modified TIMESTAMP default now(),

  PRIMARY KEY(id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id),
  CONSTRAINT fk_categories_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id),
  CONSTRAINT fk_categories_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_categories_slug ON dea.categories (slug);
CREATE INDEX IF NOT EXISTS idx_categories_product_id ON dea.categories (product_id);
CREATE INDEX IF NOT EXISTS idx_categories_created_by ON dea.categories (created_by);

DROP TRIGGER IF EXISTS update_categories_modtime on dea.categories;
CREATE TRIGGER update_categories_modtime BEFORE UPDATE ON dea.categories FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

CREATE TABLE IF NOT EXISTS dea.colors (
  id SERIAL,
  category_id INT NOT NULL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100) NOT null,
  hex VARCHAR(100) NOT NULL,
  qty INT NOT NULL,
  created TIMESTAMP default now(),
  modified TIMESTAMP default now(),

  PRIMARY KEY(id),

  FOREIGN KEY(category_id) REFERENCES dea.categories(id),
  CONSTRAINT fk_colors_category_id FOREIGN KEY (category_id)
    REFERENCES dea.categories(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id),
  CONSTRAINT fk_colors_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_colors_slug ON dea.colors (slug);
CREATE INDEX IF NOT EXISTS idx_colors_category_id ON dea.colors (category_id);
CREATE INDEX IF NOT EXISTS idx_colors_created_by ON dea.colors (created_by);

DROP TRIGGER IF EXISTS update_colors_modtime on dea.colors;
CREATE TRIGGER update_colors_modtime BEFORE UPDATE ON dea.colors FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
