package nsbm.dea.web.orders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsbm.dea.web.dao.CategoryDAO;
import nsbm.dea.web.dao.OrderDAO;
import nsbm.dea.web.dao.deliveryDetailsDAO;
import nsbm.dea.web.lib.Lib;
import nsbm.dea.web.models.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet( name="product_orders", value ="/orders/create" )
public class Create extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = "01HW2TMW12DAVYJ6QGAWF6ZGKV";

            JsonObject payload = Lib.getJSONPayloadFromRequest(request);
            JsonArray cartDataArray = payload.getAsJsonArray("cartData");

            deliveyDetails delivery= new deliveyDetails();
            delivery.setUserId(userId);
            delivery.setAddress(payload.get("address").getAsString());
            delivery.setCity(payload.get("city").getAsString());
            delivery.setProvince(payload.get("province").getAsString());
            delivery.setCity(payload.get("city").getAsString());
            delivery.setPostalCode(payload.get("postalCode").getAsString());

            deliveryDetailsDAO deliveryDetailsDAO= new deliveryDetailsDAO();
            String deliveryId=deliveryDetailsDAO.createDeliveryDetail(delivery);

            double totalPrice = 0;
            int totalQuantity = 0;
            for (JsonElement cartItemElement : cartDataArray) {
                JsonObject cartItemObject = cartItemElement.getAsJsonObject();
                totalPrice += cartItemObject.get("subTotal").getAsDouble();
                totalQuantity += cartItemObject.get("quantity").getAsInt();
            }

            Order order = new Order();
            order.setOrderedBy(userId);
            order.setDeliveryAddress(deliveryId);
            order.setStatus("processing");
            order.setQty(totalQuantity);
            order.setTotal(totalPrice);

            OrderDAO orderDAO = new OrderDAO();
            int orderId = orderDAO.createOrder(order);

            for (JsonElement cartItemElement : cartDataArray) {
                JsonObject cartItemObject = cartItemElement.getAsJsonObject();
                UserOrder userOrder = new UserOrder();
                userOrder.setOrderId(orderId);
                userOrder.setProductId(cartItemObject.get("id").getAsInt());
                userOrder.setCategoryId(cartItemObject.get("categoy").getAsJsonObject().get("id").getAsInt());
                userOrder.setColorId(cartItemObject.get("color").getAsJsonObject().get("id").getAsInt());
                userOrder.setPrice(cartItemObject.get("price").getAsBigDecimal());
                userOrder.setQuantity(cartItemObject.get("quantity").getAsInt());

                orderDAO.createUserOrder(userOrder);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Order created successfully\"}");

        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating order");
        }
    }
}




