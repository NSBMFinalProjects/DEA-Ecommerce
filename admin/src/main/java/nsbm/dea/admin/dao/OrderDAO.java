package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.DeliveryDetails;
import nsbm.dea.admin.model.Order;
import nsbm.dea.admin.model.Product;
import nsbm.dea.admin.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrderDetails() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT " +
                "o.id AS order_id, " +
                "o.total AS total, " +
                "u.username AS user_name, " +
                "d.address AS user_address, " +
                "p.name AS product_name, " +
                "uo.qty AS quantity, " +
                "c.name AS category_name, " +
                "o.status AS status " +
                "FROM dea.orders o " +
                "JOIN dea.users u ON o.ordered_by = u.id " +
                "JOIN dea.delivery_details d ON o.delivery_address = d.id " +
                "JOIN dea.user_orders uo ON o.id = uo.order_id " +
                "JOIN dea.products p ON uo.product_id = p.id " +
                "JOIN dea.categories c ON uo.category_id = c.id";
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    User user = new User(resultSet.getString("user_name"));
                    DeliveryDetails deliveryDetails = new DeliveryDetails(resultSet.getString("user_address"));
                    Product product = new Product(resultSet.getString("product_name"));
                    int quantity = resultSet.getInt("quantity");
                    double total = resultSet.getDouble("total");
                    String status = resultSet.getString("status");
                    int id = resultSet.getInt("order_id");

                    Order order = new Order(id, user, deliveryDetails, product, quantity, status, total);
                    orders.add(order);
                }
            }
        }
        return orders;
    }


}
