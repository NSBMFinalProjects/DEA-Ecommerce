<%@ page import="nsbm.dea.web.dao.ProductDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="nsbm.dea.web.models.Product" %>
<%@ page import="java.io.IOException" %>
<%@ page import="com.google.gson.Gson" %>

<%
    String searchTerm = request.getParameter("searchTerm");
    ProductDAO productDao = new ProductDAO();
    List<Product> products = productDao.searchProducts(searchTerm);
    Gson gson = new Gson();
    String json = gson.toJson(products);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    try {
        response.getWriter().write(json);
    } catch (IOException e) {
        e.printStackTrace();
    }
%>
