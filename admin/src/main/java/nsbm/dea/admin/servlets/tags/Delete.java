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

@WebServlet(name = "tags_delete",value = "/tags/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            response.setContentType("text/html");
            String adminID="01HVT9ZYMRTVGZRGJFXMMCMY8Y";
            int tagID=Integer.parseInt(request.getParameter("ID"));
            System.out.println(tagID);
            Tags tags=new Tags();
            tags.setCreatedBy(adminID);
            tags.setId(tagID);

            TagDAO tagDAO=new TagDAO();
            tagDAO.delete(tags);

            Lib.sendJSONResponse(response,HttpServletResponse.SC_OK, Status.OK,"Tag deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
