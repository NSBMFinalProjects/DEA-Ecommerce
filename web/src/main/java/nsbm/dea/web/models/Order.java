package nsbm.dea.web.models;

import java.sql.Timestamp;

public class Order {
    private int id;
    private String orderedBy;
    private String deliveryAddress;
    private Timestamp created;
    private String status;
    private int qty;
    private double total;

    public Order(int id, String orderedBy, String deliveryAddress, Timestamp created, String status, int qty, double total) {
        this.id = id;
        this.orderedBy = orderedBy;
        this.deliveryAddress = deliveryAddress;
        this.created = created;
        this.status = status;
        this.qty = qty;
        this.total = total;
    }

    public Order() {

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
