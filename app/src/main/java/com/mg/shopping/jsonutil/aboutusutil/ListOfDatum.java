package com.mg.shopping.jsonutil.aboutusutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("detail")
    @Expose
    private String detail;




    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public ListOfDatum setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public ListOfDatum setDetail(String detail) {
        this.detail = detail;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.subject);
        dest.writeString(this.detail);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.subject = in.readString();
        this.detail = in.readString();
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
