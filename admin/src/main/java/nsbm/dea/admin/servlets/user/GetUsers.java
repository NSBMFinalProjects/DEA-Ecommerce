package nsbm.dea.admin.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.UserDAO;
import nsbm.dea.admin.model.User;

@WebServlet(name = "get-all", value = "/user/get-all")
public class GetUsers extends HttpServlet {
  private static final Gson gson = new Gson();
  private UserDAO userDAO;

  public void init() throws ServletException {
    userDAO = new UserDAO();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    try {
      response.setStatus(HttpServletResponse.SC_OK);

      List<User> users = userDAO.getAllUsers();
      out.println(gson.toJson(users));
    } catch (SQLException e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      JsonObject object = new JsonObject();
      object.addProperty("status", "Something went wrong");
      out.println(object.toString());
    }
  }
}
