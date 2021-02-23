package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class AddBillingAddress extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    private ImageView imageBack;
    ImageView imageOther;
    private EditText editAddress;
    private EditText editApartment;
    private EditText editCity;
    private EditText editPostalCode;
    private EditText editPhone;
    private TextView txtCountry;
    private EditText editNote;
    private RoundKornerLinearLayout layoutSave;


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
        return R.layout.activity_add_billing_address;
    }

    @Override
    protected void getIntentData() {
        //Do nothing , use it in case of needed intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.add_shipping_detail));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);
        
        layoutSave = findViewById(R.id.layout_save);

        txtCountry = (TextView) findViewById(R.id.txt_country);
        
        editAddress = (EditText) findViewById(R.id.edit_address);
        editApartment = (EditText) findViewById(R.id.edit_apartment);
        editCity = (EditText) findViewById(R.id.edit_city);
        editPostalCode = (EditText) findViewById(R.id.edit_postal_code);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editNote = (EditText) findViewById(R.id.edit_note);
        

        layoutSave.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());
            jsonObject.accumulate("user_id", jsObject.getUserId());
            jsonObject.accumulate("address", jsObject.getAddress());
            jsonObject.accumulate("apartment", jsObject.getApartment());
            jsonObject.accumulate("city", jsObject.getCity());
            jsonObject.accumulate("postal_code", jsObject.getPostalCode());
            jsonObject.accumulate("country", jsObject.getCountry());
            jsonObject.accumulate("phone", jsObject.getPhone());
            jsonObject.accumulate("note", jsObject.getNote());


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

        if (requestObject.getConnection() == Constant.CONNECTION.SAVE_ADDRESS){

            setResult(RESULT_OK);
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
        if (v==layoutSave){

            if (Utility.isEmptyString(editAddress.getText().toString())){
                return;
            }

            if (Utility.isEmptyString(editApartment.getText().toString())){
                return;
            }

            if (Utility.isEmptyString(editCity.getText().toString())){
                return;
            }

            if (Utility.isEmptyString(editPostalCode.getText().toString())){
                return;
            }

            if (Utility.isEmptyString(editPhone.getText().toString())){
                return;
            }

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("add_billing_address")
                            .setConnection(Constant.CONNECTION.SAVE_ADDRESS)
                            .setAddress(editAddress.getText().toString())
                            .setApartment(editApartment.getText().toString())
                            .setCity(editCity.getText().toString())
                            .setPostalCode(editPostalCode.getText().toString())
                            .setCountry(txtCountry.getText().toString())
                            .setPhone(editPhone.getText().toString())
                            .setNote(editNote.getText().toString())
                            .setUserId( BottomNavigationSample.getUserId() )))
                    .setLoadingText(Utility.getStringFromRes(context,R.string.adding))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.SAVE_ADDRESS));

        }

    }


}
