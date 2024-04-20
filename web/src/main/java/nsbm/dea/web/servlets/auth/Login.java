package nsbm.dea.web.servlets.auth;

import java.io.IOException;
import java.util.Optional;
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
import nsbm.dea.web.dao.UserDAO;
import nsbm.dea.web.enums.Status;
import nsbm.dea.web.lib.Lib;
import nsbm.dea.web.models.User;
import nsbm.dea.web.tokens.AccessToken;
import nsbm.dea.web.tokens.RefreshToken;
import nsbm.dea.web.tokens.SessionToken;

@WebServlet(name = "/auth/login", value = "/auth/login")
public class Login extends HttpServlet {
  public class Data {
    @NotNull(message = "email should not be emtpy")
    @NotBlank(message = "email should not be emtpy")
    @Size(min = 3, max = 200, message = "email is not valid")
    @Email(message = "email is not valid")
    private String email;

    @NotNull(message = "password should not be empty")
    @NotNull(message = "password should not be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$", message = "password is not valid")
    private String password;

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
      Optional<User> opt = userDAO.getByEmail(data.getEmail());
      if (!opt.isPresent()) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.EMAIL_NOT_FOUND,
            "email address you entered cannot be found");
        return;
      }
      User user = opt.get();

      if (!BCrypt.verifyer().verify(data.getPassword().toCharArray(), user.getPassword().toCharArray()).verified) {
        Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.WRONG_PASSWORD,
            "incorrect password");
        return;
      }

      RefreshToken refreshToken = new RefreshToken();
      refreshToken.generate(user.getId());
      refreshToken.cookie(response);

      AccessToken accessToken = new AccessToken();
      accessToken.generate(user.getId(), refreshToken.getUlid());
      accessToken.cookie(response);

      SessionToken sessionToken = new SessionToken();
      sessionToken.generate(user);
      sessionToken.cookie(response);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "Okay");
      return;

    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
