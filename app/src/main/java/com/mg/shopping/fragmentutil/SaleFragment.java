package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.activityutil.SearchProducts;
import com.mg.shopping.adapterutil.TextualPagerAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.LockableViewPager;
import com.mg.shopping.fontutil.Font;

import com.mg.shopping.jsonutil.saleproductutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PagerTabObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

public class SaleFragment extends BaseFragment implements  ViewPager.OnPageChangeListener , View.OnClickListener {
    private ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    TextView txtMenu;
    ImageView imageBack;
    ImageView imageCart;
    ImageView imageSearch;
    TabLayout tabLayout;
    LockableViewPager viewPagerCategory;
    TextualPagerAdapter textualPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Retrieve Intent Data
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
        return R.layout.activity_sale_fragment;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent data
    }

    @Override
    protected int initAdmobBannerAds() {
        return R.id.layout_ad;
    }

    @Override
    protected void initUI(View view) {

        txtMenu = view.findViewById(R.id.txt_menu);

        imageBack = view.findViewById(R.id.image_back);
        imageSearch = view.findViewById(R.id.image_search);
        imageCart = view.findViewById(R.id.image_cart);

        //Initialize TabLayout & Setup with Viewpager

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPagerCategory = view.findViewById(R.id.view_pager_category);
        viewPagerCategory.setSwipeLocked(true);


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_sale_data")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SALE_DATA)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SALE_DATA));

        viewPagerCategory.addOnPageChangeListener(this);
        imageSearch.setOnClickListener(this);


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

        final DataObject dtObject = (DataObject) data;
        pagerList.clear();

        for (int i = 0; i < dtObject.getObjectList().size(); i++) {

            ListOfDatum datum = (ListOfDatum) dtObject.getObjectList().get(i);

            pagerList.add(new PagerTabObject()
                    .setTitle(StringUtils.capitalize(datum.getName()))
                    .setFragment(SaleProductFragment.getFragmentInstance(new ArrayList<>(datum.getListOfData()))));

        }

        textualPagerAdapter = new TextualPagerAdapter(getChildFragmentManager(), pagerList);
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


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // do nothing
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
        // do nothing
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


    @Override
    public void onClick(View v) {
        if (v==imageSearch){
            startActivity(new Intent(context, SearchProducts.class));
        }
    }
}
