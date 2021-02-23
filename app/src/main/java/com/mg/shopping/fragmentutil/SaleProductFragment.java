package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.activityutil.ProductDetail;
import com.mg.shopping.adapterutil.SaleProductAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.saleproductutil.ListOfDatumData;
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
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class SaleProductFragment extends BaseFragment {
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    SaleProductAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    LayoutMarginDecoration itemDecoration;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(List<ListOfDatumData> dataObject) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constant.IntentKey.DATA_OBJECT, new ArrayList<>(dataObject));
        Fragment fragment = new SaleProductFragment();
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
        return R.layout.activity_sale_product;
    }

    @Override
    protected void getIntentData() {
        objectArrayList.addAll(getArguments().getParcelableArrayList(Constant.IntentKey.DATA_OBJECT));
    }

    @Override
    protected void initUI(View view) {

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new SaleProductAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatumData productDatum = (ListOfDatumData) objectArrayList.get(pos);

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

                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                            .setId(productDatum.getId())
                            .setName(productDatum.getName())
                            .setPrice(productDatum.getPrice())
                            .setRating(productDatum.getRating()));
                    startActivity(intent);


                }
            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(2, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);


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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            BottomNavigationSample.showProductCartBottomSheet(context,dtObject);

        }



    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        Utility.Logger(tag,"Size = "+objectArrayList.size());
        dataAdapter.notifyDataSetChanged();

    }


}




