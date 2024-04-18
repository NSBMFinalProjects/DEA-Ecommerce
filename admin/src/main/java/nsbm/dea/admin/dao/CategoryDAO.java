package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDAO {

    public CategoryDAO() {

    }

    public void createCategory(Categories category,String createdBy,int productId) throws SQLException {
        String sql="insert into dea.categories(created_by, product_id, name) values(?,?,?)";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,createdBy);
                statement.setInt(2,productId);
                statement.setString(3, category.getName());
                statement.executeUpdate();
            }
        }
    }
}
