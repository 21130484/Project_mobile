package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int orderId;
    private double sellingPrice;
    private String name;
    private String description;
    private String path;
    private int quantity;


}
