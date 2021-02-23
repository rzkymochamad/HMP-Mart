package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;

    RelativeLayout layoutProfile;
    RelativeLayout layoutPolicy;
    RelativeLayout layoutAbout;
    RelativeLayout layoutRate;
    RelativeLayout layoutShare;
    RelativeLayout layoutCountry;
    RelativeLayout layoutSignOut;

    TextView txtCountry;

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
        return R.layout.activity_setting;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.setting));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutProfile = (RelativeLayout) findViewById(R.id.layout_profile);
        layoutPolicy = (RelativeLayout) findViewById(R.id.layout_policy);
        layoutAbout = (RelativeLayout) findViewById(R.id.layout_about);
        layoutRate = (RelativeLayout) findViewById(R.id.layout_rate);
        layoutShare = (RelativeLayout) findViewById(R.id.layout_share);
        layoutCountry = (RelativeLayout) findViewById(R.id.layout_country);
        layoutSignOut = (RelativeLayout) findViewById(R.id.layout_sign_out);

        txtCountry = findViewById(R.id.txt_country);

        layoutProfile.setOnClickListener(this);
        layoutPolicy.setOnClickListener(this);
        layoutAbout.setOnClickListener(this);
        layoutRate.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        layoutCountry.setOnClickListener(this);
        layoutSignOut.setOnClickListener(this);
        txtCountry.setOnClickListener(this);
        imageBack.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {

        if (v == imageBack) {
            finish();
        }
        if (v == layoutPolicy) {

            Intent intent = new Intent(context, ProductDescription.class);
            intent.putExtra(Constant.IntentKey.DATA_OBJECT, new DataObject()
                    .setType("privacy_policy"));
            startActivity(intent);

        }
        if (v == layoutAbout) {

            Intent intent = new Intent(context, ProductDescription.class);
            intent.putExtra(Constant.IntentKey.DATA_OBJECT, new DataObject()
                    .setType("about_us"));
            startActivity(intent);

        }
        if (v == layoutSignOut) {

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(false));

            setResult(RESULT_OK);
            finish();

        }
        if (v == layoutRate) {

            Utility.rateApp(context);

        }
        if (v == layoutShare) {

            Utility.shareApp(context);

        }
        if (v == layoutProfile){

            Intent intent = new Intent(context, UserProfile.class);
            startActivity(intent);

        }

    }


}
