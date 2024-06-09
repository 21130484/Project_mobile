package model;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private int sellingPrice;
    /*
        stock = quantity - soldout.
     */
    private int stock;
    private int categoryId;
    private int discountId;
    private int isSale;


    private List<Image> imageList;
    private List<Rate> rateList;




}
