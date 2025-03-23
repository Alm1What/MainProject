package org.example.mainpriject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
    private String userId; // ID користувача, до якого належить кошик
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(String id, String userId, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        this.cartItems = cartItems;
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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
