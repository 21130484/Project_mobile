package com.example.handmakeapp.model;

public class CartItemDTO {
    private int id;
    private int cartId;
    private String name;
    private String description;
    private double sellingPrice;
    private String path;
    private int quantity;

    public CartItemDTO(int id, int cartId, String name, String description, double sellingPrice, String path, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.path = path;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", path='" + path + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
