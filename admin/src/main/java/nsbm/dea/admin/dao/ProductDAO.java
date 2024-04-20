package nsbm.dea.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Product;

public class ProductDAO {
  private int LIMIT;

  public ProductDAO() {
  }

  public ProductDAO(int LIMIT) {
    LIMIT = this.LIMIT;
  }

  private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
    return new Product(
        resultSet.getInt("id"),
        resultSet.getString("created_by"),
        resultSet.getString("slug"),
        resultSet.getString("name"),
        ((String[]) resultSet.getArray("photo_urls").getArray()),
        resultSet.getString("description"),
        resultSet.getTimestamp("created"),
        resultSet.getTimestamp("modified"));
  }

  public List<Product> getAllProducts(int page) throws SQLException {
    List<Product> products = new ArrayList<Product>();

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("SELECT * FROM dea.products ORDER BY id OFFSET ? LIMIT ?")) {
        statement.setInt(1, page * this.LIMIT);
        statement.setInt(2, this.LIMIT);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            products.add(this.getProductFromResultSet(resultSet));
          }
        }
      }
    }
    return products;
  }

  public List<Product> getAllProducts() throws SQLException {
    return this.getAllProducts(1);
  }

  public void create(Product product) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "INSERT INTO dea.products(created_by, name, photo_urls, description) VALUES (CAST(? as ulid), ?, ?, ?) RETURNING id";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setObject(1, product.getCreatedBy());
        statement.setString(2, product.getName());
        statement.setArray(3, connection.createArrayOf("TEXT", product.getPhotoUrls()));
        statement.setString(4, product.getDescription());

        try (ResultSet resultSet = statement.executeQuery()) {
          if (!resultSet.next()) {
            throw new SQLException("failed to create the product");
          }
          product.setId(resultSet.getInt("id"));

          CategoryDAO categoryDAO = new CategoryDAO();
          categoryDAO.create(product.getCategories(), product.getId());
        }
      }
    }
  }

  public void update(Product product) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "UPDATE dea.products SET name = ?,description = ?,photo_urls = ? WHERE id = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setString(3, String.join(",", product.getPhotoUrls()));
        statement.executeUpdate();
      }
    }
  }

  public void updateName(int id, String name) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "UPDATE dea.products SET name = ? WHERE id = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, name);
        statement.setInt(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updateDescription(int id, String description) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "UPDATE dea.products SET description = ? WHERE id = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, description);
        statement.setInt(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void updatePhotoURLs(int id, String[] photoURLs) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "UPDATE dea.products SET description = ? WHERE id = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, String.join(",", photoURLs));
        statement.setInt(2, id);

        statement.executeUpdate();
      }
    }
  }

  public void delete(int productId) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "DELETE FROM dea.products WHERE id = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, productId);

        statement.executeUpdate();
      }
    }
  }
}
