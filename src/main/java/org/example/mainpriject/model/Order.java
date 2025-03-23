package org.example.mainpriject.model;

import org.example.mainpriject.enum_model.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "order")
public class Order {

    @Id
    private String id;
    private String userId; // ID користувача, який зробив замовлення
    private List<OrderItem> orderItems;
    private double totalPrice;
    private Status status; // Статус замовлення (наприклад, "PENDING", "SHIPPED", "DELIVERED")
    private String shippingAddress;

    public Order() {
    }

    public Order(String id, String userId, List<OrderItem> orderItems, double totalPrice, Status status, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
