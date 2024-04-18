package nsbm.dea.admin.servlets.products;

import java.io.IOException;
import java.math.BigDecimal;
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
import nsbm.dea.admin.dao.CategoryDAO;
import nsbm.dea.admin.dao.ColorsDAO;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.dao.TagDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Categories;
import nsbm.dea.admin.model.Colors;
import nsbm.dea.admin.model.Product;
import nsbm.dea.admin.model.Tags;


@WebServlet(name = "product_create", value = "/product/create")

public class Create extends HttpServlet {
          @Override
          protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
          ServletException, IOException {
              try {
                  response.setContentType("application/json");
                  JsonObject object = Lib.getJSONPayloadFromRequest(request);

                  String adminId = "01HVS0EE80TCDMVG7JC6Y9N0BV";
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
                  int productID = productDAO.addProduct(product);
                  System.out.println("Product ID: " + productID);

                  Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the product  sucessfully");
                  JsonObject sizesObject = object.getAsJsonObject("sizes");

                  for (String size : sizesObject.keySet()) {
                      Categories newCategory = new Categories();
                      newCategory.setCreatedBy(adminId);
                      newCategory.setProductId(productID);
                      newCategory.setName(size);
                      CategoryDAO categoryDAO = new CategoryDAO();
                      int categoryID = categoryDAO.createCategory(newCategory);
                      System.out.println("Category ID: " + categoryID);

                      JsonObject sizeObject = sizesObject.getAsJsonObject(size);
                      JsonArray colorsArray = sizeObject.getAsJsonArray("colors");

                      for (JsonElement color : colorsArray) {
                          JsonObject colorObject = color.getAsJsonObject();

                          String title = colorObject.get("title").getAsString();
                          String hex = colorObject.get("hex").getAsString();
                          int qty = colorObject.get("qty").getAsInt();
                          BigDecimal price = colorObject.get("price").getAsBigDecimal();

                          Colors newColor = new Colors();
                          newColor.setCreatedBy(adminId);
                          newColor.setCategoryId(categoryID);
                          newColor.setName(title);
                          newColor.setHex(hex);
                          newColor.setQuantity(qty);
                          newColor.setPrice(price);

                          ColorsDAO colorsDAO = new ColorsDAO();
                          colorsDAO.create(newColor);
                      }

                  }
                  Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the category and color successfully");

           JsonArray tagsArray = object.getAsJsonArray("tags");
           for (JsonElement tag : tagsArray) {
               Tags newTag = new Tags();
               newTag.setCreatedBy(adminId);
               newTag.setName(tag.getAsString());
               TagDAO tagDAO=new TagDAO();
               tagDAO.create(newTag);
           }
           Lib.sendJSONResponse(response,HttpServletResponse.SC_OK,Status.OK,"created the tag successfully");



              } catch (SQLException e) {
                  System.out.println(e.getMessage());
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }