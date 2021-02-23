package com.mg.shopping.dateutil;

import com.mg.shopping.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>It is used to generate date of specific format
 * <p>
 * For patterns , visit https://developer.android.com/reference/java/text/SimpleDateFormat#examples
 * </p>
 */
public class DateBuilder {
    private String tag = DateBuilder.class.getSimpleName();
    private long givenDateTimeInLong = 0;
    private String givenDateTime;
    private String givenFormat;
    private String outputFormat;
    private Date date = null;
    private String result;


    public DateBuilder() {
        // do nothing
    }


    public DateBuilder setGivenDateTime(String givenDateTime) {
        this.givenDateTime = givenDateTime;
        return this;
    }

    public DateBuilder setGivenDateTimeInLong(long givenDateTimeInLong) {
        this.givenDateTimeInLong = givenDateTimeInLong;
        return this;
    }

    public DateBuilder setGivenFormat(String givenFormat) {
        this.givenFormat = givenFormat;
        return this;
    }

    public DateBuilder setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public DateBuilder buildDate() {

        if (!Utility.isEmptyString(givenDateTime)) {

            try {
                date = new SimpleDateFormat(givenFormat).parse(givenDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else if (givenDateTimeInLong != 0) {

            date = new Date(givenDateTimeInLong);

        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputFormat);
        result = simpleDateFormat.format(date);

        return this;
    }

    @Override
    public String toString() {
        return "DateBuilder{" +
                "tag='" + tag + '\'' +
                ", givenDateTimeInLong=" + givenDateTimeInLong +
                ", givenDateTime='" + givenDateTime + '\'' +
                ", givenFormat='" + givenFormat + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", date=" + date +
                ", result='" + result + '\'' +
                '}';
    }
}
