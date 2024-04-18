package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    public CategoryDAO() {

    }

    public int createCategory(Categories category) throws SQLException {
        String sql="insert into dea.categories(created_by, product_id, name) values(CAST(? as ulid),?,?) returning id";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,category.getCreatedBy());
                statement.setInt(2,category.getProductId());
                statement.setString(3, category.getName());
                try(ResultSet resultSet=statement.executeQuery()){
                    if(resultSet.next()){
                        return resultSet.getInt(1);
                    }
                }
                throw new SQLException("Create category failed");
            }
        }
    }
}
