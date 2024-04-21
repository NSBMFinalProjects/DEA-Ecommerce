package nsbm.dea.admin.servlets.products;

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
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.model.Product;

@WebServlet(name = "get_all_products", value = "/products/get-all")
public class GetAll extends HttpServlet {
  private void sendJSONResponse(HttpServletResponse response, int code, Status status, String data)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(code);
    PrintWriter out = response.getWriter();

    JsonObject object = new JsonObject();
    object.addProperty("status", status.toString());
    if (data == null) {
      object.add("data", null);
    } else {
      object.addProperty("data", data);
    }
    out.println(object.toString());
    return;
  }

  private static final Gson gson = new Gson();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int DEFAULT_LIMIT = 10;
    try {
      int page;
      int limit;
      try {
        page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        limit = Integer.parseInt(
            request.getParameter("limit") != null ? request.getParameter("limit") : String.format("%s", DEFAULT_LIMIT));
      } catch (Exception e) {
        e.printStackTrace();
        this.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, null);
        return;
      }

      ProductDAO productDAO = new ProductDAO(limit);
      List<Product> products = productDAO.getAllProducts(page);

      this.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, gson.toJson(products));
      return;
    } catch (Exception e) {
      e.printStackTrace();
      this.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR, null);
      return;
    }
  }
}
