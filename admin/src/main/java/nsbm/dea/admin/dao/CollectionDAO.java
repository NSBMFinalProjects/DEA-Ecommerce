package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Collection;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionDAO {
  private Collection getCollectionFromResultSet(ResultSet resultSet) throws SQLException {
    return new Collection(
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getString("slug"),
        resultSet.getString("description"),
        resultSet.getString("created_by"),
        ((String[]) resultSet.getArray("photo_urls").getArray()),
        resultSet.getTimestamp("created"),
        resultSet.getTimestamp("modified"));
  }

  public List<Collection> getAll() throws SQLException {
    List<Collection> collections = new ArrayList<Collection>();
    String query = "SELECT * FROM dea.collections";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            collections.add(this.getCollectionFromResultSet(resultSet));
          }
        }
      }
    }
    return collections;
  }

  public Optional<Collection> getById(int id) throws SQLException {
    String query = "SELECT * FROM dea.collections WHERE id = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getCollectionFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public Optional<Collection> getBySlug(String slug) throws SQLException {
    String query = "SELECT * FROM dea.collections WHERE slug = ? LIMIT 1";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, slug);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(this.getCollectionFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }

  public void create(Collection collection) throws SQLException {
    String query = "INSERT INTO dea.collections (name, description, created_by, photo_urls) VALUES (?, ?, CAST(? AS ulid), ?)";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, collection.getName());
        statement.setString(2, collection.getDescription());
        statement.setString(3, collection.getCreatedBy());
        statement.setArray(4, connection.createArrayOf("TEXT", collection.getPhotoUrls()));

        statement.executeUpdate();
      }
    }
  }

  public void update(Collection collection) throws SQLException {
    String query = "UPDATE dea.collections SET name = ?, description = ?, photo_urls = ? WHERE id = ?";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, collection.getName());
        statement.setString(2, collection.getDescription());
        statement.setArray(3, connection.createArrayOf("TEXT", collection.getPhotoUrls()));
        statement.setInt(4, collection.getId());

        statement.executeUpdate();
      }
    }
  }

  public void delete(int id) throws SQLException {
    String query = "DELETE FROM dea.collections WHERE id = ?";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, id);

        statement.executeUpdate();
      }
    }
  }
}
