
package com.mg.shopping.jsonutil.stylejsonutil;

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orientation")
    @Expose
    private String orientation;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("actions")
    @Expose
    private String actions;
    @SerializedName("setting")
    @Expose
    private List<Setting> setting = null;
    @SerializedName("banner")
    @Expose
    private List<Banner> banner = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public List<Setting> getSetting() {
        return setting;
    }

    public void setSetting(List<Setting> setting) {
        this.setting = setting;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.orientation);
        dest.writeString(this.type);
        dest.writeString(this.tags);
        dest.writeString(this.actions);
        dest.writeTypedList(this.setting);
        dest.writeTypedList(this.banner);
        dest.writeTypedList(this.data);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.orientation = in.readString();
        this.type = in.readString();
        this.tags = in.readString();
        this.actions = in.readString();
        this.setting = in.createTypedArrayList(Setting.CREATOR);
        this.banner = in.createTypedArrayList(Banner.CREATOR);
        this.data = in.createTypedArrayList(Datum.CREATOR);
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
