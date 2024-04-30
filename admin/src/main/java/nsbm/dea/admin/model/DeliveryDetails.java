package nsbm.dea.admin.model;

public class DeliveryDetails {
    private String id;
    private String userId;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    public DeliveryDetails(String id, String userId, String address, String city, String province, String postalCode) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public DeliveryDetails() {

    }

    public DeliveryDetails(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
