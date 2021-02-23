package com.mg.shopping.objectutil;

import android.os.Parcel;
import android.os.Parcelable;

public class UserObject implements Parcelable {
    int userId;
    String userName;
    String userPicture;
    double userLatitude;
    double userLongitude;


    public UserObject(int userId, String userName, String userPicture, double userLatitude, double userLongitude) {
        this.userId = userId;
        this.userName = userName;
        this.userPicture = userPicture;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    public int getUserId() {
        return userId;
    }

    public UserObject setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserObject setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public UserObject setUserPicture(String userPicture) {
        this.userPicture = userPicture;
        return this;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public UserObject setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
        return this;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public UserObject setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
        return this;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userLatitude=" + userLatitude +
                ", userLongitude=" + userLongitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPicture);
        dest.writeDouble(this.userLatitude);
        dest.writeDouble(this.userLongitude);
    }

    protected UserObject(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.userPicture = in.readString();
        this.userLatitude = in.readDouble();
        this.userLongitude = in.readDouble();
    }

    public static final Creator<UserObject> CREATOR = new Creator<UserObject>() {
        @Override
        public UserObject createFromParcel(Parcel source) {
            return new UserObject(source);
        }

        @Override
        public UserObject[] newArray(int size) {
            return new UserObject[size];
        }
    };
}
