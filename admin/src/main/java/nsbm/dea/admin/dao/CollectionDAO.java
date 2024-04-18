package nsbm.dea.admin.dao;

import nsbm.dea.admin.connections.DB;
import nsbm.dea.admin.model.Collection;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO {

    public List<Collection> getCollections() throws SQLException {
        List<Collection> collections = new ArrayList<Collection>();
        String query = "select * from dea.collections";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(query)){
                ResultSet rs=statement.executeQuery();
                while(rs.next()){
                    Collection collection = new Collection();
                    collection.setId(rs.getInt("id"));
                    collection.setName(rs.getString("name"));
                    collection.setDescription(rs.getString("description"));
                    collections.add(collection);
                }
            }
        }
        return collections;
    }

    public List<Collection> getCollection(int id) throws SQLException {
        List<Collection> collections = new ArrayList<Collection>();
        Collection collection = new Collection();
        String query = "select * from dea.collections where id=?";
        try(Connection connection= DB.getConnection()){
            try(PreparedStatement statement=connection.prepareStatement(query)){
                statement.setInt(1, id);
                ResultSet rs=statement.executeQuery();
                while(rs.next()){
                    collection.setId(rs.getInt("id"));
                    collection.setName(rs.getString("name"));
                    collection.setDescription(rs.getString("description"));
                    collections.add(collection);
                }
            }
        }
        return collections;
    }

    public Collection addCollection(Collection collection) throws SQLException {
        String query = "insert into dea.collections (name, description,created_by) values (?,?,cast( ? as ulid))";
        try(Connection connection=DB.getConnection()){
            try(PreparedStatement statement =connection.prepareStatement(query)){
                statement.setString(1, collection.getName());
                statement.setString(2,collection.getDescription());
                statement.setString(3,collection.getCreatedBy());
                statement.executeUpdate();
            }
        }
        return collection;
    }

    public Collection updateCollection(Collection collection) throws SQLException {
        String query = "update dea.collections set name=?,description=? where id=?";
        try(Connection connection=DB.getConnection()){
            try(PreparedStatement statement =connection.prepareStatement(query)){
                statement.setString(1, collection.getName());
                statement.setString(2, collection.getDescription());
                statement.setInt(3, collection.getId());
                statement.executeUpdate();

            }
        }
        return collection;
    }

    public void deleteCollection(int id) throws SQLException {
        String query = "delete from dea.collections where id=?";
        try(Connection connection=DB.getConnection()){
            try(PreparedStatement statement =connection.prepareStatement(query)){
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
        return ;
    }

}
