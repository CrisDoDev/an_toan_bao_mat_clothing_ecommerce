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
}