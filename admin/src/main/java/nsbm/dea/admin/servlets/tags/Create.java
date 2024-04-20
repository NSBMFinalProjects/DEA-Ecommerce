package nsbm.dea.admin.servlets.tags;

import java.io.IOException;
import java.util.Set;

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
import nsbm.dea.admin.dao.TagDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Tag;

@WebServlet(name = "create_tag", value = "/tags/create")
public class Create extends HttpServlet {
  private class TagData {
    @NotNull(message = "tag name cannot be empty")
    @NotBlank(message = "tag name cannot be empty")
    @Size(min = 3, max = 100, message = "tag name is not valid")
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String adminId = "01HVY2DG7SN4FDJMS9GNE6E4JX";
      JsonObject payload = Lib.getJSONPayloadFromRequest(request);

      TagData data = new TagData();

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();

        data.setName(payload.get("name").getAsString());

        Set<ConstraintViolation<TagData>> violations = validator.validate(data);
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

      Tag tag = new Tag();
      tag.setName(data.getName());
      tag.setCreatedBy(adminId);

      TagDAO tagDAO = new TagDAO();
      tagDAO.create(tag);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "tag created");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }

  }
}
