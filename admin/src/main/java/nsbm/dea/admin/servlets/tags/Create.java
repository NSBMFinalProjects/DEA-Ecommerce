package nsbm.dea.admin.servlets.tags;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.TagDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Tags;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "tag_create" ,value = "/tag/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            response.setContentType("application/json");
       JsonObject object = Lib.getJSONPayloadFromRequest(request);
       String name = object.get("name").getAsString();
       String adminId = "01HVT9ZYMRTVGZRGJFXMMCMY8Y";

        Tags tag=new Tags();
        tag.setName(name);
        tag.setCreatedBy(adminId);

        TagDAO tagDAO=new TagDAO();
        tagDAO.create(tag);
        Lib.sendJSONResponse(response,HttpServletResponse.SC_OK, Status.OK,"tag is created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
