package nsbm.dea.admin.servlets.products;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;

@WebServlet(name = "delete_product", value = "/products/delete")
public class Delete extends HttpServlet {
  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      int id;
      try {
        id = Integer.parseInt(request.getParameter("id"));
      } catch (Exception e) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, "id is not valid");
        return;
      }

      ProductDAO productDAO = new ProductDAO();
      productDAO.delete(id);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "okay");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
