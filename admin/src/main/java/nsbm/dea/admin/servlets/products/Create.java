package nsbm.dea.admin.servlets.products;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.admin.dao.CategoryDAO;
import nsbm.dea.admin.enums.Status;
import nsbm.dea.admin.lib.Lib;
import nsbm.dea.admin.model.Product;
import nsbm.dea.admin.dao.ProductDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "product_create",value = "/product/create")
public class Create extends HttpServlet {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public Create(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
           response.setContentType("application/json");
           JsonObject object=Lib.getJSONPayloadFromRequest(request);


           String name = object.get("name").getAsString();
           String description = object.get("description").getAsString();
           JsonArray photos=object.getAsJsonArray("photos");
           List<String> photoUrls=new ArrayList<>();
           for(JsonElement photo:photos){
               String photoName=photo.getAsString();
                photoUrls.add(photoName);
           }

           String[] photoUrlArray=photoUrls.toArray(new String[0]);

           Product product=new Product();
           product.setName(name);
           product.setDescription(description);
           product.setPhotoUrls(photoUrlArray);


           boolean isProductAdded=productDAO.addProduct(product);


           if(isProductAdded){
               Lib.sendJSONResponse(response, HttpServletResponse.SC_OK, Status.OK, "created the product  sucessfully");
//               JsonObject sizesObject = object.getAsJsonObject("sizes");
//               for (String size : sizesObject.keySet()) {
//                   Categories newCategory=new Categories();
//                   newCategory.setName(size);
//                   newCategory.setCreatedBy(createBy);
//                   newCategory.setProductId(produt);
//
//               }
           }


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
