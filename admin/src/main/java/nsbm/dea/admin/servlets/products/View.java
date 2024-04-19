package nsbm.dea.admin.servlets.products;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="get_products",value = "/product/get")
public class View extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        try {
            res.setContentType("application/json");
            ProductDAO dao = new ProductDAO();
            List<Product> products = dao.getAllProducts();
            String json=new Gson().toJson(products);
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
