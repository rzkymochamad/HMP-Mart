package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConfirmationCallback;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.managementutil.Management;
import com.mg.shopping.MyApplication;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.phoneauthutil.PhoneAuth;
import com.mg.shopping.pictureselectorutil.PictureSelector;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements ConnectionCallback,
        PictureSelector.ImagePickerCallback, PhoneAuth.PhoneAuthCallback {

    protected String tag = getClassName();
    protected Management management;
    protected Context context;
    protected PictureSelector pictureSelector;
    protected PhoneAuth phoneAuth;

    /* Variable for Handler*/

    Handler handler;
    Runnable runnable;
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    private static final int DEFAULT_ZERO_VALUE = 0;

    /* Variable for Permissions*/

    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean allPermissionAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(getContentView());

        context = getContextInstance();
        management = MyApplication.getManagementInstance();
        pictureSelector = new PictureSelector(context, this, this);
        phoneAuth = new PhoneAuth(this, this, this);

        //For authenticating permissions e.g camera etc
        if (requirePermission().length > 0) {
            checkPermissions();
        }

        //For initializing admob banners ad if any available in class
        if (initAdmobBannerAds() != 0 && Constant.Credentials.ADMOB_BANNER_REQUIRED) {
            checkAdmobBannerAds();
        }


    }

    /**
     * For authenticating permissions e.g camera etc
     */
    private void checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (int i = 0; i < requirePermission().length; i++) {
                if (checkSelfPermission(requirePermission()[i]) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(requirePermission(), PERMISSION_REQUEST_CODE);
                    allPermissionAccepted = false;
                    break;
                } else {
                    allPermissionAccepted = true;
                }
            }
            if (allPermissionAccepted) {

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        onPermissionApproved();

                    }
                };

                handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);


            } else {
                onPermissionReject();
            }


        } else {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    onPermissionApproved();

                }
            };

            handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
        }

    }

    /**
     * For initializing the Admob Banners Ads
     */
    private void checkAdmobBannerAds() {

        LinearLayout mAdView = findViewById(initAdmobBannerAds());
        mAdView.setVisibility(View.VISIBLE);

        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Constant.Credentials.ADMOB_BANNER_ID);

        AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);

        GDPRChecker.Request request = GDPRChecker.getRequest();
        if (request == GDPRChecker.Request.NON_PERSONALIZED) {
            // load non Personalized ads
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        } // else do nothing , it will load PERSONALIZED ads

        adView.loadAd(adRequest.build());
        mAdView.addView(adView);


    }


    /**
     * <p>It is used to enable full screen</p>
     *
     * @return
     */
    protected abstract boolean isFullScreen();


    /**
     * <p>It is used to get Class Name</p>
     *
     * @return
     */
    protected abstract String getClassName();


    /**
     * <p>It is used to get Context Instance</p>
     */
    protected abstract Context getContextInstance();


    /**
     * <p>It is used to </p>
     *
     * @return
     */
    protected abstract int getContentView();


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    protected abstract void getIntentData();


    /**
     * <p>It is used to initialize UI</p>
     */
    protected abstract void initUI();


    /**
     * <p>It is used to get Json </p>
     *
     * @param jsObject
     * @return
     */
    protected abstract String getJson(JsonObject jsObject);


    /**
     * <p>It is used to send request to server</p>
     *
     * @param requestObject
     */
    protected abstract void sendRequestToServer(RequestObject requestObject);


    /**
     * <p>It is used to require permission which is necessary</p>
     *
     * @return
     */
    protected String[] requirePermission() {
        return new String[0];
    }


    /**
     * <p>It is used to check permission approval</p>
     */
    protected void onPermissionApproved() {
    }


    /**
     * <p>It is used to check permission rejection</p>
     */
    protected void onPermissionReject() {
    }

    /**
     * <p>It is used to initialize Admob Banner Ads</p>
     *
     * @return
     */
    protected int initAdmobBannerAds() {
        return DEFAULT_ZERO_VALUE;
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }

    @Override
    public void onImageHandled(Bitmap image) {
        Utility.Logger(tag, "Picture Selector = Success");
    }

    @Override
    public void onImageError() {
        Utility.Logger(tag, "Picture Selector = Failure");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if ((requestCode == PictureSelector.REQUEST_CODE_CAMERA
                || requestCode == PictureSelector.REQUEST_CODE_GALLERY)
                && resultCode == RESULT_OK) {

            pictureSelector.handleResponse(data, requestCode, resultCode);

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                requirePermission() != null) {

            for (int i = 0; i < requirePermission().length; i++) {
                if (checkSelfPermission(requirePermission()[i]) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(requirePermission(), PERMISSION_REQUEST_CODE);
                    allPermissionAccepted = false;
                    break;
                } else {
                    allPermissionAccepted = true;
                }
            }
            if (allPermissionAccepted) {
                onPermissionApproved();
            } else {
                onPermissionReject();
            }

        }

    }

    @Override
    public void onPhoneAuthSuccess() {
        Utility.Logger(tag, "Phone Authenticated Successfully");
    }

    @Override
    public void onPhoneAuthError() {
        Utility.Logger(tag, "Phone Authenticated Failed");
    }

    protected void showConfirmationBottomSheet(final Context context, String tagline, final ConfirmationCallback callback) {

        final View view = LayoutInflater.from(context).inflate(R.layout.confirmation_bottom_sheet_item_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        TextView txtTagline = view.findViewById(R.id.txt_tagline);
        txtTagline.setText(tagline);

        TextView txtCancel = view.findViewById(R.id.txt_cancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                if (callback != null) {
                    callback.onCancel();
                }
            }
        });

        TextView txtDone = view.findViewById(R.id.txt_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                if (callback != null) {
                    callback.onDone();
                }

            }
        });


    }


}
