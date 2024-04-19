package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductDAO {

  public ProductDAO() {

  }

  public List<Product> getAllProducts() throws SQLException {
    List<Product> products = new ArrayList<Product>();

    try (Connection connection = DB.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("select * from dea.products");
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setSlug(resultSet.getString("slug"));
        product.setCreatedBy(resultSet.getString("created_by"));
        String photoUrl = resultSet.getString("photo_urls");
        String[] photoUrls = photoUrl.split(",");
        product.setPhotoUrls(photoUrls);
        product.setCreated(Timestamp.from(resultSet.getTimestamp("created").toInstant()));
        product.setModified(Timestamp.from(resultSet.getTimestamp("modified").toInstant()));

        products.add(product);
      }

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return products;
  }


    public int addProduct(Product product) throws SQLException {
        try (Connection connection = DB.getConnection()) {
            String sql = "insert into dea.products(created_by, name, photo_urls, description) values(CAST(? as ulid),?,?,?) returning id;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, product.getCreatedBy());
                statement.setString(2, product.getName());
                statement.setArray(3, connection.createArrayOf("text", product.getPhotoUrls()));
                statement.setString(4, product.getDescription());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");

                    }
                }
                throw new SQLException("failed to create product");
            }
        }
    }
  public boolean updateProduct(Product product) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      String sql = "update dea.products set name=?,description=?,photo_urls=? where id=?";
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.setString(1, product.getName());
      statement.setString(2, product.getDescription());
      statement.setString(3, String.join(",", product.getPhotoUrls()));

      int updateResult = statement.executeUpdate();
      return updateResult > 0;

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());

    }
  }

  public boolean deleteProduct(Product product) throws SQLException {
      try (Connection connection = DB.getConnection()) {
          String sql = "delete from dea.products where id=?";
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setInt(1, product.getId());
      } catch (SQLException e) {
          throw new SQLException(e.getMessage());
      }
      return true;
  }
}
