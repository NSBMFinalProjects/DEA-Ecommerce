package nsbm.dea.web.middleware;

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
import jakarta.servlet.http.HttpServletRequest;
import nsbm.dea.web.dao.ProductDAO;
import nsbm.dea.web.models.Product;

@WebFilter(urlPatterns = {
    "/product/*",
}, filterName = "ProductFilter", description = "Navigate to proper products")
public class ProductM implements Filter {
  private ServletContext context;

  public void init(FilterConfig config) throws ServletException {
    this.context = config.getServletContext();
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    String url = req.getRequestURI();
    if (url == null) {
      chain.doFilter(request, response);
      return;
    }
    if (url.equalsIgnoreCase("/web/product/slug")) {
      request.getRequestDispatcher("/shopMen").forward(request, response);
      return;
    }
    String[] parts = url.split("/");
    if (parts.length != 4) {
      chain.doFilter(request, response);
      return;
    }

    String slug = parts[3];

    try {
      ProductDAO productDAO = new ProductDAO();
      Optional<Product> productOptional = productDAO.getProductBySlug(slug);
      if (productOptional.isEmpty()) {
        throw new Exception("product with the given slug is not found");
      }

      this.context.setAttribute("product", productOptional.get());
      request.getRequestDispatcher("/product/slug").forward(request, response);
      return;
    } catch (Exception e) {
      e.printStackTrace();
      chain.doFilter(request, response);
      return;
    }
  }
}
