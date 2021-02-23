package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.adapterutil.TextualPagerAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.LockableViewPager;
import com.mg.shopping.fontutil.Font;
import com.mg.shopping.fragmentutil.OrderFragment;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PagerTabObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import androidx.viewpager.widget.ViewPager;

public class OrderHistory extends BaseActivity implements View.OnClickListener , ViewPager.OnPageChangeListener  {
    TextView txtMenu;
    ImageView imageBack;
    ImageView imageOther;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    int visibleThreshold = 1;
    boolean loading;
    String skipIds;
    StringBuilder stringBuilder = new StringBuilder();
    PrefObject prefObject;
    LinearLayout layoutScroller;
    TabLayout tabLayout;
    LockableViewPager viewPagerCategory;
    TextualPagerAdapter textualPagerAdapter;
    ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    String type;

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
        return R.layout.activity_order_history;
    }

    @Override
    protected void getIntentData() {
        type = getIntent().getStringExtra(Constant.IntentKey.TYPE);
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.order_history));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);


        //Add data into Arraylist

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.all_orders))
                .setFragment(OrderFragment.getFragmentInstance("-1")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.pending_orders))
                .setFragment(OrderFragment.getFragmentInstance("0")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.processed_orders))
                .setFragment(OrderFragment.getFragmentInstance("1")));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context,R.string.completed_orders))
                .setFragment(OrderFragment.getFragmentInstance("2")));


        //Initialize TabLayout & Setup with Viewpager

        tabLayout = findViewById(R.id.tab_layout);
        viewPagerCategory = findViewById(R.id.view_pager_category);
        viewPagerCategory.setSwipeLocked(true);


        if (!Utility.isEmptyString(type) && type.equalsIgnoreCase("0")){
            Utility.Logger(tag,"Workign");

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    viewPagerCategory.setCurrentItem(1,true);
                }
            });


        }
        else if (!Utility.isEmptyString(type) && type.equalsIgnoreCase("1")){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    viewPagerCategory.setCurrentItem(2,true);
                }
            });
        }
        else if (!Utility.isEmptyString(type) && type.equalsIgnoreCase("2")){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    viewPagerCategory.setCurrentItem(3,true);
                }
            });
        }

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

        imageBack.setOnClickListener(this);
        viewPagerCategory.addOnPageChangeListener(this);

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
            jsonObject.accumulate("user_id", jsObject.getUserId());


            if (!Utility.isEmptyString(jsObject.getSkipIds()))
                jsonObject.accumulate("skip_ids", jsObject.getSkipIds());


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

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
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

    /* <p> you should refer to a view to stick your popup wherever u want.

     **/
    public void showPopup(View v) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.order_report_item_layout, null);

        LinearLayout layoutRefund = popupView.findViewById(R.id.layout_refund);
        layoutRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.Logger(tag,"Refund Now");
            }
        });

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v, -230, -20);
    }


}
