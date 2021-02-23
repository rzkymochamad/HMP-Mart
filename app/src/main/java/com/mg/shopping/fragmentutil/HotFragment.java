package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.activityutil.AllBrands;
import com.mg.shopping.activityutil.AllCoupons;
import com.mg.shopping.activityutil.AllFavourites;
import com.mg.shopping.activityutil.AllReport;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.BrandDetail;
import com.mg.shopping.activityutil.OrderHistory;
import com.mg.shopping.activityutil.ProductDetail;
import com.mg.shopping.adapterutil.HotAdapter;
import com.mg.shopping.adapterutil.SimplePagerAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.Brand;
import com.mg.shopping.jsonutil.hotdatautil.ListOfBrand;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class HotFragment extends BaseFragment {
    ArrayList<Object> objectArrayList = new ArrayList<>();
    boolean loading;
    LinearLayout layoutScroller;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData();
        initUI(view); //Initialize UI

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
        return R.layout.activity_hot;
    }

    @Override
    protected void getIntentData() {
        // do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI(View view) {

        layoutScroller = view.findViewById(R.id.layout_scroller);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_hot_data")
                        .setConnection(Constant.CONNECTION.RETRIEVE_HOT_DATA)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_HOT_DATA));


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_HOT_DATA) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("product_id", jsObject.getId());


            }


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
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        switch (requestObject.getConnection()) {
            case RETRIEVE_HOT_DATA:
                populateHotData(dtObject);   // Populate hot screen data
                break;
            case RETRIEVE_SPECIFIC_PRODUCT:
                BottomNavigationSample.showProductCartBottomSheet(context, dtObject);
                break;
            default:
                break;
        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    /**
     * <p>It is used to populate hot screen data</p>
     *
     * @param dtObject
     */
    private void populateHotData(final DataObject dtObject) {

        loading = false;
        layoutScroller.removeAllViews();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        View featuredBannerView = LayoutInflater.from(context).inflate(R.layout.featured_banner_item_layout, null, false);

        for (int i = 0; i < dtObject.getObjectList02().size(); i++) {
            fragmentArrayList.add(BannerFragment.getFragmentInstance(dtObject.getObjectList02().get(i)));
        }

        //Initialize Pager & its indicator as well as connect it with its adapter

        final TabLayout tabLayout = featuredBannerView.findViewById(R.id.tab_layout);
        ViewPager viewPagerFeatured = featuredBannerView.findViewById(R.id.view_pager_featured);
        SimplePagerAdapter featuredPagerAdapter = new SimplePagerAdapter(getFragmentManager(), fragmentArrayList);
        viewPagerFeatured.setAdapter(featuredPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerFeatured);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            View customTabView = LayoutInflater.from(context).inflate(R.layout.featured_indicator_layout, null);
            LinearLayout layoutTab = customTabView.findViewById(R.id.layout_indicator);

            if (i <= 0) {
                layoutTab.setBackgroundResource(R.drawable.bg_indicator_selected);
            } else {
                layoutTab.setBackgroundResource(R.drawable.bg_indicator_un_selected);
            }

            tabLayout.getTabAt(i).setCustomView(customTabView);

        }

        viewPagerFeatured.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    LinearLayout layoutTab = customItemView.findViewById(R.id.layout_indicator);

                    if (i == position) {
                        layoutTab.setBackgroundResource(R.drawable.bg_indicator_selected);
                    } else {
                        layoutTab.setBackgroundResource(R.drawable.bg_indicator_un_selected);
                        Utility.Logger(tag, "UnSelected Positon = " + i);
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing
            }
        });
        layoutScroller.addView(featuredBannerView);


        View optionView = LayoutInflater.from(context).inflate(R.layout.option_view_item_layout, null, false);

        LinearLayout layoutBrands = optionView.findViewById(R.id.layout_brands);
        layoutBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.Logger(tag, "Top Brands");
                startActivity(new Intent(context, AllBrands.class));

            }
        });

        LinearLayout layoutDiscount = optionView.findViewById(R.id.layout_discount);
        layoutDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.Logger(tag, "Discount");
                manageHotScreenOptionClickListener("all_coupon"); //open All coupon screen


            }
        });

        LinearLayout layoutFavourites = optionView.findViewById(R.id.layout_favourites);
        layoutFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.Logger(tag, "Discount");
                manageHotScreenOptionClickListener("all_favourite"); //open All favourite screen

            }
        });

        LinearLayout layoutHistory = optionView.findViewById(R.id.layout_history);
        layoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.Logger(tag, "History");
                manageHotScreenOptionClickListener("order_history"); //open All Order History screen


            }
        });

        LinearLayout layoutReport = optionView.findViewById(R.id.layout_report);
        layoutReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.Logger(tag, "Report");
                manageHotScreenOptionClickListener("report"); //open All Report screen


            }
        });

        layoutScroller.addView(optionView);


        View featuredBrandView = LayoutInflater.from(context).inflate(R.layout.featured_brand_view_item_layout, null, false);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewFeaturedBrand = featuredBrandView.findViewById(R.id.recycler_view_featured_brand);
        recyclerViewFeaturedBrand.setLayoutManager(layoutManager);
        recyclerViewFeaturedBrand.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(5)));

        objectArrayList.clear();
        objectArrayList.addAll(dtObject.getObjectList03());

        HotAdapter dataAdapter = new HotAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfBrand brand = (ListOfBrand) objectArrayList.get(pos);

                Intent intent = new Intent(context, BrandDetail.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT, new Brand()
                        .setId(brand.getId())
                        .setName(brand.getName())
                        .setImage(brand.getImage())
                        .setCoverPhoto(brand.getCoverPhoto())
                        .setItems(brand.getItems())
                        .setRating(brand.getRating())
                        .setReviews(brand.getReviews()));

                startActivity(intent);

            }
        });
        recyclerViewFeaturedBrand.setAdapter(dataAdapter);
        layoutScroller.addView(featuredBrandView);


        View trendingProductsView = LayoutInflater.from(context).inflate(R.layout.trending_product_view_item_layout, null, false);

        StaggeredGridLayoutManager layoutManagerProduct = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerViewProduct = trendingProductsView.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManagerProduct);
        recyclerViewProduct.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(5)));

        HotAdapter dataAdapterProduct = new HotAdapter(context, new ArrayList<Object>(dtObject.getObjectList()), new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum productDatum = (ListOfDatum) dtObject.getObjectList().get(pos);

                if (selectionObject.getAction().equalsIgnoreCase("product_cart")) {

                    sendRequestToServer(new RequestObject()
                            .setJson(getJson(new JsonObject()
                                    .setFunctionality("retrieve_specific_product")
                                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT)
                                    .setId(productDatum.getId())))
                            .setLoadingText(Utility.getStringFromRes(context, R.string.loading))
                            .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT));

                } else {

                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, productDatum);
                    startActivity(intent);


                }


            }
        });
        recyclerViewProduct.setAdapter(dataAdapterProduct);
        layoutScroller.addView(trendingProductsView);

    }

    /**
     * <p>It is used to manage Hot Screen Option Click Listener</p>
     * @param type
     */
    private void manageHotScreenOptionClickListener(String type) {

        switch (type) {
            case "report":

                if (management.getPreferences(null).isLogin())
                    startActivity(new Intent(context, AllReport.class));

                break;
            case "order_history":

                if (management.getPreferences(null).isLogin())
                    startActivity(new Intent(context, OrderHistory.class));

                break;
            case "all_favourite":

                if (management.getPreferences(null).isLogin())
                    startActivity(new Intent(context, AllFavourites.class));

                break;
            case "all_coupon":

                if (management.getPreferences(null).isLogin())
                    startActivity(new Intent(context, AllCoupons.class));

                break;

            default:
                Utility.Logger(tag,"Default Case");
                break;
        }


    }

}




