package nsbm.dea.admin.servlets.collections;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
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

@WebServlet(name = "get_collections", value = "/collections/all")
public class Get extends HttpServlet {
  public static final Gson gson = new Gson();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      CollectionDAO collectionDAO = new CollectionDAO();
      List<Collection> collections = collectionDAO.getAll();

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");

      JsonObject object = new JsonObject();
      object.addProperty("collections", gson.toJson(collections));

      out.println(object.toString());
      return;
    } catch (Exception e) {
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "Internal server error");
    }
  }
}
