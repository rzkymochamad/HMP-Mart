package com.mg.shopping.jsonutil.specificproductquestionsutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificProductQuestionJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<ListOfDatum> listOfData = null;

    public SpecificProductQuestionJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListOfDatum> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<ListOfDatum> listOfData) {
        this.listOfData = listOfData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.listOfData);
    }

    protected SpecificProductQuestionJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
    }

    public static final Creator<SpecificProductQuestionJson> CREATOR = new Creator<SpecificProductQuestionJson>() {
        @Override
        public SpecificProductQuestionJson createFromParcel(Parcel source) {
            return new SpecificProductQuestionJson(source);
        }

        @Override
        public SpecificProductQuestionJson[] newArray(int size) {
            return new SpecificProductQuestionJson[size];
        }
    };
}
