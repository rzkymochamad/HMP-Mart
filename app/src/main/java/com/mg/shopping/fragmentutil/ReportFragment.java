package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mg.shopping.activityutil.AddRefund;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.adapterutil.ReportAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.specificuserrefundutil.ListOfDatum;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class ReportFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    ReportAdapter dataAdapter;
    LayoutMarginDecoration itemDecoration;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String type;
    private SwipeRefreshLayout swipeRefresh;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.DATA_OBJECT, type);
        Fragment fragment = new ReportFragment();
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
        return R.layout.activity_report;
    }

    @Override
    protected void getIntentData() {
        type = getArguments().getString(Constant.IntentKey.DATA_OBJECT,"0");
    }

    @Override
    protected void initUI(View view) {

        objectArrayList.clear();

        swipeRefresh = view.findViewById(R.id.swipe_refresh);

        layoutManager = new GridLayoutManager(context,1, LinearLayoutManager.VERTICAL,false);
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new ReportAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum datum = (ListOfDatum) objectArrayList.get(pos);

                if (type.equalsIgnoreCase("0")){

                    Intent intent = new Intent(context, AddRefund.class);
                    intent.putExtra(Constant.IntentKey.TYPE,"0");
                    intent.putExtra(Constant.IntentKey.ID,datum.getOrderId());
                    intent.putExtra(Constant.IntentKey.REFUND_REQUEST_ID,datum.getId());
                    startActivity(intent);

                }

            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        onRefresh();

        swipeRefresh.setOnRefreshListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REPORT) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("user_id", jsObject.getUserId());
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

        if (swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }

        objectArrayList.clear();
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


    }

    @Override
    public void onRefresh() {

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_specific_user_report")
                        .setUserId(BottomNavigationSample.getUserId())
                        .setType(type)
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REPORT)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REPORT));


    }
}




