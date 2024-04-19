package nsbm.dea.admin.servlets.products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.ProductDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Product;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "product_delete",value = "/product/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String adminID="01HVT9ZYMRTVGZRGJFXMMCMY8Y";

            Product newProduct = new Product();
            newProduct.setId(id);

            ProductDAO newProductDAO = new ProductDAO();
            newProductDAO.deleteProduct(newProduct);

            Lib.sendJSONResponse(response,HttpServletResponse.SC_OK, Status.OK,"product deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
