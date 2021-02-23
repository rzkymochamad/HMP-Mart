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
import com.mg.shopping.jsonutil.featuredcategoriesutil.ListOfDatum;
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
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    ImageView imageAdd;
    TabLayout tabLayout;
    LockableViewPager viewPagerCategory;
    TextualPagerAdapter textualPagerAdapter;
    LinearLayout layoutSearch;
    ArrayList<Object> objectArrayList = new ArrayList<>();


    /**
     * <p>It is used to get Fragment Instance</p>
     *
     * @param list
     * @return
     */
    public static Fragment getFragmentInstance(List<ListOfDatum> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constant.IntentKey.DATA_OBJECT, new ArrayList<>(list));
        Fragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.activity_home_fragment;
    }

    @Override
    protected void getIntentData() {
        objectArrayList.clear();
        objectArrayList.addAll(getArguments().getParcelableArrayList(Constant.IntentKey.DATA_OBJECT));
    }

    @Override
    protected int initAdmobBannerAds() {
        return R.id.layout_ad;
    }

    @Override
    protected void initUI(View view) {

        Utility.Logger(tag, String.valueOf(objectArrayList.size()));
        layoutSearch = view.findViewById(R.id.layout_search);

        //Initialize TabLayout & Setup with Viewpager

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPagerCategory = view.findViewById(R.id.view_pager_category);
        viewPagerCategory.setSwipeLocked(true);


        pagerList.clear();
        pagerList.add(new PagerTabObject()
                .setTitle(StringUtils.capitalize("Hot"))
                .setFragment(new HotFragment()));

        for (int i = 0; i < objectArrayList.size(); i++) {

            ListOfDatum datum = (ListOfDatum) objectArrayList.get(i);

            pagerList.add(new PagerTabObject()
                    .setTitle(StringUtils.capitalize(datum.getName()))
                    .setFragment(FeaturedCategoriesProductFragment.getFragmentInstance(
                            new DataObject()
                    .setCategoryId(datum.getChildSubCategories()))));

        }

        textualPagerAdapter = new TextualPagerAdapter(getChildFragmentManager(), pagerList);
        viewPagerCategory.setAdapter(textualPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerCategory);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            View customTabView = LayoutInflater.from(context).inflate(R.layout.featured_categories_item_layout, null);
            customTabView.setPadding(0, 0, 0, 0);

            TextView tv = (TextView) customTabView.findViewById(R.id.txt_tab);
            tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
            tv.setText(Utility.capitalize(pagerList.get(i).getTitle()));

            LinearLayout layoutTab = customTabView.findViewById(R.id.layout_indicator);


            if (i <= 0) {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
                tv.setTypeface(Font.ubuntuMediumFont(context));
                layoutTab.setVisibility(View.VISIBLE);

            } else {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
                tv.setTypeface(Font.ubuntuLightFont(context));
                layoutTab.setVisibility(View.INVISIBLE);


            }

            tabLayout.getTabAt(i).setCustomView(customTabView);

        }

        viewPagerCategory.addOnPageChangeListener(this);
        layoutSearch.setOnClickListener(this);

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

        Utility.Logger(tag,requestObject.toString());


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

    }

    @Override
    public void onClick(View v) {
        if (v == imageAdd) {
            showPopup(imageAdd);
        }
        if (v == layoutSearch) {
            startActivity(new Intent(context, SearchProducts.class));
        }

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

            if (i == position) {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
                txtTab.setTypeface(Font.ubuntuMediumFont(context));
                layoutTab.setVisibility(View.VISIBLE);

            } else {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
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


}
