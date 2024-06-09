package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {
  private int id, productId, quantity, cartId;
  private String name, description, PATH;
  private double sellingPrice;
}