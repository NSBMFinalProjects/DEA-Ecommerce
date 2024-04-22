package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

  public CategoryDAO() {
  }

  public void create(Category[] categories) throws SQLException {
    String sql = "INSERT INTO dea.categories(created_by, product_id, name) VALUES (CAST(? as ulid), ?, ?) RETURNING id";
    try (Connection connection = DB.getConnection()) {
      for (Category category : categories) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setString(1, category.getCreatedBy());
          statement.setInt(2, category.getProductId());
          statement.setString(3, category.getName());
          try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
              throw new SQLException("category creation failed");
            }

            ColorDAO colorDAO = new ColorDAO();
            colorDAO.create(category.getColors(), resultSet.getInt("id"));
          }
        }
      }
    }
  }

  public void create(Category[] categories, int productId) throws SQLException {
    String sql = "INSERT INTO dea.categories(created_by, product_id, name) VALUES (CAST(? as ulid), ?, ?) RETURNING id";
    try (Connection connection = DB.getConnection()) {
      for (Category category : categories) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setString(1, category.getCreatedBy());
          statement.setInt(2, productId);
          statement.setString(3, category.getName());
          try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
              throw new SQLException("category creation failed");
            }

            ColorDAO colorDAO = new ColorDAO();
            colorDAO.create(category.getColors(), resultSet.getInt("id"));
          }
        }
      }
    }
  }
}
