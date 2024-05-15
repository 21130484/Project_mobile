package model.bean;

public class Product {
    private int id;
    private String name;
    private String description;
    private double costPrice;
    private double sellingPrice;
    private int quantity;
    private int soldout;
    private int categoryId;
    private int discountId;
    private int isSale;

    public Product() {
    }

    public Product(String name, String description, double costPrice, double sellingPrice, int quantity, int soldout, int categoryId, int isSale) {
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.soldout = soldout;
        this.categoryId = categoryId;
        this.isSale = isSale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldout() {
        return soldout;
    }

    public void setSoldout(int soldout) {
        this.soldout = soldout;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", status='" + soldout + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", discountId='" + discountId + '\'' +
                '}';
    }
}
