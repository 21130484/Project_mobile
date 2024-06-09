package com.example.handmakeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductDetail implements Parcelable {
    private int id;
    private String name;
    private String description;
    private int sellingPrice;

    private int stock;
    private int categoryId;
    private int discountId;
    private int isSale;
    private List<Image> imageList;
    private List<Rate> rateList;

    public ProductDetail(int id, String name, String description, int sellingPrice, int stock, int categoryId, int discountId, int isSale, List<Image> imageList, List<Rate> rateList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
        this.categoryId = categoryId;
        this.discountId = discountId;
        this.isSale = isSale;
        this.imageList = imageList;
        this.rateList = rateList;
    }

    protected ProductDetail(Parcel in) {
        id = in.readInt();
        name = in.readString();
       description =  in.readString();
       sellingPrice = in.readInt();
        stock = in.readInt();
        categoryId = in.readInt();
        discountId = in.readInt();
        isSale = in.readInt();
        imageList = in.createTypedArrayList(Image.CREATOR);
        rateList = in.createTypedArrayList(Rate.CREATOR);
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

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public List<Rate> getRateList() {
        return rateList;
    }

    public void setRateList(List<Rate> rateList) {
        this.rateList = rateList;
    }

    public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
        @Override
        public ProductDetail createFromParcel(Parcel source) {
            return new ProductDetail(source);
        }


        @Override
        public ProductDetail[] newArray(int size) {
            return new ProductDetail[size];
        }
    };

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", stock=" + stock +
                ", categoryId=" + categoryId +
                ", discountId=" + discountId +
                ", isSale=" + isSale +
                ", imageList=" + imageList +
                ", rateList=" + rateList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(sellingPrice);
        dest.writeInt(stock);
        dest.writeInt(categoryId);
        dest.writeInt(discountId);
        dest.writeInt(isSale);
        dest.writeTypedList(imageList);
        dest.writeTypedList(rateList);
    }
}
