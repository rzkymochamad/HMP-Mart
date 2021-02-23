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

import com.mg.shopping.base64util.Base64Builder;
import com.mg.shopping.base64util.Base64Object;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.databaseutil.DbConstraint;
import com.mg.shopping.databaseutil.TypeConstraint;
import com.mg.shopping.jsonutil.loginuserutil.FavouriteDetail;
import com.mg.shopping.jsonutil.loginuserutil.FollowingDetail;
import com.mg.shopping.jsonutil.loginuserutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.widget.AppCompatCheckBox;

public class Login extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;

    EditText editPhone;
    EditText editPassword;
    AppCompatCheckBox checkboxRemember;
    LinearLayout layoutSignIn;
    TextView txtLogin;
    TextView txtSignUp;
    String loginType;

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
        return R.layout.activity_login;
    }

    @Override
    protected void getIntentData() {
        //do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editPhone = (EditText) findViewById(R.id.edit_phone);
        editPassword = (EditText) findViewById(R.id.edit_password);
        checkboxRemember = (AppCompatCheckBox) findViewById(R.id.checkbox_remember);
        layoutSignIn = (LinearLayout) findViewById(R.id.layout_sign_in);

        txtSignUp = findViewById(R.id.txt_sign_up);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        if (management.getPreferences(null).isUserRemember()) {

            Base64Builder base64Builder = new Base64Builder();
            base64Builder.setBase64Object(new Base64Object()
                    .setText(management.getPreferences(null).getUserPassword()));

            editPhone.setText(management.getPreferences(null).getUserEmail());
            editPassword.setText(base64Builder.decodedBase64StringIntoString());
            checkboxRemember.setChecked(true);

        }

        imageBack.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        layoutSignIn.setOnClickListener(this);


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
            jsonObject.accumulate("user_phone_email", jsObject.getUserPhone());
            jsonObject.accumulate("password", jsObject.getPassword());
            jsonObject.accumulate("login_type", jsObject.getType());


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

        if (!dtObject.getObjectList().isEmpty()) {

            ListOfDatum datum = (ListOfDatum) dtObject.getObjectList().get(0);

            Utility.Logger(tag, "User ID = " + datum.getId() + " First Name = " + datum.getFirstName()
                    + " Last Name = " + datum.getLastName() + " Email Address = " + datum.getEmailAddress()
                    + " Phone  = " + datum.getPhoneNumber() + " Picture = " + datum.getPicture());


            ///For saving Favourites into Local Db

            for (int i = 0; i < datum.getFavouriteDetail().size(); i++) {

                FavouriteDetail favouriteDetail = datum.getFavouriteDetail().get(i);

                if (favouriteDetail.getKey().equalsIgnoreCase("favourite_store")) {

                    management.getDataFromDatabase(new DatabaseObject()
                            .setTypeOperation(TypeConstraint.FAVOURITES)
                            .setDbOperation(DbConstraint.INSERT_NEW_FAVOURITES_STORE)
                            .setDataObject(new DataObject()
                                    .setId(favouriteDetail.getValue())
                                    .setUserId(BottomNavigationSample.getUserId())));

                } else {

                    management.getDataFromDatabase(new DatabaseObject()
                            .setTypeOperation(TypeConstraint.FAVOURITES)
                            .setDbOperation(DbConstraint.INSERT_NEW_FAVOURITES_PRODUCT)
                            .setDataObject(new DataObject()
                                    .setId(favouriteDetail.getValue())
                                    .setUserId(BottomNavigationSample.getUserId())
                                    .setType("favourite_product")));

                }

            }

            //For saving Following into local db

            for (int i = 0; i < datum.getFollowingDetail().size(); i++) {

                FollowingDetail followingDetail = datum.getFollowingDetail().get(i);

                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(TypeConstraint.BRAND)
                        .setDbOperation(DbConstraint.INSERT_NEW_BRAND_FOLLOWING)
                        .setDataObject(new DataObject()
                                .setId(followingDetail.getValue())
                                .setUserId(BottomNavigationSample.getUserId())));

            }

            //For saving User Credentials

            management.savePreferences(new PrefObject()
                    .setLogin(true)
                    .setSaveLogin(true));

            management.savePreferences(new PrefObject()
                    .setUserRemember( checkboxRemember.isChecked() )
                    .setSaveUserRemember(true));

            Base64Builder base64Builder = new Base64Builder();
            base64Builder.setBase64Object(new Base64Object()
                    .setText(editPassword.getText().toString()));


            management.savePreferences(new PrefObject()
                    .setSaveUserCredential(true)
                    .setUserId(datum.getId())
                    .setUserName(datum.getFirstName() + " " + datum.getLastName())
                    .setLoginType(loginType)
                    .setUserEmail(editPhone.getText().toString())
                    .setUserPassword(base64Builder.encodedStringIntoBase64String())
                    .setPictureUrl(datum.getPicture()));

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
        if (v == txtSignUp) {
            startActivity(new Intent(context, SignUp.class));
        }
        if (v == layoutSignIn) {
//            startActivity(new Intent(context, BottomNavigationSample.class));
            if (Utility.isEmptyString(editPhone.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_phone)
                        , Toast.LENGTH_SHORT);

                return;
            }


            if (Utility.isEmptyString(editPassword.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_password)
                        , Toast.LENGTH_SHORT);

                return;
            }


            if (Utility.isValidMail(editPhone.getText().toString())) {
                loginType = "email";
            } else if (Utility.isValidMobile(editPhone.getText().toString())) {
                loginType = "phone";
            }
//
            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("login_user")
                            .setConnection(Constant.CONNECTION.LOGIN)
                            .setUserPhone(editPhone.getText().toString())
                            .setPassword(editPassword.getText().toString())
                            .setType(loginType)))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context, R.string.login))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.LOGIN));

        }

    }

    @Override
    public void onBackPressed() {

        Utility.Logger(tag, "onBackPressed");
        setResult(RESULT_OK);
        finish();

    }


}
