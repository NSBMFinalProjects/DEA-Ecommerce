package nsbm.dea.admin.servlets.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;

import com.google.gson.Gson;
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

@WebServlet(name = "login", value = "/auth/login")
public class Login extends HttpServlet {
  private static final Gson gson = new Gson();

  private class LoginData {
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be empty")
    private String password;

    public void setEmail(String email) {
      this.email = email;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getEmail() {
      return this.email;
    }

    public String getPassword() {
      return BCrypt.withDefaults().hashToString(12, this.password.toCharArray());
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
      StringBuilder jsonStr = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        jsonStr.append(line);
      }

      JsonObject payload = gson.fromJson(jsonStr.toString(), JsonObject.class);

      String email = payload.get("email").getAsString();
      String password = payload.get("password").getAsString();

      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();

      LoginData data = new LoginData();
      data.setEmail(email);
      data.setPassword(password);

      Set<ConstraintViolation<LoginData>> violations = validator.validate(data);
      if (!violations.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        JsonObject object = new JsonObject();
        object.addProperty("status", "Bad Reqeust");
        object.addProperty("mesage", violations.iterator().next().getMessage());
        out.println(object.toString());
        return;
      }

      JsonObject object = new JsonObject();
      object.addProperty("email", data.getEmail());
      object.addProperty("password", data.getPassword());

      response.setStatus(HttpServletResponse.SC_OK);
      out.println(object.toString());
      return;
    } catch (Exception e) {
      System.out.println(e);
      JsonObject object = new JsonObject();
      object.addProperty("status", "Bad request");

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      out.println(object.toString());
      return;
    }
  }
}
