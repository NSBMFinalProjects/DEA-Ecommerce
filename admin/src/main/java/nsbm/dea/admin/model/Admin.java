package nsbm.dea.admin.model;

public class Admin {
  private String id;
  private String username;
  private String email;
  private String name;
  private String password;
  private String photoURL;

  public Admin() {

  }

  public Admin(
      String id,
      String username,
      String email,
      String name,
      String password,
      String photoURL) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.name = name;
    this.password = password;
    this.photoURL = photoURL;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
