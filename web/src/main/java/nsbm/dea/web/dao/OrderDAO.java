package nsbm.dea.web.dao;

import nsbm.dea.web.connections.DB;
import nsbm.dea.web.models.Order;
import nsbm.dea.web.models.UserOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO dea.orders (ordered_by, delivery_address, created, status, qty, total) VALUES (cast(? as ulid), ?, ?, ?, ?, ?) RETURNING id";
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

    public void createUserOrder(UserOrder userOrder) throws SQLException {
        String sql = "INSERT INTO dea.user_orders (order_id, product_id, category_id, color_id, price, qty) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userOrder.getOrderId());
            preparedStatement.setInt(2, userOrder.getProductId());
            preparedStatement.setInt(3, userOrder.getCategoryId());
            preparedStatement.setInt(4, userOrder.getColorId());
            preparedStatement.setBigDecimal(5, userOrder.getPrice());
            preparedStatement.setInt(6, userOrder.getQuantity());
            preparedStatement.executeUpdate();
        }
    }
}
