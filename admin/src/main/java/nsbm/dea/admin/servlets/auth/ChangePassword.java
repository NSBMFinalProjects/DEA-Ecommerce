package nsbm.dea.admin.servlets.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonObject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
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
import nsbm.dea.admin.lib.DB;
import nsbm.dea.admin.model.Admin;

public class ChangePassword extends HttpServlet {
  public class Data {
    @NotNull(message = "email cannot be empty")
    @NotBlank(message = "email cannot be empty")
    @Size(min = 5, max = 200, message = "email address is invalid")
    @Email(message = "must be an valid email address")
    private String email;

    @NotNull(message = "password should not be empty")
    @NotNull(message = "password should not be empty")
    private String password;

    @NotNull(message = "password should not be empty")
    @NotNull(message = "password should not be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$", message = "password is not valid")
    private String newPassword;

    public void setEmail(String email) {
      this.email = email;
    }

    public String getEmail() {
      return email;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getPassword() {
      return password;
    }

    public String getNewPassword() {
      return newPassword;
    }

    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      JsonObject payload = Lib.getJSONPayloadFromRequest(request);
      Data data = new Data();

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();

        data.setEmail(payload.get("email").getAsString());
        data.setPassword(payload.get("password").getAsString());
        data.setNewPassword(payload.get("new_password").getAsString());

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
      Optional<Admin> adminOptional = adminDAO.getByEmail(data.getEmail());
      if (adminOptional.isEmpty()) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.EMAIL_NOT_FOUND,
            "account with the given email address could not be found");
        return;
      }

      Admin admin = adminOptional.get();

      if (!BCrypt.verifyer().verify(data.getPassword().toCharArray(), admin.getPassword().toCharArray()).verified) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.WRONG_PASSWORD,
            "password you entered is incorrect");
        return;
      }

      adminDAO.updatePassword(admin.getId(), data.getNewPassword());

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "password changed sucsessfully");
      return;
    } catch (SQLException e) {
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
      return;
    }
  }
}
