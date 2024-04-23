package nsbm.dea.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.favre.lib.crypto.bcrypt.BCrypt;
import nsbm.dea.web.connections.DB;
import nsbm.dea.web.errors.DuplicateKeyException;
import nsbm.dea.web.models.User;

public class UserDAO {
  private int LIMIT;

  public UserDAO() {

  }

  public UserDAO(int LIMIT) {
    this.LIMIT = LIMIT;
  }

  private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
    return new User(
        resultSet.getString("id"),
        resultSet.getString("username"),
        resultSet.getString("email"),
        resultSet.getString("name"),
        resultSet.getString("photo_url"),
        resultSet.getString("password"));
  }

  public void create(User user) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO dea.users (email, username, name, password) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getName());
        statement.setString(4, user.getPassword());

        statement.executeUpdate();
      }
    }
  }

  public Optional<User> getByID(String id) throws SQLException {
    String query = "SELECT * FROM dea.users WHERE id = cast(? as ulid) LIMIT  1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getUserFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public Optional<User> getByUsername(String username) throws SQLException {
    String query = "SELECT * FROM dea.users WHERE username = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getUserFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public Optional<User> getByEmail(String email) throws SQLException {
    String query = "SELECT * FROM dea.users WHERE email = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getUserFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public List<User> getAll(int page) throws SQLException {
    String query = "SELECT * FROM dea.users ORDER BY id OFFSET ? LIMIT ?";
    List<User> users = new ArrayList<>();

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, page * this.LIMIT);
        statement.setInt(2, this.LIMIT);

        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            users.add(this.getUserFromResultSet(resultSet));
          }
        }
      }
    }

    return users;
  }

  public List<User> getAll() throws SQLException {
    return this.getAll(1);
  }

  public void updateUsername(String id, String username) throws SQLException, DuplicateKeyException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.users WHERE username = ? LIMIT 1")) {
        statement.setString(1, username);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            throw new DuplicateKeyException("username is already used");
          }
        }
      }

      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.users SET username = ? WHERE id = ?")) {
        statement.setString(1, username);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updateEmail(String id, String email) throws SQLException, DuplicateKeyException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.users WHERE email = ? LIMIT = 1")) {
        statement.setString(1, email);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            throw new DuplicateKeyException("email is already used");
          }
        }
      }

      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.users SET email = ? WHERE id = ?")) {
        statement.setString(1, email);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updateName(String id, String name) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("UPDATE dea.users SET name = ? WHERE id = ?")) {
        statement.setString(1, name);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updatePhotoURL(String id, String photoURL) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.users SET photo_url = ? WHERE id = ?")) {
        statement.setString(1, photoURL);
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updatePassword(String id, String password) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("UPDATE dea.users SET password = ? WHERE id = ?")) {
        statement.setString(1, BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        statement.setString(2, id);

        statement.executeUpdate();
      }
    }
  }

  public boolean isUsernameAvailable(String username) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT id FROM dea.users WHERE username = ? LIMIT 1")) {
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
          .prepareStatement("SELECT id FROM dea.users WHERE email = ? LIMIT 1")) {
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
