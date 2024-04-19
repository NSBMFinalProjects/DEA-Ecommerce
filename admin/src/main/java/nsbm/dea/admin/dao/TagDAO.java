package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Tag;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {
  private Tag getTagFromResultSet(ResultSet resultSet) throws SQLException {
    return new Tag(
        resultSet.getInt("id"),
        resultSet.getString("created_by"),
        resultSet.getString("slug"),
        resultSet.getString("name"),
        resultSet.getTimestamp("created"),
        resultSet.getTimestamp("modified"));
  }

  public void create(Tag tag) throws SQLException {
    String query = "INSERT INTO dea.tags (created_by, name) VALUES (CAST(? as ulid), ?)";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, tag.getCreatedBy());
        statement.setString(2, tag.getName());

        statement.executeUpdate();
      }
    }
  }

  public List<Tag> getTags(String createdBy) throws SQLException {
    List<Tag> tags = new ArrayList<Tag>();
    String sql = "SELECT * FROM dea.tags";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, createdBy);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            tags.add(this.getTagFromResultSet(resultSet));
          }
        }
      }
    }
    return tags;
  }

  public void update(int id, String name) throws SQLException {
    String sql = "UPDATE dea.tags SET name = ? WHERE id = ?";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, name);
        statement.setInt(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM dea.tags WHERE id = ?";
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);

        statement.executeUpdate();
      }
    }
  }
}
