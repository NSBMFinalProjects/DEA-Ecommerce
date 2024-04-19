package nsbm.dea.admin.model;

import java.sql.Timestamp;

public class Tag {
  private int id;
  private String createdBy;
  private String slug;
  private String name;
  private Timestamp created;
  private Timestamp modified;

  public Tag() {

  }

  public Tag(int id, String createdBy, String slug, String name, Timestamp created, Timestamp modified) {
    this.id = id;
    this.createdBy = createdBy;
    this.slug = slug;
    this.name = name;
    this.created = created;
    this.modified = modified;
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
}
