package com.example.handmakeapp.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int orderId;
    private double sellingPrice;
    private String name;
    private String description;
    private String path;
    private int quantity;

    public OrderItem(int orderId, double sellingPrice, String name, String description, String path, int quantity) {
        this.orderId = orderId;
        this.sellingPrice = sellingPrice;
        this.name = name;
        this.description = description;
        this.path = path;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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
        return "OrderItem{" +
                "orderId=" + orderId +
                ", sellingPrice=" + sellingPrice +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
