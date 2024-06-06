package com.example.handmakeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

  private int id;



   @Expose(deserialize = false)
  private String name;
  private String path;


    @Expose(deserialize = false)
  private int productId;


    public Image(int id, String name, String path, int productId) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.productId = productId;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        productId = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", productId=" + productId +
                '}';
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
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
      dest.writeString(path);
      dest.writeInt(productId);

    }
}
