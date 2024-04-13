package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() throws SQLException{
        List<Product> products = new ArrayList<Product>();

        try (Connection connection= DB.getConnection()){
            PreparedStatement statement=connection.prepareStatement("select * from product");
            ResultSet resultSet=statement.executeQuery();

            while(resultSet.next()){
                Product product=new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setSlug(resultSet.getString("slug"));
                product.setCategoryId(resultSet.getInt("categoryID"));
                product.setSubcategoryId(resultSet.getInt("subcategoryID"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setSize(resultSet.getString("size"));
                product.setAdminId(resultSet.getInt("adminID"));

                products.add(product);
            }

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return products;
    }

    public boolean addProduct(Product product) throws SQLException{

        try (Connection connection= DB.getConnection()){
            String sql="insert into product(slug, name, price, category_id, subcategory_id, size, description, quantity, admin_id) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1, product.getSlug());
            statement.setString(2,product.getName());
            statement.setString(3,product.getDescription());
            statement.setInt(4,product.getCategoryId());
            statement.setInt(5,product.getSubcategoryId());
            statement.setBigDecimal(6,product.getPrice());
            statement.setInt(7,product.getQuantity());
            statement.setString(8,product.getSize());
            statement.setInt(9,product.getAdminId());

            int result=statement.executeUpdate();
            return result>0;

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public boolean updateProduct(Product product) throws SQLException{

        try (Connection connection= DB.getConnection()){
            String sql="update product set name=?,price=?,description=?,quantity=?,size=? where id=?";
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setString(1,product.getName());
            statement.setBigDecimal(2,product.getPrice());
            statement.setString(3,product.getDescription());
            statement.setInt(4,product.getQuantity());
            statement.setString(5,product.getSize());

            int updateResult=statement.executeUpdate();
            return updateResult>0;

        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public boolean deleteProduct(Product product) throws SQLException{
        try (Connection connection= DB.getConnection()){
            String sql="delete from product where id=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setInt(1,product.getId());
            int result=statement.executeUpdate();

            return result>0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}
