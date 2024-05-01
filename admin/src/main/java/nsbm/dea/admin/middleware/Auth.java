package nsbm.dea.admin.middleware;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Admin;
import nsbm.dea.admin.tokens.AccessToken;
import nsbm.dea.admin.tokens.SessionToken;

@WebFilter(urlPatterns = {
    "/manageProducts.jsp"
}, filterName = "AuthFilter", description = "Protect routes by checking for authentication")
public class Auth implements Filter {
  private ServletContext context;

  public void init(FilterConfig config) throws ServletException {
    this.context = config.getServletContext();
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String accessToken;
    String authorization = req.getHeader("Authorization");
    if (authorization != null) {
      String[] data = authorization.split("Bearer");
      if (data.length == 2) {
        accessToken = data[1];
      } else {
        res.sendRedirect(Lib.getPath("/loginSignup.jsp"));
        return;
      }
    } else {
      Optional<Cookie> accessTokenOptional = Lib.getCookieByName(req, "access_token");
      if (accessTokenOptional.isEmpty()) {
        res.sendRedirect(Lib.getPath("/loginSignup.jsp"));
        return;
      }

      accessToken = accessTokenOptional.get().getValue();
    }

    AccessToken token = new AccessToken();
    if (!token.isValid(accessToken)) {
      res.sendRedirect(Lib.getPath("/loginSignup.jsp"));
      return;
    }

    Optional<Cookie> optionalSession = Lib.getCookieByName(req, "session");
    if (optionalSession.isEmpty()) {
      res.sendRedirect(Lib.getPath("/loginSignup.jsp"));
      return;
    }

    SessionToken session = new SessionToken();
    if (!session.isValid(optionalSession.get().getValue())) {
      res.sendRedirect(Lib.getPath("/loginSignup.jsp"));
      return;
    }

    Admin admin = session.getAdmin();
    this.context.setAttribute("admin", admin);

    chain.doFilter(request, response);
  }
}
