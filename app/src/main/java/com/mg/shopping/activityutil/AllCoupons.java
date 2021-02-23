package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.adapterutil.TextualPagerAdapter;
import com.mg.shopping.customutil.LockableViewPager;
import com.mg.shopping.fontutil.Font;
import com.mg.shopping.fragmentutil.BrandCouponFragment;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PagerTabObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

public class AllCoupons extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    TextView txtMenu;
    private ImageView imageBack;
    ImageView imageSearch;
    private TabLayout tabLayout;
    LockableViewPager viewPagerCategory;
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
        return R.layout.activity_sale_fragment;
    }

    @Override
    protected void getIntentData() {
        //Do nothing , needed if receive data from Intent
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.deals_coupon));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageSearch = findViewById(R.id.image_search);
        imageSearch.setVisibility(View.GONE);

        //Add data into Arraylist

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.all_coupons))
                .setFragment(BrandCouponFragment.getFragmentInstance("4")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.category_coupons))
                .setFragment(BrandCouponFragment.getFragmentInstance("2")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.brand_coupons))
                .setFragment(BrandCouponFragment.getFragmentInstance("1")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.product_coupons))
                .setFragment(BrandCouponFragment.getFragmentInstance("3")));

        //Initialize TabLayout & Setup with Viewpager

        tabLayout = findViewById(R.id.tab_layout);
        viewPagerCategory = findViewById(R.id.view_pager_category);
        viewPagerCategory.setSwipeLocked(true);

        textualPagerAdapter = new TextualPagerAdapter(getSupportFragmentManager(), pagerList);
        viewPagerCategory.setAdapter(textualPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerCategory);

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

        viewPagerCategory.addOnPageChangeListener(this);
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
        //do nothing , we didn't use this method
    }


    /* <p> you should refer to a view to stick your popup wherever u want.
     **/
    public void showPopup(View v) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_add_category_layout, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                400,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOutsideTouchable(true);


        popupWindow.showAsDropDown(v, -330, -10);
    }



}
