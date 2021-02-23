package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class DeeplinkViewer extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI();  //Initialize View


    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Context getContextInstance() {
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void getIntentData() {

        Intent mainIntent = getIntent();

        if (mainIntent != null && mainIntent.getData() != null
                && (mainIntent.getData().getScheme().equals("http") ||
                mainIntent.getData().getScheme().equals("https"))) {

            Uri data = mainIntent.getData();
            String id = data.getQueryParameter("id");
            String name = data.getQueryParameter("name");
            String price = data.getQueryParameter("price");
            String rating = data.getQueryParameter("rating");

            Utility.Logger(tag, "Manual = " + id + " Link = " +
                    data.toString());

            Intent intent = new Intent(context, SplashSample.class);
            intent.putExtra(Constant.IntentKey.DATA_OBJECT,
                    new DataObject()
                            .setId(id)
                            .setName(name)
                            .setPrice(price)
                            .setRating(rating)
                            .setDeeplink(true));
            startActivity(intent);
            finish();


        }

    }

    @Override
    protected void initUI() {
        // do nothing ,  needed in case of receiving intent data
    }

    @Override
    protected int initAdmobBannerAds() {
        return R.id.layout_ad;
    }


    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (!Utility.isEmptyString(jsObject.getSkipIds()))
                jsonObject.accumulate("skip_ids", jsObject.getSkipIds());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(tag + " Json", json);

        return json;
    }

    @Override
    protected void sendRequestToServer(RequestObject requestObject) {

        management.sendRequestToServer(requestObject
                .setConnectionCallback(this));

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        Utility.Logger(tag,requestObject.toString());


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

    }



}
