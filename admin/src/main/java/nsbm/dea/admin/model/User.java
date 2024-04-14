package nsbm.dea.admin.model;

public class User {
  private String id;
  private String email;
  private String username;
  private String name;
  private String password;
  private String photoURL;

  public User() {
  }

  public User(
      String id,
      String email,
      String username,
      String name,
      String password,
      String photo_url) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.name = name;
    this.password = password;
    this.photoURL = photo_url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhotoURL() {
    return photoURL;
  }

  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }
}
