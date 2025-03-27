package org.example.mainpriject.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "reviews")
public class Reviews {

    @Id
    private String id;

    private String reviewerName;
    private String reviews_content;

    @Min(0)
    @Max(5)
    private int rating;
    private LocalDate reviews_date;

    public Reviews() {
    }

    public Reviews(String id, String reviewerName, String reviews_content, int rating, LocalDate reviews_date) {
        this.id = id;
        this.reviewerName = reviewerName;
        this.reviews_content = reviews_content;
        this.rating = rating;
        this.reviews_date = reviews_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviews_content() {
        return reviews_content;
    }

    public void setReviews_content(String reviews_content) {
        this.reviews_content = reviews_content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReviews_date() {
        return reviews_date;
    }

    public void setReviews_date(LocalDate reviews_date) {
        this.reviews_date = reviews_date;
    }
}
