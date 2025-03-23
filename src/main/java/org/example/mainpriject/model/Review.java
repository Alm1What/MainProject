package org.example.mainpriject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String productId; // ID товару, до якого належить відгук
    private String userId; // ID користувача, який залишив відгук
    private int rating; // Рейтинг (наприклад, від 1 до 5)
    private String comment;

    public Review() {
    }

    public Review(String id, String productId, String userId, int rating, String comment) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
