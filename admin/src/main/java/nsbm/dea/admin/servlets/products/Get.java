package nsbm.dea.admin.servlets.products;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

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

@WebServlet(name = "get_product_with_id", value = "/products/get")
public class Get extends HttpServlet {
  private static final Gson gson = new Gson();

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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      ProductDAO productDAO = new ProductDAO();
      Optional<Product> productOptional = productDAO.getProductById(26);
      if (productOptional.isEmpty()) {
        this.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, null);
        return;
      }

      this.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, gson.toJson(productOptional.get()));
      return;
    } catch (Exception e) {
      e.printStackTrace();
      this.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR, null);
      return;
    }
  }
}
