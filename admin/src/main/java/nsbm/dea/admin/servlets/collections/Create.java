package nsbm.dea.admin.servlets.collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nsbm.dea.admin.dao.CollectionDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Collection;

@WebServlet(name = "create_collection", value = "/collections/create")
public class Create extends HttpServlet {
  private class Data {
    @NotNull(message = "name cannot be empty")
    @NotBlank(message = "name cannot be empty")
    @Size(min = 3, max = 100, message = "name is not valid")
    private String name;

    @Size(min = 3, max = 200, message = "description is not valid")
    private String description;

    private List<@URL String> photoURLs;

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }

    public void setPhotoURLs(JsonArray array) {
      List<String> urls = new ArrayList<>();
      for (JsonElement url : array) {
        urls.add(url.getAsString());
      }

      this.photoURLs = urls;
    }

    public List<String> getPhotoURLs() {
      return photoURLs;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String adminId = "01HVY2DG7SN4FDJMS9GNE6E4JX";

      JsonObject payload = Lib.getJSONPayloadFromRequest(request);

      Data data = new Data();

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();

        try {
          data.setName(payload.get("name").getAsString());
          data.setDescription(payload.get("description").getAsString());
          if (!payload.get("photo_urls").isJsonArray()) {
            Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST,
                "provide valid photo urls");
            return;
          }
          data.setPhotoURLs(payload.get("photo_urls").getAsJsonArray());

        } catch (Exception e) {
          e.printStackTrace();
          Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST, "bad request");
          return;
        }

        Set<ConstraintViolation<Data>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
          Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST,
              violations.iterator().next().getMessage());
          return;
        }
      }

      Collection collection = new Collection();
      collection.setName(data.getName());
      collection.setDescription(data.getDescription());
      collection.setPhotoUrls(data.getPhotoURLs().toArray(new String[0]));
      collection.setCreatedBy(adminId);

      CollectionDAO collectionDAO = new CollectionDAO();
      collectionDAO.create(collection);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the collection");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }

  }
}
