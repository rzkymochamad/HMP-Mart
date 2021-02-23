package com.mg.shopping.fragmentutil;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.shopping.activityutil.AllFavourites;
import com.mg.shopping.activityutil.AllReport;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.ContactUs;
import com.mg.shopping.activityutil.Login;
import com.mg.shopping.activityutil.OrderHistory;
import com.mg.shopping.activityutil.ProductRating;
import com.mg.shopping.activityutil.Setting;
import com.mg.shopping.activityutil.UserAddressBook;
import com.mg.shopping.activityutil.UserFollowing;
import com.mg.shopping.activityutil.UserProfile;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

public class AccountProfile extends BaseFragment implements View.OnClickListener {
    TextView txtMenu;
    private LinearLayout layoutAddressBook;
    private LinearLayout layoutPending;
    private LinearLayout layoutProcessed;
    private LinearLayout layoutShipped;
    private LinearLayout layoutHistory;
    private LinearLayout layoutReport;
    private LinearLayout layoutMyReview;
    private LinearLayout layoutContactUs;
    private LinearLayout layoutWishlist;
    private LinearLayout layoutFollowing;
    private LinearLayout layoutEdit;
    private ImageView imageProfile;
    private TextView txtUsername;
    private LinearLayout layoutSetting;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);  //Initialize View

    }

    @Override
    protected String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Context getContextInstance() {
        return getActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_account_profile;
    }

    @Override
    protected void getIntentData() {
        //Currently do nothing , for receiving intent data if needed
    }

    @Override
    protected void initUI(View view) {

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.account_setting));

        layoutPending = (LinearLayout) view.findViewById(R.id.layout_pending);
        layoutProcessed = (LinearLayout) view.findViewById(R.id.layout_processed);
        layoutShipped = (LinearLayout) view.findViewById(R.id.layout_shipped);

        layoutWishlist = view.findViewById(R.id.layout_wishlist);
        layoutHistory = view.findViewById(R.id.layout_history);
        layoutReport = view.findViewById(R.id.layout_report);
        layoutFollowing = view.findViewById(R.id.layout_following);

        layoutAddressBook = view.findViewById(R.id.layout_address_book);
        layoutMyReview = view.findViewById(R.id.layout_my_review);
        layoutContactUs = view.findViewById(R.id.layout_contact_us);
        layoutSetting = view.findViewById(R.id.layout_setting);

        layoutEdit = view.findViewById(R.id.layout_edit);

        imageProfile = view.findViewById(R.id.image_profile);
        txtUsername = view.findViewById(R.id.txt_username);

        if (!management.getPreferences(null).isLogin()) {

            Intent intent = new Intent(context, Login.class);
            startActivityForResult(intent, Constant.RequestCode.LOGIN_CODE);

        }

        ////((BottomNavigationSample)context).

        layoutAddressBook.setOnClickListener(this);
        layoutWishlist.setOnClickListener(this);
        layoutPending.setOnClickListener(this);
        layoutProcessed.setOnClickListener(this);
        layoutShipped.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
        layoutMyReview.setOnClickListener(this);
        layoutContactUs.setOnClickListener(this);
        layoutFollowing.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutEdit.setOnClickListener(this);
        layoutReport.setOnClickListener(this);

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

            if (jsObject.getConnection() == Constant.CONNECTION.ADD_REVIEWS) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("order_id", jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("brand_id", jsObject.getBrandId());
                jsonObject.accumulate("product_id", jsObject.getProductId());
                jsonObject.accumulate("rating", jsObject.getRate());
                jsonObject.accumulate("review", jsObject.getReview());


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
    public void onClick(View v) {
        if (v == layoutEdit) {

            Intent intent = new Intent(context, UserProfile.class);
            startActivityForResult(intent, Constant.RequestCode.PROFILE_UPDATE);

        }
        if (v == layoutAddressBook) {
            Utility.Logger(tag, "Address Book Clicking");
            Intent intent = new Intent(context, UserAddressBook.class);
            startActivity(intent);
        }
        if (v == layoutPending) {

            Intent intent = new Intent(context, OrderHistory.class);
            intent.putExtra(Constant.IntentKey.TYPE, "0");
            startActivity(intent);

        }
        if (v == layoutProcessed) {

            Intent intent = new Intent(context, OrderHistory.class);
            intent.putExtra(Constant.IntentKey.TYPE, "1");
            startActivity(intent);

        }
        if (v == layoutShipped) {

            Intent intent = new Intent(context, OrderHistory.class);
            intent.putExtra(Constant.IntentKey.TYPE, "2");
            startActivity(intent);

        }
        if (v == layoutHistory) {

            Intent intent = new Intent(context, OrderHistory.class);
            startActivity(intent);

        }
        if (v == layoutMyReview) {

            Utility.Logger(tag, "My Review Clicking");

            Intent intent = new Intent(context, ProductRating.class);
            intent.putExtra(Constant.IntentKey.DATA_OBJECT, new DataObject()
                    .setUserId(management.getPreferences(null).getUserId())
                    .setType("user_rating"));
            startActivity(intent);

        }
        if (v == layoutContactUs) {
            Intent intent = new Intent(context, ContactUs.class);
            startActivity(intent);
        }
        if (v == layoutWishlist) {
            Intent intent = new Intent(context, AllFavourites.class);
            startActivity(intent);
        }
        if (v == layoutFollowing) {
            Intent intent = new Intent(context, UserFollowing.class);
            startActivity(intent);
        }
        if (v == layoutSetting) {
            startActivityForResult(new Intent(context, Setting.class), Constant.RequestCode.LOGIN_CODE);
        }
        if (v == layoutReport) {
            Intent intent = new Intent(context, AllReport.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (management.getPreferences(null).isLogin()) {

            String pictureUrl = management.getPreferences(null).getPictureUrl();
            String userName = management.getPreferences(null).getUserName();

            GlideApp.with(context).load(BottomNavigationSample.getUserPicture(pictureUrl)).into(imageProfile);
            txtUsername.setText(userName);

            Utility.Logger(tag, "Name = " + userName + " PictureUrl = " + pictureUrl);

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Utility.Logger(tag, "Working");

        if (requestCode == Constant.RequestCode.LOGIN_CODE
                && resultCode == Activity.RESULT_OK) {

            Utility.Logger(tag, "RESULT_OK");

            ((BottomNavigationSample) context).getViewPager().setCurrentItem(0, false);

        } else if (requestCode == Constant.RequestCode.PROFILE_UPDATE
                && resultCode == Activity.RESULT_OK) {

            Utility.Logger(tag, "RESULT_OK Picture Updated");
            onResume();


        }



    }
}
