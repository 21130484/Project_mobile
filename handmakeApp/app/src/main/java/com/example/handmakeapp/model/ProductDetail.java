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
    private List<Image> imageList;
    private List<Rate> rateList;

    public ProductDetail(int id, String name, String description, int sellingPrice, List<Image> imageList, List<Rate> rateList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.imageList = imageList;
        this.rateList = rateList;
    }

    protected ProductDetail(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description =  in.readString();
        this.sellingPrice = in.readInt();
        this.imageList = in.createTypedArrayList(Image.CREATOR);
        this.rateList = in.createTypedArrayList(Rate.CREATOR);
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

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", imageList=" + imageList +
                ", rateList=" + rateList +
                '}';
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(sellingPrice);
        dest.writeTypedList(imageList);
        dest.writeTypedList(rateList);
    }
}
