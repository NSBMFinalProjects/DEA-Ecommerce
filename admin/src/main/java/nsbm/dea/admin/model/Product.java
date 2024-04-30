package nsbm.dea.admin.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
  private int id;
  private String createdBy;
  private String slug;
  private String name;
  private BigDecimal price;
  private String[] photoUrls;
  private String description;
  private Timestamp created;
  private Timestamp modified;
  private Category[] categories;
  private Collection[] collections;

  public Product() {
  }

  public Product(int id, String createdBy, String slug, String name, BigDecimal price, String[] photoUrls,
      String description) {
    this.id = id;
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.price = price;
    this.photoUrls = photoUrls;
    this.description = description;
  }

  public Product(int id, String createdBy, String slug, String name, BigDecimal price, String[] photoUrls,
      String description,
      Category[] categories) {
    this.id = id;
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.price = price;
    this.photoUrls = photoUrls;
    this.description = description;
    this.categories = categories;
  }

  public Product(int id, String createdBy, String slug, String name, BigDecimal price, String[] photoUrls,
      String description,
      Timestamp created,
      Timestamp modified) {
    this.id = id;
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.price = price;
    this.photoUrls = photoUrls;
    this.description = description;
    this.created = created;
    this.modified = modified;
  }

  public Product(int id, String createdBy, String slug, String name, BigDecimal price, String[] photoUrls,
      String description,
      Timestamp created,
      Timestamp modified, Category[] categories) {
    this.id = id;
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.price = price;
    this.photoUrls = photoUrls;
    this.description = description;
    this.created = created;
    this.modified = modified;
    this.categories = categories;
  }
  public Product(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String[] getPhotoUrls() {
    return photoUrls;
  }

  public void setPhotoUrls(String[] photoUrls) {
    this.photoUrls = photoUrls;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public Timestamp getModified() {
    return modified;
  }

  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  public Category[] getCategories() {
    return categories;
  }

  public void setCategories(Category[] categories) {
    this.categories = categories;
  }

  public Collection[] getCollections() {
    return collections;
  }

  public void setCollections(Collection[] collections) {
    this.collections = collections;
  }
}
