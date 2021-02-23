package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;

    EditText editFname;
    EditText editLname;
    EditText editPhone;
    EditText editEmail;
    EditText editPassword;
    EditText editConfirmPassword;

    LinearLayout layoutSignUp;
    TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI();  //Initialize View


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
    protected int getContentView() {
        return R.layout.activity_signup;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editFname = (EditText) findViewById(R.id.edit_fname);
        editLname = (EditText) findViewById(R.id.edit_lname);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        layoutSignUp = (LinearLayout) findViewById(R.id.layout_sign_up);

        txtLogin = findViewById(R.id.txt_login);


        imageBack.setOnClickListener(this);
        layoutSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

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

            jsonObject.accumulate("first_name", jsObject.getFirstName());
            jsonObject.accumulate("last_name", jsObject.getLastName());
            jsonObject.accumulate("email_address", jsObject.getEmailAddress());
            jsonObject.accumulate("phone", jsObject.getUserPhone());
            jsonObject.accumulate("password", jsObject.getPassword());


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

        Utility.Logger(tag, "OnSuccess Sign Up");
        finish();


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);
        Utility.Toaster(context, data, Toast.LENGTH_SHORT);

    }

    @Override
    public void onClick(View v) {

        if (v == imageBack) {
            finish();
        }
        else if (v == txtLogin) {
            startActivity(new Intent(context, Login.class));
            finish();
        }
        else if (v == layoutSignUp && isRequiredFieldFilled()) {
            phoneAuth.showPhoneAuthenticationSheet(editPhone.getText().toString());
        }

    }

    @Override
    public void onBackPressed() {

        Utility.Logger(tag, "onBackPressed");
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onPhoneAuthSuccess() {
        super.onPhoneAuthSuccess();

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("register_user")
                        .setConnection(Constant.CONNECTION.SIGN_UP)
                        .setFirstName(editFname.getText().toString())
                        .setLastName(editLname.getText().toString())
                        .setEmailAddress(editEmail.getText().toString())
                        .setUserPhone(editPhone.getText().toString())
                        .setPassword(editPassword.getText().toString())))
                .setFirstRequest(true)
                .setLoadingText(Utility.getStringFromRes(context, R.string.sign_up))
                .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.SIGN_UP));

    }

    @Override
    public void onPhoneAuthError() {
        super.onPhoneAuthError();

        Utility.Toaster(context,
                Utility.getStringFromRes(context, R.string.failed_to_authenticate),
                Toast.LENGTH_SHORT);
    }

    /**
     * <p>It is used to check the required fields are filled or empty</p>
     */
    private boolean isRequiredFieldFilled() {

        if (Utility.isEmptyString(editFname.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_first_name)
                    , Toast.LENGTH_SHORT);

            return false;
        }
        if (Utility.isEmptyString(editLname.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_last_name)
                    , Toast.LENGTH_SHORT);

            return false;
        }
        if (Utility.isEmptyString(editEmail.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_email)
                    , Toast.LENGTH_SHORT);

            return false;
        }
        if (Utility.isEmptyString(editPhone.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_phone)
                    , Toast.LENGTH_SHORT);

            return false;
        }
        if (Utility.isEmptyString(editPassword.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_password)
                    , Toast.LENGTH_SHORT);

            return false;
        }
        if (Utility.isEmptyString(editConfirmPassword.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.empty_confirm_password)
                    , Toast.LENGTH_SHORT);

            return false;
        }

        if (!editPassword.getText().toString()
                .equalsIgnoreCase(editConfirmPassword.getText().toString())) {

            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.password_mis_match)
                    , Toast.LENGTH_SHORT);

            return false;
        }

        return true;

    }

}
