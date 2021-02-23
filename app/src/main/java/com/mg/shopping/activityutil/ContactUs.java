package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;


public class ContactUs extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;
    RoundKornerLinearLayout layoutSave;
    EditText editSubject;
    EditText editMessage;


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
        return R.layout.activity_contact_us;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.contact_us));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editSubject = (EditText) findViewById(R.id.edit_subject);
        editMessage = (EditText) findViewById(R.id.edit_message);

        layoutSave = (RoundKornerLinearLayout) findViewById(R.id.layout_save);

        layoutSave.setOnClickListener(this);
        imageBack.setOnClickListener(this);

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
            jsonObject.accumulate("user_id", jsObject.getUserId());
            jsonObject.accumulate("subject", jsObject.getSubject());
            jsonObject.accumulate("detail", jsObject.getDetail());



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

        if (requestObject.getConnection() == Constant.CONNECTION.CONTACT_US) {


            finish();

        }


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
        if (v == layoutSave) {

            if (Utility.isEmptyString(editSubject.getText().toString())){
                Utility.Toaster(context,
                        Utility.getStringFromRes(context,R.string.empty_subject)
                ,Toast.LENGTH_SHORT);
                return;

            }

            if (Utility.isEmptyString(editMessage.getText().toString())){
                Utility.Toaster(context,
                        Utility.getStringFromRes(context,R.string.empty_message)
                        ,Toast.LENGTH_SHORT);
                return;

            }

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("add_contact_us")
                            .setConnection(Constant.CONNECTION.CONTACT_US)
                            .setSubject(editSubject.getText().toString())
                            .setDetail(editMessage.getText().toString())
                            .setUserId(BottomNavigationSample.getUserId())))
                    .setLoadingText(Utility.getStringFromRes(context,R.string.adding))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.CONTACT_US));

        }

    }



}
