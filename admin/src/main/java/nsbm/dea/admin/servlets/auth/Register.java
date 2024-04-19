package nsbm.dea.admin.servlets.auth;

import java.io.IOException;
import java.util.Set;

import com.google.gson.JsonObject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import nsbm.dea.admin.dao.AdminDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Admin;

@WebServlet(name = "register", value = "/auth/register")
public class Register extends HttpServlet {

  private class Data {
    @NotNull(message = "email cannot be empty")
    @NotBlank(message = "email cannot be empty")
    @Size(min = 5, max = 200, message = "email address is invalid")
    @Email(message = "must be an valid email address")
    private String email;

    @NotNull(message = "username cannot be empty")
    @NotBlank(message = "username cannot be empty")
    @Size(min = 3, message = "username must be larger than 3 characters")
    @Size(max = 40, message = "username should not be larger than 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "username is not valid")
    private String username;

    @NotNull(message = "password should not be empty")
    @NotNull(message = "password should not be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$", message = "password is not valid")
    private String password;

    @NotNull(message = "name should not be empty")
    @NotNull(message = "name should not be empty")
    @Size(min = 3, message = "name should be larger than 3 cahracters")
    @Size(max = 50, message = "name should not be larger than 50 characters")
    private String name;

    public void setEmail(String email) {
      this.email = email;
    }

    public String getEmail() {
      return email;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getUsername() {
      return username;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public String getPassword() {
      return BCrypt.withDefaults().hashToString(12, this.password.toCharArray());
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      JsonObject payload = Lib.getJSONPayloadFromRequest(request);
      Data data = new Data();

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();

        data.setEmail(payload.get("email").getAsString());
        data.setUsername(payload.get("username").getAsString());
        data.setName(payload.get("name").getAsString());
        data.setPassword(payload.get("password").getAsString());

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

      AdminDAO adminDAO = new AdminDAO();
      if (!adminDAO.isUsernameAvailable(data.getUsername())) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.USERNAME_ALREADY_USED,
            "username already used");
        return;
      }

      if (!adminDAO.isEmailAvailable(data.getEmail())) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.EMAIL_ALREADY_USED,
            "email already used");
        return;
      }

      Admin admin = new Admin();
      admin.setEmail(data.getEmail());
      admin.setUsername(data.getUsername());
      admin.setName(data.getName());
      admin.setPassword(data.getPassword());

      adminDAO.create(admin);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the admin account sucessfully");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
