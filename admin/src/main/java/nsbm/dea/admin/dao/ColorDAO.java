package nsbm.dea.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Color;

public class ColorDAO {

  public ColorDAO() {
  }

  public void create(Color[] colors) throws SQLException {
    String sql = "INSERT INTO dea.colors(category_id, created_by, name, hex, qty) VALUES";
    for (int index = 0; index < colors.length; index++) {
      sql = String.format("%s (?, CAST(? as ulid), ?, ?, ?)", sql);
      if (index != colors.length - 1) {
        sql = String.format("%s,", sql);
      }
    }

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        int index = 1;
        for (Color color : colors) {
          statement.setInt(index++, color.getCategoryId());
          statement.setString(index++, color.getCreatedBy());
          statement.setString(index++, color.getName());
          statement.setString(index++, color.getHex());
          statement.setInt(index++, color.getQuantity());
        }

        statement.executeUpdate();
      }
    }
  }

  public void create(Color[] colors, int categoryId) throws SQLException {
    String sql = "INSERT INTO dea.colors(category_id, created_by, name, hex, qty) VALUES";
    for (int index = 0; index < colors.length; index++) {
      sql = String.format("%s (?, CAST(? as ulid), ?, ?, ?)", sql);
      if (index != colors.length - 1) {
        sql = String.format("%s,", sql);
      }
    }

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        int index = 1;
        for (Color color : colors) {
          statement.setInt(index++, categoryId);
          statement.setString(index++, color.getCreatedBy());
          statement.setString(index++, color.getName());
          statement.setString(index++, color.getHex());
          statement.setInt(index++, color.getQuantity());
        }

        statement.executeUpdate();
      }
    }
  }

}
