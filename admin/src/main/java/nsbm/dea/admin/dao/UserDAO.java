package nsbm.dea.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.User;

public class UserDAO {
  public List<User> getAllUsers() throws SQLException {
    List<User> users = new ArrayList<>();

    try (Connection connection = DB.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM dea.users");
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

        users.add(user);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    }

    return users;
  }
}
