package com.example.handmakeapp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Order implements Serializable {
    private int id;
    private double totalPrice;
    private String status;
    private String consigneeName;
    private String consigneePhoneNumber;
    private String address;

    private List<OrderItem> itemList;

    public Order(int id, double totalPrice, String status, String consigneeName, String consigneePhoneNumber, String address, List<OrderItem> itemList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.consigneeName = consigneeName;
        this.consigneePhoneNumber = consigneePhoneNumber;
        this.address = address;
        this.itemList = itemList;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhoneNumber() {
        return consigneePhoneNumber;
    }

    public void setConsigneePhoneNumber(String consigneePhoneNumber) {
        this.consigneePhoneNumber = consigneePhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", consigneeName='" + consigneeName + '\'' +
                ", phoneNumber='" + consigneePhoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
