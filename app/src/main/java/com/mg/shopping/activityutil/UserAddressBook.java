package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.adapterutil.UserAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConfirmationCallback;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.specificuseraddressbookutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserAddressBook extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;
    ImageView imageOther;
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    UserAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    LayoutMarginDecoration itemDecoration;
    boolean loading;
    StringBuilder stringBuilder = new StringBuilder();
    CardView layoutAddAddress;

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
        return R.layout.activity_user_adddress_book;
    }

    @Override
    protected void getIntentData() {
        //do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.address_book));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);

        layoutAddAddress = findViewById(R.id.layout_add_address);

        objectArrayList.add(new ProgressObject());

        layoutManager = new GridLayoutManager(context,1, LinearLayoutManager.VERTICAL,false);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recycler_view_all_brands);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new UserAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction());
                final int pos = selectionObject.getPosition();
                final ListOfDatum datum = (ListOfDatum) objectArrayList.get(pos);

                showConfirmationBottomSheet(context,Utility.getStringFromRes(context,R.string.want_delete_item), new ConfirmationCallback() {
                    @Override
                    public void onDone() {
                        Utility.Logger(tag,"Confirmation Done");

                        management.sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("delete_specific_address")
                                        .setConnection(Constant.CONNECTION.DELETE_SPECIFIC_ADDRESS_BOOK)
                                        .setId(datum.getId())))
                                .setFirstRequest(true)
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.DELETE_SPECIFIC_ADDRESS_BOOK)
                                .setConnectionCallback(new ConnectionCallback() {
                                    @Override
                                    public void onSuccess(Object data, RequestObject requestObject) {
                                        objectArrayList.remove(pos);
                                        dataAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(String data, RequestObject requestObject) {
                                        Utility.Logger(tag,"Message = "+data);
                                    }
                                }));

                    }

                    @Override
                    public void onCancel() {
                        Utility.Logger(tag,"Confirmation Cancel");
                    }
                });

            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_available_addresses")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK));


        imageBack.setOnClickListener(this);
        layoutAddAddress.setOnClickListener(this);

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
            
            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK){
                
                jsonObject.accumulate("user_id", jsObject.getUserId());

            }
            else if (jsObject.getConnection() == Constant.CONNECTION.DELETE_SPECIFIC_ADDRESS_BOOK){
                
                jsonObject.accumulate("address_id", jsObject.getId());

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
        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK
            || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS) {

            loading = false;
            objectArrayList.clear();
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

        objectArrayList.clear();
        dataAdapter.notifyDataSetChanged();
        Utility.Logger(tag, data);

    }

    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v==layoutAddAddress){

            startActivityForResult(new Intent(context, AddBillingAddress.class), Constant.RequestCode.ADDRESS_REQUEST);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.ADDRESS_REQUEST && resultCode == RESULT_OK) {

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_available_addresses")
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK)
                            .setUserId( management.getPreferences(null).getUserId())))
                    .setLoadingText(Utility.getStringFromRes(context, R.string.loading))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK));

        }


    }


}
