package nsbm.dea.admin.servlets.products;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.lib.Lib;

import java.io.IOException;

@WebServlet(name = "product_update",value = "/product/update")
public class Update extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        JsonObject object = Lib.getJSONPayloadFromRequest(request);
        String name = object.get("name").getAsString();
        String description = object.get("description").getAsString();
        


    }
}
