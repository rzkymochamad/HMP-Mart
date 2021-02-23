
package com.mg.shopping.jsonutil.productratingutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.specificproductutil.ImageRating;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("image")
    @Expose
    private List<ImageRating> image = null;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    private boolean isUser;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public List<ImageRating> getImage() {
        return image;
    }

    public ListOfDatum setImage(List<ImageRating> image) {
        this.image = image;
        return this;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isUser() {
        return isUser;
    }

    public ListOfDatum setUser(boolean user) {
        isUser = user;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userImage);
        dest.writeString(this.name);
        dest.writeString(this.rating);
        dest.writeString(this.review);
        dest.writeString(this.avgRating);
        dest.writeTypedList(this.image);
        dest.writeString(this.dateCreated);
        dest.writeByte(this.isUser ? (byte) 1 : (byte) 0);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.userImage = in.readString();
        this.name = in.readString();
        this.rating = in.readString();
        this.review = in.readString();
        this.avgRating = in.readString();
        this.image = in.createTypedArrayList(ImageRating.CREATOR);
        this.dateCreated = in.readString();
        this.isUser = in.readByte() != 0;
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
