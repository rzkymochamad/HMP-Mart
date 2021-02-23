package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.ProductDetail;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import androidx.fragment.app.Fragment;


public class ProductPictureFragment extends BaseFragment {

    ImageView imageBoard;
    String pictureUrl;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param picture
     * @return
     */
    public static Fragment getFragmentInstance(String picture) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.DATA_OBJECT, picture);
        Fragment fragment = new ProductPictureFragment();
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
        return R.layout.activity_product_picture;
    }

    @Override
    protected void getIntentData() {
        pictureUrl = getArguments().getString(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI(View view) {

        imageBoard = view.findViewById(R.id.image_board);
        GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(imageBoard);


        imageBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ProductDetail)getActivity()).onSelectPicture();

            }
        });

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        return null;
    }

    @Override
    protected void sendRequestToServer(RequestObject requestObject) {
        Utility.Logger(tag,requestObject.toString());
    }




}