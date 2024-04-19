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

@WebServlet(name = "refresh", value = "/auth/refresh")
public class Refresh extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      Optional<Cookie> rto = Lib.getCookieByName(request, "refresh_token");
      if (rto.isEmpty()) {
        throw new UnauthorizedException("refresh token is not present");
      }
      String rtv = rto.get().getValue();

      RefreshToken rt = new RefreshToken();
      if (!rt.isValid(rtv)) {
        throw new UnauthorizedException("refresh token is not valid");
      }
      String userId = rt.getSub();

      AccessToken at = new AccessToken();
      at.generate(userId, rt.getUlid());
      at.cookie(response);

      Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "token refreshed");
      return;
    } catch (UnauthorizedException e) {
      System.err.println(e.getMessage());
      Auth.removeAuthCookies(request, response);
      Lib.sendJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Status.UNAUTHORIZED,
          "you are not logged in login to proceed");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
          "something went wrong");
      return;
    }
  }
}
