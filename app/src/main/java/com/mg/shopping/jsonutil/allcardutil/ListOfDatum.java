package com.mg.shopping.jsonutil.allcardutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("card_company_name")
    @Expose
    private String cardCompanyName;

    @SerializedName("card_no")
    @Expose
    private String cardNo;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("card_token")
    @Expose
    private String cardToken;


    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardCompanyName() {
        return cardCompanyName;
    }

    public ListOfDatum setCardCompanyName(String cardCompanyName) {
        this.cardCompanyName = cardCompanyName;
        return this;
    }

    public String getCardNo() {
        return cardNo;
    }

    public ListOfDatum setCardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public String getCardToken() {
        return cardToken;
    }

    public ListOfDatum setCardToken(String cardToken) {
        this.cardToken = cardToken;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cardCompanyName);
        dest.writeString(this.cardNo);
        dest.writeString(this.image);
        dest.writeString(this.cardToken);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.cardCompanyName = in.readString();
        this.cardNo = in.readString();
        this.image = in.readString();
        this.cardToken = in.readString();
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
