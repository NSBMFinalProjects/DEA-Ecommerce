package nsbm.dea.admin.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nsbm.dea.admin.config.Env;

public class DB {
  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(Env.getDBURL(), Env.getDBUser(), Env.getDBPassword());
  }

  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
