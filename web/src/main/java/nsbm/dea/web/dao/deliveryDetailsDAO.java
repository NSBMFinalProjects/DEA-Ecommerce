package nsbm.dea.web.dao;

import nsbm.dea.web.connections.DB;
import nsbm.dea.web.models.deliveyDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class deliveryDetailsDAO {

    public String createDeliveryDetail(deliveyDetails deliveryDetail) throws SQLException {
        String sql = "INSERT INTO dea.delivery_details (user_id, address, city, province, postal_code) VALUES (cast(? as ulid), ?, ?, ?, ?) RETURNING id";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, deliveryDetail.getUserId());
            preparedStatement.setString(2, deliveryDetail.getAddress());
            preparedStatement.setString(3, deliveryDetail.getCity());
            preparedStatement.setString(4, deliveryDetail.getProvince());
            preparedStatement.setString(5, deliveryDetail.getPostalCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                return id;
            }
        }
        return deliveryDetail.getId();
    }
}