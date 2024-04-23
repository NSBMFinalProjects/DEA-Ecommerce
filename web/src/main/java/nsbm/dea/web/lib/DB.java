package nsbm.dea.web.lib;

import java.sql.SQLException;

public class DB {
  public static boolean isBadRequest(SQLException e) {
    return e.getSQLState().equals("23505");
  }

  public static boolean isUnauthorized(SQLException e) {
    return e.getSQLState().equals("23503");
  }
}
