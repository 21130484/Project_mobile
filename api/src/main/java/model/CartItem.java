package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private int id;
    private int cartId;
    private String name;
    private String description;
    private double sellingPrice;
    private String PATH;
    private int quantity;

}