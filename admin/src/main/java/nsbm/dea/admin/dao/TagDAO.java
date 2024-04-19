package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Tags;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {

    public void create(Tags tag) throws SQLException {
        String sql = "INSERT INTO dea.tags (created_by, name) VALUES (CAST(? as ulid),?)";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1, tag.getCreatedBy());
                statement.setString(2, tag.getName());
                statement.executeUpdate();
            }
        }
    }

    public List<Tags> getTags(String createdBy) throws SQLException {
        List<Tags> tags = new ArrayList<Tags>();
        String sql = "SELECT * FROM dea.tags WHERE created_by = ?";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1, createdBy);
                try(ResultSet resultSet=statement.executeQuery()){
                    if(resultSet.next()){
                        Tags tag = new Tags();
                        tag.setId(resultSet.getInt("id"));
                        tag.setName(resultSet.getString("name"));
                        tags.add(tag);
                    }
                }
            }
        }
        return tags;
    }

    public void delete(Tags tags) throws SQLException {
        String sql = "DELETE FROM dea.tags WHERE id = ? AND created_by = cast(? as ulid)";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setInt(1, tags.getId());
                statement.setString(2, tags.getCreatedBy());
                statement.executeUpdate();
            }
        }
    }

    public void update(Tags tag) throws SQLException {
        String sql = "UPDATE dea.tags SET created_by = ? WHERE id = ?";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1, tag.getCreatedBy());
                statement.setInt(2, tag.getId());
                statement.executeUpdate();
            }
        }

    }




}
