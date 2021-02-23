package com.mg.shopping.jsonutil.answerutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.specificproductquestionsutil.Answer;

public class AnswerJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<Answer> answerList = null;

    public AnswerJson() {
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

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public AnswerJson setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.answerList);
    }

    protected AnswerJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.answerList = in.createTypedArrayList(Answer.CREATOR);
    }

    public static final Creator<AnswerJson> CREATOR = new Creator<AnswerJson>() {
        @Override
        public AnswerJson createFromParcel(Parcel source) {
            return new AnswerJson(source);
        }

        @Override
        public AnswerJson[] newArray(int size) {
            return new AnswerJson[size];
        }
    };
}
