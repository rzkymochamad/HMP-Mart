package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;


public class ProgressFragment extends BaseFragment {


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
        return R.layout.activity_progress;
    }

    @Override
    protected void getIntentData() {
        //do nothing
    }

    @Override
    protected void initUI(View view) {
        Utility.Logger(tag,"Progress Fragment Working");
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