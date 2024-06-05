package com.example.handmakeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rate implements Parcelable {
    private int userId;
    private int starRatings;
    private String comment;
    private long createDate;

    public Rate(int userId, int starRatings, String comment, long createDate) {
        this.userId = userId;
        this.starRatings = starRatings;
        this.comment = comment;
        this.createDate = createDate;
    }
    protected Rate(Parcel in){
        userId = in.readInt();
        starRatings = in.readInt();
        comment = in.readString();
        createDate = in.readLong();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        return "Rate{}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(starRatings);
        dest.writeString(comment);
        dest.writeLong(createDate);
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
