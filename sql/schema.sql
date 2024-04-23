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

CREATE OR REPLACE FUNCTION update_photourl()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.photo_url IS NULL THEN
    NEW.photo_url = 'https://api.dicebear.com/8.x/initials/svg?seed=' || NEW.name;
  END IF;
  RETURN NEW;
END;
$$ language 'plpgsql';

CREATE OR REPLACE FUNCTION generate_slug()
RETURNS TRIGGER AS $$
DECLARE
  slug VARCHAR;
  name VARCHAR;
BEGIN
  IF NEW.name IS NULL THEN
    RETURN NULL;
  END IF;
  name := NEW.name;

  -- Remove leading and traling whitespaces
  slug := TRIM(name);

  -- Replace spaces with dashes
  slug := REPLACE(slug, ' ', '-');

  -- Convert to lower case
  slug := LOWER(slug);

  -- Remove non-alphanumeric characters except dashes
  slug := REGEXP_REPLACE(slug, '[^a-z0-9-]', '', 'g');

  -- Merge multiple dashes into one
  slug := REPLACE(slug, '-+', '-');

  -- Remove leading and trailing dashes
  slug := REGEXP_REPLACE(slug, '^(-+)|(-+$)', '', 'g');

  NEW.slug = slug;
  RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TABLE IF NOT EXISTS dea.users (
  id ulid NOT NULL DEFAULT gen_ulid(),
  email VARCHAR(255) NOT NULL UNIQUE,
  username VARCHAR(255) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  photo_url VARCHAR(255) NOT NULL,

  PRIMARY KEY(id)
);
CREATE INDEX IF NOT EXISTS idx_user_email ON dea.users (email);
CREATE INDEX IF NOT EXISTS idx_user_username ON dea.users (username);

DROP TRIGGER IF EXISTS update_user_photo_url on dea.users;
CREATE TRIGGER update_user_photo_url BEFORE INSERT ON dea.users FOR EACH ROW EXECUTE PROCEDURE update_photourl();

CREATE TABLE IF NOT EXISTS dea.delivery_details (
  id ulid NOT NULL DEFAULT gen_ulid(),
  user_id ulid NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  province VARCHAR(100) NOT NULL,
  postal_code VARCHAR(100) NOT NULL,

  PRIMARY KEY(id),
  FOREIGN KEY(user_id) REFERENCES dea.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_delivery_details_users FOREIGN KEY (user_id)
    REFERENCES dea.users(id)
);
CREATE INDEX IF NOT EXISTS idx_delivery_details_user_id ON dea.delivery_details (user_id);


CREATE TABLE IF NOT EXISTS dea.admins(
    id ulid NOT NULL DEFAULT gen_ulid(),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_admins_email ON dea.admins (email);
CREATE INDEX IF NOT EXISTS idx_admins_username ON dea.admins (username);

DROP TRIGGER IF EXISTS update_admin_photo_url on dea.admins;
CREATE TRIGGER update_admin_photo_url BEFORE INSERT ON dea.admins FOR EACH ROW EXECUTE PROCEDURE update_photourl();

CREATE TABLE IF NOT EXISTS dea.collections (
  id SERIAL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  description TEXT,
  created TIMESTAMP DEFAULT now(), 
  modified TIMESTAMP DEFAULT now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_collections_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_collections_slug ON dea.collections (slug);
CREATE INDEX IF NOT EXISTS idx_collections_created_by ON dea.collections (created_by);

DROP TRIGGER IF EXISTS update_collections_modtime on dea.collections;
CREATE TRIGGER update_collections_modtime BEFORE UPDATE ON dea.collections FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

DROP TRIGGER IF EXISTS generate_collection_slug on dea.collections;
CREATE TRIGGER generate_collection_slug BEFORE INSERT ON dea.collections FOR EACH ROW EXECUTE PROCEDURE generate_slug();

CREATE TABLE IF NOT EXISTS dea.tags (
  id SERIAL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  created TIMESTAMP DEFAULT now(), 
  modified TIMESTAMP DEFAULT now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_tags_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_tags_slug ON dea.tags (slug);
CREATE INDEX IF NOT EXISTS idx_tags_created_by ON dea.tags (created_by);

DROP TRIGGER IF EXISTS update_tags_modtime on dea.tags;
CREATE TRIGGER update_tags_modtime BEFORE UPDATE ON dea.tags FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

DROP TRIGGER IF EXISTS generate_tag_slug on dea.tags;
CREATE TRIGGER generate_tag_slug BEFORE INSERT OR UPDATE ON dea.tags FOR EACH ROW EXECUTE PROCEDURE generate_slug();

CREATE TABLE IF NOT EXISTS dea.products (
  id SERIAL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  photo_urls TEXT[3] NOT NULL,
  price DECIMAL NOT NULL,
  description TEXT,
  created TIMESTAMP DEFAULT now(),
  modified TIMESTAMP DEFAULT now(),

  PRIMARY KEY(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_products_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_products_slug ON dea.products (slug);
CREATE INDEX IF NOT EXISTS idx_products_created_by ON dea.products (created_by);

DROP TRIGGER IF EXISTS update_products_modtime on dea.products;
CREATE TRIGGER update_products_modtime BEFORE UPDATE ON dea.products FOR EACH ROW EXECUTE PROCEDURE update_modified_column();


DROP TRIGGER IF EXISTS generate_product_slug on dea.products;
CREATE TRIGGER generate_product_slug BEFORE INSERT ON dea.products FOR EACH ROW EXECUTE PROCEDURE generate_slug();


CREATE TABLE IF NOT EXISTS dea.product_collections (
  product_id INT NOT NULL,
  collection_id INT NOT NULL,

  PRIMARY KEY(product_id, collection_id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_product_collections_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(collection_id) REFERENCES dea.collections(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_product_collections_collection_id FOREIGN KEY (collection_id)
    REFERENCES dea.collections(id)
);
CREATE INDEX IF NOT EXISTS idx_product_collections_product_id ON dea.product_collections (product_id);
CREATE INDEX IF NOT EXISTS idx_product_collections_collection_id ON dea.product_collections (collection_id);

CREATE TABLE IF NOT EXISTS dea.product_tags (
  product_id INT NOT NULL,
  tag_id INT NOT NULL,

  PRIMARY KEY(product_id, tag_id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_product_tags_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(tag_id) REFERENCES dea.tags(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_product_tags_tag_id FOREIGN KEY (tag_id)
    REFERENCES dea.tags(id)
);
CREATE INDEX IF NOT EXISTS idx_product_tags_product_id ON dea.product_tags (product_id);
CREATE INDEX IF NOT EXISTS idx_product_tags_tag_id ON dea.product_tags (tag_id);

CREATE TABLE IF NOT EXISTS dea.categories (
  id SERIAL,
  created_by ulid NOT NULL,
  product_id INT NOT NULL,
  slug VARCHAR(100) NOT NULL,
  name VARCHAR(100) NOT NULL,
  created TIMESTAMP DEFAULT now(),
  modified TIMESTAMP DEFAULT now(),

  PRIMARY KEY(id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_categories_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_categories_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_categories_product_id ON dea.categories (product_id);
CREATE INDEX IF NOT EXISTS idx_categories_created_by ON dea.categories (created_by);

DROP TRIGGER IF EXISTS update_categories_modtime on dea.categories;
CREATE TRIGGER update_categories_modtime BEFORE UPDATE ON dea.categories FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

DROP TRIGGER IF EXISTS generate_categories_slug on dea.categories;
CREATE TRIGGER generate_categories_slug BEFORE INSERT ON dea.categories FOR EACH ROW EXECUTE PROCEDURE generate_slug();

CREATE TABLE IF NOT EXISTS dea.colors (
  id SERIAL,
  category_id INT NOT NULL,
  created_by ulid NOT NULL,
  slug VARCHAR(100) NOT NULL,
  name VARCHAR(100) NOT null,
  hex VARCHAR(100) NOT NULL,
  qty INT NOT NULL,
  created TIMESTAMP DEFAULT now(),
  modified TIMESTAMP DEFAULT now(),

  PRIMARY KEY(id),

  FOREIGN KEY(category_id) REFERENCES dea.categories(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_colors_category_id FOREIGN KEY (category_id)
    REFERENCES dea.categories(id),

  FOREIGN KEY(created_by) REFERENCES dea.admins(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_colors_created_by FOREIGN KEY (created_by)
    REFERENCES dea.admins(id)
);
CREATE INDEX IF NOT EXISTS idx_colors_category_id ON dea.colors (category_id);
CREATE INDEX IF NOT EXISTS idx_colors_created_by ON dea.colors (created_by);

DROP TRIGGER IF EXISTS update_colors_modtime on dea.colors;
CREATE TRIGGER update_colors_modtime BEFORE UPDATE ON dea.colors FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

DROP TRIGGER IF EXISTS generate_colors_slug on dea.colors;
CREATE TRIGGER generate_colors_slug BEFORE INSERT ON dea.colors FOR EACH ROW EXECUTE PROCEDURE generate_slug();

DROP TYPE IF EXISTS dea.order_status;
CREATE TYPE dea.order_status AS ENUM('processing', 'delivering', 'delivered');

CREATE TABLE IF NOT EXISTS dea.orders (
  id SERIAL,
  ordered_by ulid NOT NULL,
  delivery_address ulid NOT NULL,
  created TIMESTAMP DEFAULT now(),
  status dea.ORDER_STATUS DEFAULT 'processing'::dea.ORDER_STATUS,
  qty INT NOT NULL,
  total DECIMAL NOT NULL,

  PRIMARY KEY(id),

  FOREIGN KEY(ordered_by) REFERENCES dea.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_orders_ordered_by FOREIGN KEY (ordered_by)
    REFERENCES dea.users(id),

  FOREIGN KEY(delivery_address) REFERENCES dea.delivery_details(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_orders_delivery_address FOREIGN KEY (delivery_address)
    REFERENCES dea.delivery_details(id)
);
CREATE INDEX IF NOT EXISTS idx_orders_ordered_by ON dea.orders (ordered_by);
CREATE INDEX IF NOT EXISTS idx_orders_delivery_address ON dea.orders (delivery_address);
CREATE INDEX IF NOT EXISTS idx_orders_status ON dea.orders (status);

CREATE TABLE IF NOT EXISTS dea.user_orders (
  id SERIAL,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  category_id INT NOT NULL,
  color_id INT NOT NULL,
  price DECIMAL NOT NULL,
  qty INT NOT NULL,

  PRIMARY KEY(id),

  FOREIGN KEY(order_id) REFERENCES dea.orders(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_user_orders_order_id FOREIGN KEY (order_id)
    REFERENCES dea.orders(id),

  FOREIGN KEY(product_id) REFERENCES dea.products(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_user_orders_product_id FOREIGN KEY (product_id)
    REFERENCES dea.products(id),

  FOREIGN KEY(category_id) REFERENCES dea.categories(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_user_orders_category_id FOREIGN KEY (category_id)
    REFERENCES dea.categories(id),

  FOREIGN KEY(color_id) REFERENCES dea.colors(id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_user_orders_color_id FOREIGN KEY (color_id)
    REFERENCES dea.colors(id)
);
CREATE INDEX IF NOT EXISTS idx_user_orders_order_id ON dea.user_orders (order_id);
CREATE INDEX IF NOT EXISTS idx_user_orders_product_id ON dea.user_orders (product_id);
CREATE INDEX IF NOT EXISTS idx_user_orders_category_id ON dea.user_orders (category_id);
CREATE INDEX IF NOT EXISTS idx_user_orders_color_id ON dea.user_orders (color_id);
