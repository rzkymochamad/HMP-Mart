package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mg.shopping.adapterutil.FavouritesAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.fragmentutil.BaseFragment;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class ProductFavouriteFragment extends BaseFragment  {
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    FavouritesAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    LayoutMarginDecoration itemDecoration;

    int visibleThreshold = 1;
    private boolean loading;
    private String skipIds;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
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
        return R.layout.activity_sale_product;
    }

    @Override
    protected void getIntentData() {
        //do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI(View view) {

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new FavouritesAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum productDatum = (ListOfDatum) objectArrayList.get(pos);

                if (selectionObject.getAction().equalsIgnoreCase("product_cart")){

                    sendRequestToServer(new RequestObject()
                            .setJson(getJson(new JsonObject()
                                    .setFunctionality("retrieve_specific_product")
                                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT)
                                    .setId(productDatum.getProductId())))
                            .setLoadingText(Utility.getStringFromRes(context,R.string.loading))
                            .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT));

                }
                else {

                    com.mg.shopping.jsonutil.listofproductutil.ListOfDatum
                            product = new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum();

                    product.setId(productDatum.getProductId());
                    product.setName(productDatum.getName());
                    product.setRating(productDatum.getRating());
                    product.setPrice(productDatum.getPrice());

                    Intent intent = new Intent(context,ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT,product);
                    startActivity(intent);

                }

            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);
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
                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("retrieve_favourites_data")
                                        .setConnection(Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT)
                                        .setType("favourite_product")
                                        .setUserId(BottomNavigationSample.getUserId())
                                        .setSkipIds(skipIds)))
                                .setFirstRequest(true)
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT));

                    }

                }
            }
        });

        itemDecoration = new LayoutMarginDecoration(2, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_favourites_data")
                        .setConnection(Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT)
                        .setType("favourite_product")
                        .setUserId(BottomNavigationSample.getUserId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT));


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("type", jsObject.getType());
                jsonObject.accumulate("user_id", jsObject.getUserId());

                if (!Utility.isEmptyString(jsObject.getSkipIds()))
                    jsonObject.accumulate("skip_ids", jsObject.getSkipIds());

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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            BottomNavigationSample.showProductCartBottomSheet(context,dtObject);

        }
        else {

            if (requestObject.isFirstRequest()) {
                objectArrayList.clear();
            }

            loading = false;
            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                objectArrayList.add(dtObject.getObjectList().get(i));

                stringBuilder.append("'");
                stringBuilder.append(((ListOfDatum) dtObject.getObjectList().get(i)).getId());
                stringBuilder.append("'");

                if (i < (dtObject.getObjectList().size() - 1))
                    stringBuilder.append(",");


            }
            dataAdapter.notifyDataSetChanged();

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




