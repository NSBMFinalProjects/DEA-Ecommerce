package nsbm.dea.web.servlets.auth;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.web.enums.Status;
import nsbm.dea.web.errors.UnauthorizedException;
import nsbm.dea.web.lib.Auth;
import nsbm.dea.web.lib.Lib;
import nsbm.dea.web.tokens.AccessToken;
import nsbm.dea.web.tokens.RefreshToken;

@WebServlet(name = "logout", value = "/auth/logout")
public class Logout extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      Optional<Cookie> refreshTokenOptional = Lib.getCookieByName(request, "refresh_token");
      if (refreshTokenOptional.isEmpty()) {
        throw new UnauthorizedException("refresh token is not present");
      }
      Optional<Cookie> accessTokenOptional = Lib.getCookieByName(request, "access_token");
      if (accessTokenOptional.isEmpty()) {
        throw new UnauthorizedException("access token is not present");
      }

      RefreshToken refreshToken = new RefreshToken();
      AccessToken accessToken = new AccessToken();

      try {
        accessToken.delete(accessTokenOptional.get().getValue());
      } catch (UnauthorizedException e) {
        System.err.println(e.getMessage());
      }
      refreshToken.delete(refreshTokenOptional.get().getValue());

      Auth.removeAuthCookies(request, response);
      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "Okay");
      return;
    } catch (UnauthorizedException e) {
      System.err.println(e.getMessage());
      Auth.removeAuthCookies(request, response);
      Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.UNAUTHORIZED,
          "unable to logout the user");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
