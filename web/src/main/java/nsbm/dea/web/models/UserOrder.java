package nsbm.dea.web.models;

import java.math.BigDecimal;

public class UserOrder {
    private int id;
    private int orderId;
    private int productId;
    private int categoryId;
    private int colorId;
    private BigDecimal price;
    private int quantity ;

    public UserOrder(){

    }

    public UserOrder(int orderId, int productId, int categoryId, int colorId, BigDecimal price, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.price = price;
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getColorId() {
        return colorId;
    }
    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
