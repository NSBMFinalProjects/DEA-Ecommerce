package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Colors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ColorsDAO {

    public ColorsDAO() {

    }
    public void create(Colors colors) throws SQLException {
        String sql="insert into dea.colors(category_id, created_by, name, hex, qty, price) values(?,CAST(? as ulid),?,?,?,?)";
        try(Connection connection= DB.getConnection()) {
            try (PreparedStatement statement=connection.prepareStatement(sql)) {
                statement.setInt(1, colors.getCategoryId());
                statement.setString(2, colors.getCreatedBy());
                statement.setString(3, colors.getName());
                statement.setString(4, colors.getHex());
                statement.setInt(5, colors.getQuantity());
                statement.setBigDecimal(6, colors.getPrice());
                statement.executeUpdate();
            }
        }
    }


}
