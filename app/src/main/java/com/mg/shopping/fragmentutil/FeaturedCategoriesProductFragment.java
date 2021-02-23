package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.ProductDetail;
import com.mg.shopping.activityutil.SpecificCategorizedProducts;
import com.mg.shopping.adapterutil.FeaturedCategoriesAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.featuredcategoriesproductutil.ListOfCategory;
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


public class FeaturedCategoriesProductFragment extends BaseFragment {
    StaggeredGridLayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerViewCategories;
    RecyclerView recyclerViewProduct;
    FeaturedCategoriesAdapter categoriesDataAdapter;
    FeaturedCategoriesAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    ArrayList<Object> categoriesArrayList = new ArrayList<>();
    DataObject dataObject;
    TextView txtSearch;
    TextView txtType;
    LayoutMarginDecoration itemDecoration;
    String categoryType;

    int visibleThreshold = 1;
    boolean loading;
    String skipIds;
    StringBuilder stringBuilder = new StringBuilder();


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(DataObject dataObject) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.IntentKey.DATA_OBJECT, dataObject);
        Fragment fragment = new FeaturedCategoriesProductFragment();
        fragment.setArguments(args);
        return fragment;
    }


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
        return R.layout.activity_featured_categorized_product;
    }

    @Override
    protected void getIntentData() {
        dataObject = getArguments().getParcelable(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI(View view) {

        gridLayoutManager = new GridLayoutManager(context,4,LinearLayoutManager.VERTICAL,false);
        recyclerViewCategories = view.findViewById(R.id.recycler_view_categories);
        recyclerViewCategories.setLayoutManager(gridLayoutManager);

        categoriesDataAdapter = new FeaturedCategoriesAdapter(context, categoriesArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                ListOfCategory listOfCategory = (ListOfCategory) categoriesArrayList.get(pos);
                Utility.Logger(tag,listOfCategory.toString());

                DataObject dataObjectCategory = new DataObject();
                dataObjectCategory.setCategoryId(listOfCategory.getId());
                dataObjectCategory.setCategoryName(listOfCategory.getName());
                dataObjectCategory.setCategoryType("child_sub_category_id");

                Intent intent = new Intent(context, SpecificCategorizedProducts.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT,dataObjectCategory);
                startActivity(intent);


            }
        });
        recyclerViewCategories.setAdapter(categoriesDataAdapter);
        recyclerViewCategories.addItemDecoration(new LayoutMarginDecoration(4, Utility.dpToPx(5)));


        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new FeaturedCategoriesAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                com.mg.shopping.jsonutil.featuredcategoriesproductutil.ListOfDatum productDatum = (com.mg.shopping.jsonutil.featuredcategoriesproductutil.ListOfDatum) objectArrayList.get(pos);

                if (selectionObject.getAction().equalsIgnoreCase("product_cart")){

                    sendRequestToServer(new RequestObject()
                            .setJson(getJson(new JsonObject()
                                    .setFunctionality("retrieve_specific_product")
                                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT)
                                    .setId(productDatum.getId())))
                            .setLoadingText(Utility.getStringFromRes(context,R.string.loading))
                            .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT));

                }
                else {

                    Intent intent = new Intent(context,ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT,productDatum);
                    startActivity(intent);


                }
            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(2, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);
        recyclerViewProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
                    int lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions);
                    int totalItemCount = layoutManager.getItemCount();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loading = true;

                        Utility.Logger(tag, "Last Item Wow !");

                        skipIds = stringBuilder.toString();

                    }

                }
            }
        });


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_home_categorized_products")
                        .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES_PRODUCT)
                        .setId(dataObject.getCategoryId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES_PRODUCT));



    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES_PRODUCT) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("categories_id", jsObject.getId());

            }
            else  if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

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

        DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        switch (requestObject.getConnection()){
            case RETRIEVE_FEATURED_CATEGORIES_PRODUCT:

                loading = false;

                categoriesArrayList.addAll(dtObject.getObjectList02());   // populate featured categorized data
                categoriesDataAdapter.notifyDataSetChanged();

                for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                    objectArrayList.add(dtObject.getObjectList().get(i));

                    stringBuilder.append("'");
                    stringBuilder.append(((com.mg.shopping.jsonutil.featuredcategoriesproductutil.ListOfDatum) dtObject.getObjectList().get(i)).getId());
                    stringBuilder.append("'");

                    if (i < (dtObject.getObjectList().size() - 1))
                        stringBuilder.append(",");


                }   // populate Categorized Product Data
                dataAdapter.notifyDataSetChanged();

                break;
            case RETRIEVE_SPECIFIC_PRODUCT:

                BottomNavigationSample.showProductCartBottomSheet(context,dtObject);

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

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            maxSize = lastVisibleItemPositions[i];
        }
        return maxSize;
    }



}




