package nsbm.dea.web.dao;

import nsbm.dea.web.connections.DB;
import nsbm.dea.web.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO dea.orders (ordered_by, delivery_address, created, status, qty, total) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
                preparedStatement.setString(1, order.getOrderedBy());
                preparedStatement.setString(2, order.getDeliveryAddress());
                preparedStatement.setTimestamp(3, order.getCreated());
                preparedStatement.setString(4, order.getStatus());
                preparedStatement.setInt(5, order.getQty());
                preparedStatement.setDouble(6, order.getTotal());
                preparedStatement.executeUpdate();
            }
        }
        return order.getId();
    }
}
