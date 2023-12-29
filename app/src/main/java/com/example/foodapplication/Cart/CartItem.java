package com.example.foodapplication.Cart;

public class CartItem {
    private String cartname, cartimageUrl;
    private int quantity;
    private double cartprice;
    private String key;

    public CartItem() {
    }

    public CartItem(String cartname, String cartimageUrl, int quantity, double cartprice) {
        this.cartname = cartname;
        this.cartimageUrl = cartimageUrl;
        this.quantity = quantity;
        this.cartprice = cartprice;
    }

    public String getKey() {
        return key;
    }

    // Setter method for the Firebase key (if needed)
    public void setKey(String key) {
        this.key = key;
    }

    public String getCartname() {
        return cartname;
    }

    public void setCartname(String cartname) {
        this.cartname = cartname;
    }

    public String getCartimageUrl() {
        return cartimageUrl;
    }

    public void setCartimageUrl(String cartimageUrl) {
        this.cartimageUrl = cartimageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCartprice() {
        return cartprice;
    }

    public void setCartprice(double cartprice) {
        this.cartprice = cartprice;
    }
}
