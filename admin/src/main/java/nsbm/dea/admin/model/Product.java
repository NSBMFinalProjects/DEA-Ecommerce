package nsbm.dea.admin.model;

import java.sql.Timestamp;

public class Product {
  private int id;
  private String createdBy;
  private String slug;
  private String name;
  private String[] photoUrls;
  private String description;
  private Timestamp created;
  private Timestamp modified;
  private Category[] categories;

  public Product() {
  }

  public Product(int id, String createdBy, String slug, String name, String[] photoUrls, String description) {
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.photoUrls = photoUrls;
    this.description = description;
  }

  public Product(int id, String createdBy, String slug, String name, String[] photoUrls, String description,
      Category[] categories) {
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.photoUrls = photoUrls;
    this.description = description;
    this.categories = categories;
  }

  public Product(int id, String createdBy, String slug, String name, String[] photoUrls, String description,
      Timestamp created,
      Timestamp modified) {
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.photoUrls = photoUrls;
    this.description = description;
    this.created = created;
    this.modified = modified;
  }

  public Product(int id, String createdBy, String slug, String name, String[] photoUrls, String description,
      Timestamp created,
      Timestamp modified, Category[] categories) {
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.photoUrls = photoUrls;
    this.description = description;
    this.created = created;
    this.modified = modified;
    this.categories = categories;
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
}
