package nsbm.dea.admin.servlets.tags;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.TagDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;

@WebServlet(name = "delete_tag", value = "/tags/delete")
public class Delete extends HttpServlet {
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      int tagId;
      try {
        tagId = Integer.parseInt(request.getParameter("id"));
      } catch (Exception e) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, "id is not valid");
        return;
      }

      TagDAO tagDAO = new TagDAO();
      tagDAO.delete(tagId);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "deleted tag");
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }

  }
}
