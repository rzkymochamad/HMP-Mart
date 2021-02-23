package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.ListOfFeaturedProducts;
import com.mg.shopping.activityutil.ProductDetail;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.jsonutil.hotdatautil.ListOfFeatured;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import androidx.fragment.app.Fragment;


public class BannerFragment extends BaseFragment implements View.OnClickListener {
    ImageView imageBoard;
    ListOfFeatured dataObject;
    RoundKornerLinearLayout layoutBanner;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param dataObject
     * @return
     */
    public static Fragment getFragmentInstance(Object dataObject) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.IntentKey.DATA_OBJECT, (Parcelable) dataObject);
        Fragment fragment = new BannerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getIntentData();
        initUI(view);

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
        return R.layout.activity_banner;
    }

    @Override
    protected void getIntentData() {
        dataObject = getArguments().getParcelable(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI(View view) {

        imageBoard = view.findViewById(R.id.image_banner);
        layoutBanner = view.findViewById(R.id.layout_banner);

        GlideApp.with(context).load(BottomNavigationSample.getFeaturedBanner(dataObject.getImage())).into(imageBoard);

        layoutBanner.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        return null;
    }

    @Override
    protected void sendRequestToServer(RequestObject requestObject) {
        //do nothing , used in case of sending request
    }


    @Override
    public void onClick(View v) {
        if (v==layoutBanner){

            Utility.Logger(tag,"Clicking on Banner");
            if (dataObject.getType().equalsIgnoreCase("list")){

                Intent intent = new Intent(context, ListOfFeaturedProducts.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT,dataObject);
                startActivity(intent);

            }
            else {

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                        .setId(dataObject.getDetail()));
                startActivity(intent);

            }


        }
    }
}