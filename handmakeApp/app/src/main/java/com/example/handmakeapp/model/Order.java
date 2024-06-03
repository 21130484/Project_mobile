package com.example.handmakeapp.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private double totalPrice;
    private String status;
    private List<OrderItem> itemList;

    public Order(int id, double totalPrice, String status, List<OrderItem> itemList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
