package org.example.mainpriject.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart_item")
public class CartItem {
    private String productId; // ID товару
    private int quantity;
}
