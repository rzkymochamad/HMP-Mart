package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mg.shopping.adapterutil.CouponAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.listofcouponutil.ListOfDatumCoupon;
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


public class BrandCouponFragment extends BaseFragment  {
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    CouponAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    LayoutMarginDecoration itemDecoration;
    String type;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.DATA_OBJECT, type);
        Fragment fragment = new BrandCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }


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
        type = getArguments().getString(Constant.IntentKey.DATA_OBJECT,"0");
    }

    @Override
    protected void initUI(View view) {

        objectArrayList.clear();

        layoutManager = new GridLayoutManager(context,1, LinearLayoutManager.VERTICAL,false);
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new CouponAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatumCoupon couponDatum = (ListOfDatumCoupon) objectArrayList.get(pos);
                Utility.Logger(tag,couponDatum.getCode());
                Utility.copyData(context,couponDatum.getCode());


            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_coupon_data")
                        .setType(type)
                        .setConnection(Constant.CONNECTION.RETRIEVE_COUPON_DATA)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_COUPON_DATA));


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_COUPON_DATA) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("type", jsObject.getType());

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


        objectArrayList.addAll(dtObject.getObjectList());
        dataAdapter.notifyDataSetChanged();


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




