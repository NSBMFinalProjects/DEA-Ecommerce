package nsbm.dea.admin.servlets.products;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Admin;
import nsbm.dea.admin.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name="update product",value = "/product/update")
public class Update extends HttpServlet {

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Admin admin = ((Admin) request.getSession().getServletContext().getAttribute("admin"));
        String productId = request.getParameter("productId");
        JsonObject payload = Lib.getJSONPayloadFromRequest(request);

        String name = payload.get("name").getAsString();
        String description = payload.get("description").getAsString();
        BigDecimal price = new BigDecimal(payload.get("price").getAsString());

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setId(Integer.parseInt(productId));

        try{
            ProductDAO productDAO=new ProductDAO();
            productDAO.update(product);
            Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "okay");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            Lib.sendJSONResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR, "something went wrong");
        }

    }
}
