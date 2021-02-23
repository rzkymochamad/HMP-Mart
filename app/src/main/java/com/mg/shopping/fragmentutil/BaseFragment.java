package com.mg.shopping.fragmentutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.mg.shopping.pictureselectorutil.PictureSelector;
import com.mg.shopping.R;

import com.ixidev.gdpr.GDPRChecker;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public abstract class BaseFragment extends Fragment implements ConnectionCallback , PictureSelector.ImagePickerCallback {
    protected String tag = getClassName();
    protected Context context;
    protected RelativeLayout layoutMenu;
    protected Management management;
    protected PictureSelector pictureSelector;
    FragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContextInstance();
        management = MyApplication.getManagementInstance();
        pictureSelector = new PictureSelector(context,getActivity(),this);
        layoutMenu = view.findViewById(R.id.layout_menu);
        activity = getActivity();

        if (initAdmobBannerAds() != 0 && Constant.Credentials.ADMOB_BANNER_REQUIRED){

                LinearLayout mAdView = view.findViewById(initAdmobBannerAds());
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


    }


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
    protected abstract void initUI(View view);


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
     * <p>It is used to initialize Admob Banner Ads</p>
     * @return
     */
    protected int initAdmobBannerAds (){
        return 0;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }

    @Override
    public void onImageHandled(Bitmap image) {

    }

    @Override
    public void onImageError() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if ((requestCode == PictureSelector.REQUEST_CODE_CAMERA
                || requestCode == PictureSelector.REQUEST_CODE_GALLERY)
                && resultCode == Activity.RESULT_OK) {

            pictureSelector.handleResponse(data,requestCode,resultCode);

        }

        super.onActivityResult(requestCode, resultCode, data);
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