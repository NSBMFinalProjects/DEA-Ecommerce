package nsbm.dea.admin.servlets.products;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nsbm.dea.admin.dao.CollectionDAO;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.dao.TagDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Admin;
import nsbm.dea.admin.model.Category;
import nsbm.dea.admin.model.Color;
import nsbm.dea.admin.model.Product;
import nsbm.dea.admin.lib.DB;

@WebServlet(name = "create_product", value = "/products/create")
public class Create extends HttpServlet {
  private class Data {
    @Valid
    private ProductData product;

    public ProductData getProduct() {
      return product;
    }
  }

  private class ProductData {
    @NotEmpty(message = "product name should not be emtpy")
    @NotNull(message = "product name should not be emtpy")
    @Size(min = 3, max = 100, message = "product is not valid")
    private String name;

    @Min(value = 1, message = "price is not valid")
    private BigDecimal price;

    private List<@URL(message = "photos must be URLs") String> photo_urls;

    @Size(min = 3, max = 200, message = "description is not valid")
    private String description;

    @Valid
    @NotEmpty(message = "categories should not be empty")
    @NotNull(message = "categories should not be empty")
    @Size(min = 1, message = "categories should not be empty")
    private List<CategoryData> categories;

    @Valid
    @NotNull(message = "tags cannot be emtpy")
    @NotEmpty(message = "tags cannot be empty")
    private List<Integer> tags;

    @Valid
    @NotNull(message = "collections cannot be empty")
    @NotEmpty(message = "collections cannot be empty")
    private List<Integer> collections;

    public String getName() {
      return name;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public List<String> getPhoto_urls() {
      return photo_urls;
    }

    public String getDescription() {
      return description;
    }

    public List<CategoryData> getCategories() {
      return categories;
    }

    public Integer[] getTags() {
      return tags.toArray(Integer[]::new);
    }

    public Integer[] getCollections() {
      return collections.toArray(Integer[]::new);
    }
  }

  private class CategoryData {
    @NotNull(message = "category should not be empty")
    @NotBlank(message = "category should not be empty")
    @Size(min = 3, max = 50, message = "category is not valid")
    private String name;

    @Valid
    @NotNull(message = "colors should not be empty")
    @NotEmpty(message = "colors should not be empty")
    @Size(min = 1, message = "colors should not be empty")
    private List<ColorData> colors;

    public String getName() {
      return name;
    }

    public List<ColorData> getColors() {
      return colors;
    }
  }

  private class ColorData {
    @NotEmpty(message = "color name cannot be empty")
    @NotNull(message = "color name cannot be empty")
    @Size(min = 3, max = 10, message = "color name is not valid")
    private String name;

    @Min(value = 1, message = "quantity is not valid")
    private int qty;

    @NotEmpty(message = "color code cannot be empty")
    @NotNull(message = "color code cannot be empty")
    private String hex;

    public String getName() {
      return name;
    }

    public int getQty() {
      return qty;
    }

    public String getHex() {
      return hex;
    }
  }

  private static final Gson gson = new Gson();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      Admin admin = ((Admin) request.getSession().getServletContext().getAttribute("admin"));
      JsonObject payload = Lib.getJSONPayloadFromRequest(request);
      Data data;

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();
        data = gson.fromJson(payload, Data.class);

        Set<ConstraintViolation<Data>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
          Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST,
              violations.iterator().next().getMessage());
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, "bad request");
        return;
      }
      ProductData productData = data.getProduct();

      Product product = new Product();
      product.setName(productData.getName());
      product.setPrice(productData.getPrice());
      product.setPhotoUrls(productData.getPhoto_urls().toArray(String[]::new));
      product.setDescription(productData.getDescription());

      List<Category> categories = new ArrayList<>();
      for (CategoryData category : productData.getCategories()) {
        Category c = new Category();
        c.setName(category.getName());
        c.setCreatedBy(admin.getId());

        List<Color> colors = new ArrayList<>();
        for (ColorData color : category.getColors()) {
          Color cl = new Color();
          cl.setName(color.getName());
          cl.setQuantity(color.getQty());
          cl.setHex(color.getHex());
          cl.setCreatedBy(admin.getId());

          colors.add(cl);
        }

        c.setColors(colors.toArray(Color[]::new));
        categories.add(c);
      }
      product.setCategories(categories.toArray(Category[]::new));
      product.setCreatedBy(admin.getId());

      ProductDAO productDAO = new ProductDAO();
      productDAO.create(product);

      try {
        TagDAO tagDAO = new TagDAO();
        tagDAO.linkWithProduct(productData.getTags(), product.getId());

        CollectionDAO collectionDAO = new CollectionDAO();
        collectionDAO.linkWithProduct(productData.getCollections(), product.getId());
      } catch (SQLException e) {
        e.printStackTrace();
      }

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "okay");
      return;
    } catch (SQLException e) {
      e.printStackTrace();
      if (DB.isBadRequest(e)) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST,
            "product with this name already exsists");
        return;
      }
      if (DB.isUnauthorized(e)) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.UNAUTHORIZED,
            "you are unauthorized to perform this operation");
        return;
      }

      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
    }
  }
}
