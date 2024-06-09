package com.example.handmakeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rate implements Parcelable {
    private String fullName;
    private int starRatings;
    private String comment;
    private long createDate;

    public Rate(String fullName, int starRatings, String comment, long createDate) {
        this.fullName = fullName;
        this.starRatings = starRatings;
        this.comment = comment;
        this.createDate = createDate;
    }

    public String getUserName() {
        return fullName;
    }

    public void setUserName(String userName) {
        this.fullName = userName;
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
                "userName='" + fullName + '\'' +
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
        dest.writeString(fullName);
        dest.writeInt(starRatings);
        dest.writeString(comment);
        dest.writeLong(createDate);
    }
    protected Rate(Parcel in){
        fullName = in.readString();
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
