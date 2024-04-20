package nsbm.dea.web.servlets.auth;

import java.io.IOException;
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
import nsbm.dea.web.dao.UserDAO;
import nsbm.dea.web.enums.Status;
import nsbm.dea.web.lib.Lib;
import nsbm.dea.web.models.User;

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

      UserDAO userDAO = new UserDAO();
      Optional<User> userOptional = userDAO.getByEmail(data.getEmail());
      if (userOptional.isEmpty()) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.EMAIL_NOT_FOUND,
            "account with the given email address could not be found");
        return;
      }

      User user = userOptional.get();

      if (!BCrypt.verifyer().verify(data.getPassword().toCharArray(), user.getPassword().toCharArray()).verified) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.WRONG_PASSWORD,
            "password you entered is incorrect");
        return;
      }

      userDAO.updatePassword(user.getId(), data.getNewPassword());

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "password changed sucsessfully");
      return;

    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
