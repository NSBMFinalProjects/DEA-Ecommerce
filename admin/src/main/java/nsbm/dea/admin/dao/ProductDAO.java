package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() throws SQLException{
        List<Product> products = new ArrayList<Product>();

        try (Connection connection= DB.getConnection()){
            PreparedStatement statement=connection.prepareStatement("select * from dea.products");
            ResultSet resultSet=statement.executeQuery();

            while(resultSet.next()){
                Product product=new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setSlug(resultSet.getString("slug"));
                product.setCreatedBy(resultSet.getString("created by"));

                String photoUrl=resultSet.getString("photo_url");
                String[] photoUrls=photoUrl.split(",");

                product.setPhotoUrls(photoUrls);
                product.setCreated(Timestamp.from(resultSet.getTimestamp("created").toInstant()));
                product.setModified(Timestamp.from(resultSet.getTimestamp("modified").toInstant()));

                products.add(product);
            }

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return products;
    }

    public boolean addProduct(Product product) throws SQLException{

        try (Connection connection= DB.getConnection()){
            String sql="insert into dea.products(slug, name, description, admin_id, photoUrls) values(?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1, product.getSlug());
            statement.setString(2,product.getName());
            statement.setString(3,product.getDescription());
            statement.setString(4,product.getCreatedBy());
            statement.setString(5, String.join(",", product.getPhotoUrls()));

            int result=statement.executeUpdate();
            return result>0;

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public boolean updateProduct(Product product) throws SQLException{

        try (Connection connection= DB.getConnection()){
            String sql="update dea.products set name=?,description=?,photoUrls=? where id=?";
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setString(1,product.getName());
            statement.setString(2,product.getDescription());
            statement.setString(3, String.join(",", product.getPhotoUrls()));

            int updateResult=statement.executeUpdate();
            return updateResult>0;

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public boolean deleteProduct(Product product) throws SQLException{
        try (Connection connection= DB.getConnection()){
            String sql="delete from dea.products where id=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setInt(1,product.getId());
            int result=statement.executeUpdate();

            return result>0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}
