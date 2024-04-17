package nsbm.dea.admin.servlets.auth;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonObject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
import nsbm.dea.admin.config.Env;
import nsbm.dea.admin.dao.AdminDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Admin;
import nsbm.dea.admin.tokens.AccessToken;
import nsbm.dea.admin.tokens.RefreshToken;
import nsbm.dea.admin.tokens.SessionToken;

@WebServlet(name = "login", value = "/auth/login")
public class Login extends HttpServlet {
  public class LoginData {
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

      try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
        Validator validator = factory.getValidator();

        LoginData data = new LoginData();
        data.setEmail(payload.get("email").getAsString());
        data.setPassword(payload.get("password").getAsString());

        Set<ConstraintViolation<LoginData>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
          Lib.sendJSONResponse(response, HttpServletResponse.SC_BAD_REQUEST, Status.BAD_REQUEST,
              violations.iterator().next().getMessage());
          return;
        }

        AdminDAO adminDAO = new AdminDAO();
        Optional<Admin> opt = adminDAO.getByEmail(data.getEmail());
        if (!opt.isPresent()) {
          Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.EMAIL_NOT_FOUND,
              "email address you entered cannot be found");
          return;
        }
        Admin admin = opt.get();

        if (!BCrypt.verifyer().verify(data.getPassword().toCharArray(), admin.getPassword().toCharArray()).verified) {
          Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.WRONG_PASSWORD,
              "incorrect password");
          return;
        }

        RefreshToken refreshToken = new RefreshToken();
        String rt = refreshToken.generate(admin.getId());

        AccessToken accessToken = new AccessToken();
        String at = accessToken.generate(admin.getId(), refreshToken.getUlid());

        SessionToken sessionToken = new SessionToken();
        String st = sessionToken.generate(admin);

        Cookie refreshTokenC = new Cookie("refresh_token", rt);
        refreshTokenC.setMaxAge(Math.toIntExact(Env.getRefreshTokenExp()));
        refreshTokenC.setDomain(Env.getDomain());
        refreshTokenC.setPath("/");
        refreshTokenC.setSecure(Env.getEnv() == "PROD" ? false : true);
        refreshTokenC.setHttpOnly(true);

        Cookie accessTokenC = new Cookie("access_token", at);
        accessTokenC.setMaxAge(Math.toIntExact(Env.getAccessTokenExp()));
        accessTokenC.setDomain(Env.getDomain());
        accessTokenC.setPath("/");
        accessTokenC.setSecure(Env.getEnv() == "PROD" ? false : true);
        accessTokenC.setHttpOnly(true);

        Cookie sessionTokenC = new Cookie("session", st);
        sessionTokenC.setMaxAge(Math.toIntExact(Env.getSessionTokenExp()));
        sessionTokenC.setDomain(Env.getDomain());
        sessionTokenC.setPath("/");
        sessionTokenC.setSecure(Env.getEnv() == "PROD" ? false : true);
        sessionTokenC.setHttpOnly(false);

        response.addCookie(refreshTokenC);
        response.addCookie(accessTokenC);
        response.addCookie(sessionTokenC);
        Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "Okay");
        return;
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
