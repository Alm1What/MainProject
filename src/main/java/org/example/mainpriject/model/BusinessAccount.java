package org.example.mainpriject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "business_account")
public class BusinessAccount {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    private Long id; // Id як у звичайному акаунті, потрібно постійно в User брати
    private String nameOfCompany;
    private List<Product> products;
    // https://www.baeldung.com/spring-boot-mongodb-auto-generated-field генератор для id там дуже просто написано можна використовувати
    private Long userId; // це id яке потрібно сетнути від користувача просто User а генерація id буде просто новий id щоб можна було шукати (для цього сервіс створив)

    @DBRef
    private List<Reviews> reviews;
    private LocalDateTime createdAt;

    public BusinessAccount() {
    }

    public BusinessAccount(Long id, String nameOfCompany, List<Product> products, Long userId, List<Reviews> reviews, LocalDateTime createdAt) {
        this.id = id;
        this.nameOfCompany = nameOfCompany;
        this.products = products;
        this.userId = userId;
        this.reviews = reviews;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
