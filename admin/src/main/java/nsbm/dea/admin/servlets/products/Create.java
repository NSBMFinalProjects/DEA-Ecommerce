package nsbm.dea.admin.servlets.products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Product;

@WebServlet(name = "product_create", value = "/product/create")
public class Create extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      response.setContentType("application/json");
      JsonObject object = Lib.getJSONPayloadFromRequest(request);

      String adminId = "01HVJJBZNAAXKBT6KA816QW41N";

      String name = object.get("name").getAsString();
      String description = object.get("description").getAsString();
      JsonArray photos = object.getAsJsonArray("photos");
      List<String> photoUrls = new ArrayList<>();
      for (JsonElement photo : photos) {
        String photoName = photo.getAsString();
        photoUrls.add(photoName);
      }

      String[] photoUrlArray = photoUrls.toArray(new String[0]);

      Product product = new Product();
      product.setName(name);
      product.setDescription(description);
      product.setPhotoUrls(photoUrlArray);
      product.setCreatedBy(adminId);

      ProductDAO productDAO = new ProductDAO();
      int productId = productDAO.addProduct(product);
      System.out.println(productId);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the product  sucessfully");
      // JsonObject sizesObject = object.getAsJsonObject("sizes");
      // for (String size : sizesObject.keySet()) {
      // Categories newCategory=new Categories();
      // newCategory.setName(size);
      // newCategory.setCreatedBy(createBy);
      // newCategory.setProductId(produt);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      // Handle error
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
