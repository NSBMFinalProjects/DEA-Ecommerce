package nsbm.dea.admin.model;

import java.sql.Timestamp;

public class Collection {
  private int id;
  private String name;
  private String slug;
  private String description;
  private String createdBy;
  private Timestamp created;
  private Timestamp modified;
  private String[] photoUrls;
  private Product[] products;

  public Collection() {
  }

  public Collection(
      int id,
      String name,
      String slug,
      String description,
      String createdBy,
      String[] photoUrls,
      Timestamp created,
      Timestamp modified) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.description = description;
    this.createdBy = createdBy;
    this.photoUrls = photoUrls;
    this.created = created;
    this.modified = modified;
  }

  public int getId() {
    return id;

  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
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

  public String[] getPhotoUrls() {
    return photoUrls;
  }

  public void setPhotoUrls(String[] photoUrls) {
    this.photoUrls = photoUrls;
  }

  public Product[] getProducts() {
    return products;
  }

  public void setProducts(Product[] products) {
    this.products = products;
  }

}
