package com.mg.shopping.activityutil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.jsonutil.cityjson.Configuration;
import com.mg.shopping.jsonutil.featuredcategoriesutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashSample extends BaseActivity {
    PrefObject prefObject;
    LinearLayout layoutConfigure;
    private String id;
    private String name;
    private String price;
    private String rating;
    private boolean isDeeplinkAction = false;
    DataObject dataObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        initUI(); //Initialize UI

    }

    @Override
    protected boolean isFullScreen() {
        return true;
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
    protected String[] requirePermission() {
//        return new String[]{
//                Manifest.permission.ACCESS_FINE_LOCATION
//                , Manifest.permission.ACCESS_COARSE_LOCATION
//                , Manifest.permission.CAMERA
//                , Manifest.permission.READ_EXTERNAL_STORAGE
//                , Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//        };
        return new String[]{};
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void getIntentData() {

       dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
       if (dataObject!=null){
           id = dataObject.getId();
           name = dataObject.getName();
           price = dataObject.getPrice();
           rating = dataObject.getRating();
           isDeeplinkAction = dataObject.isDeeplink();
       }


    }

    @Override
    protected void initUI() {

        Utility.Logger(tag, "Working");
        MobileAds.initialize(context, Constant.Credentials.ADMOB_APP_ID);


        layoutConfigure = findViewById(R.id.layout_configure);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveFirstLaunch(true));


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());


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

        DataObject dtObject = (DataObject) data;

        Utility.Logger(tag, String.valueOf(dtObject.getObjectList().size()));
        ArrayList<ListOfDatum> featuredList = new ArrayList<>();

        for (int i = 0; i < dtObject.getObjectList().size(); i++) {

            featuredList.add(((ListOfDatum) dtObject.getObjectList().get(i)));

        }

        Utility.Logger(tag, String.valueOf(featuredList.size()));

        if (isDeeplinkAction){
            processDeepLink(featuredList);
            return;
        }

        Intent intent = new Intent(context, BottomNavigationSample.class);
        intent.putExtra(Constant.IntentKey.DATA_OBJECT, featuredList);
        startActivity(intent);
        finish();

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

        Intent intent = new Intent(context, BottomNavigationSample.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onPermissionApproved() {
        super.onPermissionApproved();

        startActivity();

    }

    @Override
    protected void onPermissionReject() {
        super.onPermissionReject();
        Utility.Logger(tag, "PermissionReject");
    }

    /**
     * <p>It is used to check preference</p>
     */
    private void startActivity() {

        if (management.getPreferences(null).isFirstLaunch()) {

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_featured_categories")
                            .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES)))
                    .setFirstRequest(true)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES));

            return;
        }

        management.sendRequestToServer(new RequestObject()
                .setContext(context)
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_cities")))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.LIST_OF_CITIES)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {
                        DataObject dtObject = (DataObject) data;

                        ArrayList<Object> objectArrayList = new ArrayList<>();
                        objectArrayList.addAll(dtObject.getObjectList());

                        if (!objectArrayList.isEmpty()) {

                            management.savePreferences(new PrefObject()
                                    .setSaveFirstLaunch(true)
                                    .setFirstLaunch(true));

                            management.savePreferences(new PrefObject()
                                    .setSaveCountryInformation(true)
                                    .setCountryId("0")
                                    .setCurrencyCode(((Configuration) objectArrayList.get(0)).getValue())
                                    .setCurrencySymbol(((Configuration) objectArrayList.get(0)).getValue02()));

                            sendRequestToServer(new RequestObject()
                                    .setJson(getJson(new JsonObject()
                                            .setFunctionality("retrieve_featured_categories")
                                            .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES)))
                                    .setFirstRequest(true)
                                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                    .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES));


                        }


                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        Utility.Toaster(context,
                                Utility.getStringFromRes(context, R.string.failed_to_load_configuration)
                                , Toast.LENGTH_SHORT);
                    }
                }));


    }

    public void processDeepLink(List<ListOfDatum> list){

        Intent intent = new Intent(context, ProductDetail.class);
        intent.putExtra(Constant.IntentKey.TYPE,true);
        intent.putExtra(Constant.IntentKey.LIST,new ArrayList<>(list));
        intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setRating(rating));
        startActivity(intent);
        finish();

    }


}
