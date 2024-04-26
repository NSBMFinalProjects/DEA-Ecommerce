package nsbm.dea.web.dao;

import nsbm.dea.web.connections.DB;
import nsbm.dea.web.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    public CategoryDAO() {
    }

    public void create(Category[] categories) throws SQLException {
        String sql = "INSERT INTO dea.categories(created_by, product_id, name) VALUES (CAST(? as ulid), ?, ?) RETURNING id";
        System.out.println(categories.length);
        try (Connection connection = DB.getConnection()) {
            for (Category category : categories) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, category.getCreatedBy());
                    statement.setInt(2, category.getProductId());
                    statement.setString(3, category.getName());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            throw new SQLException("category creation failed");
                        }

                        ColorDAO colorDAO = new ColorDAO();
                        colorDAO.create(category.getColors(), resultSet.getInt("id"));
                    }
                }
            }
        }
    }

    public void create(Category[] categories, int productId) throws SQLException {
        String sql = "INSERT INTO dea.categories(created_by, product_id, name) VALUES (CAST(? as ulid), ?, ?) RETURNING id";
        System.out.println(categories.length);
        try (Connection connection = DB.getConnection()) {
            for (Category category : categories) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, category.getCreatedBy());
                    statement.setInt(2, productId);
                    statement.setString(3, category.getName());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            throw new SQLException("category creation failed");
                        }

                        ColorDAO colorDAO = new ColorDAO();
                        colorDAO.create(category.getColors(), resultSet.getInt("id"));
                    }
                }
            }
        }
    }

    public int getCategoryId(int productId, String categoryName) throws SQLException {
        String sql = "SELECT c.id FROM dea.categories c " +
                "JOIN dea.products p ON c.product_id = p.id " +
                "WHERE p.id = ? AND c.name = ?";
        int categoryId = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, productId);
                statement.setString(2, categoryName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        categoryId = resultSet.getInt("id");
                    } else {
                        throw new SQLException("Category not found for product ID " + productId + " and category name " + categoryName);
                    }
                }
            }
        }
        return categoryId;
    }



}
