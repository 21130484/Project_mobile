package com.example.handmakeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rate implements Parcelable {
    private String userName;
    private int starRatings;
    private String comment;
    private long createDate;

    public Rate(String userName, int starRatings, String comment, long createDate) {
        this.userName = userName;
        this.starRatings = starRatings;
        this.comment = comment;
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStarRatings() {
        return starRatings;
    }

    public void setStarRatings(int starRatings) {
        this.starRatings = starRatings;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "userName='" + userName + '\'' +
                ", starRatings=" + starRatings +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(starRatings);
        dest.writeString(comment);
        dest.writeLong(createDate);
    }
    protected Rate(Parcel in){
        userName = in.readString();
        starRatings = in.readInt();
        comment = in.readString();
        createDate = in.readLong();
    }


    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };
}
