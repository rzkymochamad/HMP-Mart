package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.jsonutil.aboutusutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDescription extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;
    WebView webviewAbout;
    DataObject dataObject;
    String type;
    String functionalityType = "privacy_policy";
    

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
        return R.layout.activity_about_us;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        webviewAbout = findViewById(R.id.webview_about);

        type = dataObject.getType();


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality(type.equalsIgnoreCase(functionalityType) ? "retrieve_privacy_policy" : "retrieve_about_us")
                        .setConnection(type.equalsIgnoreCase(functionalityType) ? Constant.CONNECTION.PRIVACY_POLICY
                                : Constant.CONNECTION.ABOUT_US)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection( type.equalsIgnoreCase(functionalityType) ? Constant.CONNECTION.PRIVACY_POLICY
                        : Constant.CONNECTION.ABOUT_US ));


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

        final DataObject dtObject = (DataObject) data;
        ListOfDatum datum = (ListOfDatum) dtObject.getObjectList().get(0);
        txtMenu.setText(datum.getSubject());
        webviewAbout.loadData(datum.getDetail()
                    , "text/html", "UTF-8");


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

    }



}
