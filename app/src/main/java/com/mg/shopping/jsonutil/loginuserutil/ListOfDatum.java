
package com.mg.shopping.jsonutil.loginuserutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("favourite_detail")
    @Expose
    private List<FavouriteDetail> favouriteDetail = null;
    @SerializedName("following_detail")
    @Expose
    private List<FollowingDetail> followingDetail = null;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<FavouriteDetail> getFavouriteDetail() {
        return favouriteDetail;
    }

    public void setFavouriteDetail(List<FavouriteDetail> favouriteDetail) {
        this.favouriteDetail = favouriteDetail;
    }

    public List<FollowingDetail> getFollowingDetail() {
        return followingDetail;
    }

    public void setFollowingDetail(List<FollowingDetail> followingDetail) {
        this.followingDetail = followingDetail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.picture);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailAddress);
        dest.writeString(this.phoneNumber);
        dest.writeTypedList(this.favouriteDetail);
        dest.writeTypedList(this.followingDetail);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.picture = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.emailAddress = in.readString();
        this.phoneNumber = in.readString();
        this.favouriteDetail = in.createTypedArrayList(FavouriteDetail.CREATOR);
        this.followingDetail = in.createTypedArrayList(FollowingDetail.CREATOR);
    }

    public static final Creator<ListOfDatum> CREATOR = new Creator<ListOfDatum>() {
        @Override
        public ListOfDatum createFromParcel(Parcel source) {
            return new ListOfDatum(source);
        }

        @Override
        public ListOfDatum[] newArray(int size) {
            return new ListOfDatum[size];
        }
    };
}
