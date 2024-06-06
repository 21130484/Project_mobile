package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private int userId;
    private double totalPrice;
    private Timestamp orderDate;
    private String status;
    private String consigneeName;
    private String consigneePhoneNumber;
    private String address;
    private double shippingFee;
    private String note;
    private List<OrderItem> itemList;
}