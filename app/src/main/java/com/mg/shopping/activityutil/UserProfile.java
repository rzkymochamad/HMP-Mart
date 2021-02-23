package com.mg.shopping.activityutil;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.jsonutil.userprofileutil.Detail;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;

    EditText editFname;
    EditText editLname;
    EditText editPhone;
    EditText editEmail;
    EditText editPassword;

    LinearLayout layoutUpdate;
    LinearLayout layoutProfile;

    ImageView imageProfile;
    boolean isPictureUpdate = false;
    String profilePictureBase64;
    String currentPassword;


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
        return R.layout.activity_user_profile;
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

        imageProfile = findViewById(R.id.image_profile);

        editFname = (EditText) findViewById(R.id.edit_fname);
        editLname = (EditText) findViewById(R.id.edit_lname);

        editPhone = (EditText) findViewById(R.id.edit_phone);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);

        layoutUpdate = (LinearLayout) findViewById(R.id.layout_update);
        layoutProfile = findViewById(R.id.layout_profile);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_profile_detail")
                        .setConnection(Constant.CONNECTION.RETRIEVE_PROFILE)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setFirstRequest(true)
                .setLoadingText(Utility.getStringFromRes(context, R.string.loading))
                .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_PROFILE));

        layoutUpdate.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);


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


            if (jsObject.getConnection() == Constant.CONNECTION.UPDATE_PROFILE) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("first_name", jsObject.getFirstName());
                jsonObject.accumulate("last_name", jsObject.getLastName());
                jsonObject.accumulate("password", jsObject.getPassword());

                if (!Utility.isEmptyString(profilePictureBase64))
                    jsonObject.accumulate("picture", profilePictureBase64);

            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_PROFILE) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("user_id", jsObject.getUserId());

            }


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

        Utility.Logger(tag, "OnSuccess UserProfile");
        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PROFILE) {

            DataObject dataObject = (DataObject) data;
            Detail detail = (Detail) dataObject.getObjectList().get(0);

            editFname.setText(detail.getFirstName());
            editLname.setText(detail.getLastName());

            editEmail.setText(detail.getEmailAddress());
            editEmail.setEnabled(false);

            editPhone.setText(detail.getPhoneNumber());
            editPhone.setEnabled(false);

            editPassword.setText(detail.getPassword());
            currentPassword = detail.getPassword();

            GlideApp.with(context).load(BottomNavigationSample.getUserPicture(detail.getPicture())).into(imageProfile);


        } else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE_PROFILE) {

            DataObject dataObject = (DataObject) data;

            management.savePreferences(new PrefObject()
                    .setSaveUserPicture(true)
                    .setPictureUrl(dataObject.getUserPicture()));

            management.savePreferences(new PrefObject()
                    .setSaveUserName(true)
                    .setUserName(editFname.getText().toString()+" "+editLname.getText().toString()));

            setResult(RESULT_OK);
            finish();
        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);
        Utility.Toaster(context, data, Toast.LENGTH_SHORT);

    }

    @Override
    public void onClick(View v) {
        if (v == layoutProfile) {
            pictureSelector.showImagePickerDialog();
        }
        if (v == layoutUpdate) {

            if (Utility.isEmptyString(editFname.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_first_name)
                        , Toast.LENGTH_SHORT);

                return;
            }

            if (Utility.isEmptyString(editLname.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_last_name)
                        , Toast.LENGTH_SHORT);

                return;
            }

            if (Utility.isEmptyString(editPassword.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_password)
                        , Toast.LENGTH_SHORT);

                return;
            }

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("update_profile")
                            .setConnection(Constant.CONNECTION.UPDATE_PROFILE)
                            .setUserId(BottomNavigationSample.getUserId())
                            .setFirstName(editFname.getText().toString())
                            .setLastName(editLname.getText().toString())
                            .setPassword(editPassword.getText().toString().equalsIgnoreCase(currentPassword) ? "null" :  editPassword.getText().toString() )))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context, R.string.updating))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.UPDATE_PROFILE));


        }

    }

    @Override
    public void onImageHandled(Bitmap image) {
        super.onImageHandled(image);

        GlideApp.with(context).load(image).into(imageProfile);

        Base64Builder base64Builder = new Base64Builder();
        base64Builder.setScaling(true);
        base64Builder.setRequiredHeight(250);
        base64Builder.setBase64Object(
                new Base64Object().setBitmap(image));
        profilePictureBase64 = base64Builder.encodedBitmapIntoString();

        isPictureUpdate = true;

    }

    @Override
    public void onImageError() {
        super.onImageError();
        isPictureUpdate = false;
    }

}
