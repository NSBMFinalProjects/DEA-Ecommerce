package nsbm.dea.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.errors.DuplicateKeyException;
import nsbm.dea.admin.model.Admin;

public class AdminDAO {
  private int LIMIT;

  public AdminDAO() {

  }

  public AdminDAO(int LIMIT) {
    this.LIMIT = LIMIT;
  }

  private Admin getAdminFromResultSet(ResultSet resultSet) throws SQLException {
    return new Admin(
        resultSet.getString("id"),
        resultSet.getString("username"),
        resultSet.getString("email"),
        resultSet.getString("name"),
        resultSet.getString("password"),
        resultSet.getString("photo_url"));
  }

  public void create(Admin admin) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO dea.admins (email, username, name, password) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, admin.getEmail());
        statement.setString(2, admin.getUsername());
        statement.setString(3, admin.getName());
        statement.setString(4, admin.getPassword());

        statement.executeUpdate();
      }
    }
  }

  public Optional<Admin> getByID(String id) throws SQLException {
    String query = "SELECT * FROM dea.admins WHERE email = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getAdminFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public Optional<Admin> getByUsername(String username) throws SQLException {
    String query = "SELECT * FROM dea.admins WHERE username = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getAdminFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public Optional<Admin> getByEmail(String email) throws SQLException {
    String query = "SELECT * FROM dea.admins WHERE email = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getAdminFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public List<Admin> getAll(int page) throws SQLException {
    String query = "SELECT * FROM dea.admins ORDER BY id OFFSET ? LIMIT ?";
    List<Admin> admins = new ArrayList<>();

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, page * this.LIMIT);
        statement.setInt(2, this.LIMIT);

        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            admins.add(this.getAdminFromResultSet(resultSet));
          }
        }
      }
    }

    return admins;
  }

  public List<Admin> getAll() throws SQLException {
    return this.getAll(1);
  }

  public void updateUsername(String id, String username) throws SQLException, DuplicateKeyException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.admins WHERE username = ? LIMIT 1")) {
        statement.setString(1, username);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            throw new DuplicateKeyException("username is already used");
          }
        }
      }

      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.admins SET username = ? WHERE id = ?")) {
        statement.setString(1, username);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updateEmail(String id, String email) throws SQLException, DuplicateKeyException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.admins WHERE email = ? LIMIT = 1")) {
        statement.setString(1, email);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            throw new DuplicateKeyException("email is already used");
          }
        }
      }

      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.admins SET email = ? WHERE id = ?")) {
        statement.setString(1, email);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updateName(String id, String name) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("UPDATE dea.admins SET name = ? WHERE id = ?")) {
        statement.setString(1, name);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updatePhotoURL(String id, String photoURL) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.admins SET photo_url = ? WHERE id = ?")) {
        statement.setString(1, photoURL);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updatePassword(String id, String password) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.admins SET password = ? WHERE id = ?")) {
        statement.setString(1, password);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public boolean isUsernameAvailable(String username) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.admins WHERE username = ? LIMIT 1")) {
        statement.setString(1, username);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return false;
          }

          return true;
        }
      }
    }
  }

  public boolean isEmailAvailable(String email) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.admins WHERE email = ? LIMIT 1")) {
        statement.setString(1, email);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return false;
          }

          return true;
        }
      }
    }
  }
}
