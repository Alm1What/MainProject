package org.example.mainpriject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private List<String> imageUrls;
    private LocalDate createdAt;
    private String userNameCreated;


    public Product() {
    }

    public Product(String id, String name, String description, double price, int stockQuantity, List<String> imageUrls, LocalDate createdAt, String userNameCreated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrls = imageUrls;
        this.createdAt = createdAt;
        this.userNameCreated = userNameCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserNameCreated() {
        return userNameCreated;
    }

    public void setUserNameCreated(String userNameCreated) {
        this.userNameCreated = userNameCreated;
    }
}
