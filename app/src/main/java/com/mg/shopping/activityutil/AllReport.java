package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.adapterutil.TextualPagerAdapter;
import com.mg.shopping.customutil.LockableViewPager;
import com.mg.shopping.fontutil.Font;
import com.mg.shopping.fragmentutil.ReportFragment;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PagerTabObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import androidx.viewpager.widget.ViewPager;

public class AllReport extends BaseActivity implements View.OnClickListener , ViewPager.OnPageChangeListener{
    ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    TextView txtMenu;
    ImageView imageBack;
    ImageView imageOther;

    TabLayout tabLayout;
    LockableViewPager viewPagerReport;
    TextualPagerAdapter textualPagerAdapter;

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
        return R.layout.activity_all_report;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.list_of_reports));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);

        //Add data into Arraylist

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.refund_request))
                .setFragment(ReportFragment.getFragmentInstance("0")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.dispute_request))
                .setFragment(ReportFragment.getFragmentInstance("1")));


        //Initialize TabLayout & Setup with Viewpager

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewPagerReport = findViewById(R.id.view_pager_report);
        viewPagerReport.setSwipeLocked(true);

        textualPagerAdapter = new TextualPagerAdapter(getSupportFragmentManager(), pagerList);
        viewPagerReport.setAdapter(textualPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerReport);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            View customTabView = LayoutInflater.from(context).inflate(R.layout.sale_categories_item_layout, null);
            customTabView.setPadding(0, 0, 0, 0);

            TextView tv = (TextView) customTabView.findViewById(R.id.txt_tab);
            tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextTertiary));
            tv.setText(Utility.capitalize(pagerList.get(i).getTitle()));

            LinearLayout layoutTab = customTabView.findViewById(R.id.layout_indicator);
            layoutTab.setBackgroundColor(Utility.getColourFromRes(context,R.color.colorDialogTertiaryDay));


            if (i <= 0) {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextTertiary));
                tv.setTypeface(Font.ubuntuRegularFont(context));
                layoutTab.setVisibility(View.VISIBLE);

            } else {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextTertiary));
                tv.setTypeface(Font.ubuntuLightFont(context));
                layoutTab.setVisibility(View.INVISIBLE);


            }

            tabLayout.getTabAt(i).setCustomView(customTabView);

        }

        viewPagerReport.addOnPageChangeListener(this);
        imageBack.setOnClickListener(this);

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
        Utility.Logger(tag, requestObject.toString());
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

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //do nothing
    }

    @Override
    public void onPageSelected(int position) {

        Utility.Logger(tag, "Page Selected , Position = " + position);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            Utility.Logger(tag, "Position Tab = " + i);

            View customItemView = tabLayout.getTabAt(i).getCustomView();
            TextView txtTab = customItemView.findViewById(R.id.txt_tab);

            LinearLayout layoutTab = customItemView.findViewById(R.id.layout_indicator);
            layoutTab.setBackgroundColor(Utility.getColourFromRes(context,R.color.colorDialogTertiaryDay));

            if (i == position) {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextTertiary));
                txtTab.setTypeface(Font.ubuntuRegularFont(context));
                layoutTab.setVisibility(View.VISIBLE);

            } else  {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextTertiary));
                txtTab.setTypeface(Font.ubuntuLightFont(context));
                layoutTab.setVisibility(View.INVISIBLE);

            }

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }




}
