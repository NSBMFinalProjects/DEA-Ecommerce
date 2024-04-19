package nsbm.dea.admin.servlets.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.CollectionDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Collection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Collection_create",value = "/collection/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("application/json");

            JsonObject object= Lib.getJSONPayloadFromRequest(request);
            String adminId="01HVT9ZYMRTVGZRGJFXMMCMY8Y";
            String name=object.get("name").getAsString();
            String description=object.get("description").getAsString();
            JsonArray photos=object.get("photos").getAsJsonArray();
            List<String> photoList=new ArrayList<String>();
            for(JsonElement photo:photos){
                String photoName=photo.getAsString();
                photoList.add(photoName);
            }
            String[] collectionPhotos=photoList.toArray(new String[0]);

            Collection collection=new Collection();
            collection.setName(name);
            collection.setDescription(description);
            collection.setCreatedBy(adminId);
            collection.setPhotoUrls(collectionPhotos);

            CollectionDAO collectionDAO=new CollectionDAO();
            collectionDAO.addCollection(collection);

            Lib.sendJSONResponse(response,HttpServletResponse.SC_OK, Status.OK,"Collection created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
