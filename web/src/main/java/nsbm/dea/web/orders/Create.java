//package nsbm.dea.web.orders;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import nsbm.dea.web.dao.CategoryDAO;
//import nsbm.dea.web.dao.OrderDAO;
//import nsbm.dea.web.lib.Lib;
//import nsbm.dea.web.models.Order;
//import nsbm.dea.web.models.Product;
//import nsbm.dea.web.models.User;
//import nsbm.dea.web.models.UserOrder;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//
//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    try {
//        User user = ((User) request.getSession().getServletContext().getAttribute("User"));
//        JsonObject payload = Lib.getJSONPayloadFromRequest(request);
//
//        String userId = payload.get("user_id").getAsString();
//        Double totalPrice = payload.get("total").getAsDouble();
//
//        Order order = new Order();
//        order.setOrderedBy(userId);
//        order.setTotal(totalPrice);
//
//        OrderDAO orderDAO = new OrderDAO();
//        int orderId = orderDAO.createOrder(order); // Assuming this method sets the order ID
//
//        JsonArray productsArray = payload.getAsJsonArray("products");
//        for (JsonElement productElement : productsArray) {
//            JsonObject productObject = productElement.getAsJsonObject();
//
//            int productId = productObject.get("product_id").getAsInt();
//            String productName = productObject.get("product_name").getAsString();
//            BigDecimal price = productObject.get("price").getAsBigDecimal();
//            int quantity = productObject.get("quantity").getAsInt();
//            String CategoryName = productObject.get("Category_name").getAsString();
//
//            CategoryDAO categoryDAO = new CategoryDAO();
//            int categoryId = categoryDAO.getCategoryId(productId,CategoryName);
//            int colorId = getColorIdForProduct(productId);
//
//            UserOrder userOrder = new UserOrder();
//            userOrder.setOrderId(orderId);
//            userOrder.setProductId(productId);
//            userOrder.setCategoryId(categoryId);
//            userOrder.setColorId(colorId);
//            userOrder.setPrice(price);
//            userOrder.setQuantity(quantity);
//
//            orderDAO.createUserOrder(userOrder);
//        }
//
//        // Send a response back to the client
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"message\": \"Order created successfully\"}");
//
//    } catch (SQLException e) {
//        // Handle exception
//        e.printStackTrace();
//        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating order");
//    }
//}
//
