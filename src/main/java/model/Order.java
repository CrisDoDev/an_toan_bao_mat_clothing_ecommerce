package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private double totalMoney;
    private String status;
    private String shippingAddress;
    private Timestamp orderDate; 
    
    // Thêm các trường phục vụ chữ ký số
    private Integer keyId;
    private String digitalSignature;
    private String orderHash;
    private Timestamp signedAt;
    
    // Trường bổ sung để phục vụ hiển thị/verify (lôi từ DB join)
    private String publicKeyText;

    public Order() {
    }

    public Order(int id, int userId, double totalMoney, String status, String shippingAddress, Timestamp orderDate) {
        this.id = id;
        this.userId = userId;
        this.totalMoney = totalMoney;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalMoney() { return totalMoney; }
    public void setTotalMoney(double totalMoney) { this.totalMoney = totalMoney; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    // Getters and Setters cho chữ ký số
    public Integer getKeyId() { return keyId; }
    public void setKeyId(Integer keyId) { this.keyId = keyId; }

    public String getDigitalSignature() { return digitalSignature; }
    public void setDigitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }

    public String getOrderHash() { return orderHash; }
    public void setOrderHash(String orderHash) { this.orderHash = orderHash; }

    public Timestamp getSignedAt() { return signedAt; }
    public void setSignedAt(Timestamp signedAt) { this.signedAt = signedAt; }

    public String getPublicKeyText() { return publicKeyText; }
    public void setPublicKeyText(String publicKeyText) { this.publicKeyText = publicKeyText; }
}