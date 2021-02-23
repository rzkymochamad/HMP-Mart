package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.adapterutil.BrandAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.Brand;
import com.mg.shopping.jsonutil.listofbrandutil.ListOfDatum;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserFollowing extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    ImageView imageBack;
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    BrandAdapter dataAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    LayoutMarginDecoration itemDecoration;
    int visibleThreshold = 1;
    boolean loading;
    StringBuilder stringBuilder = new StringBuilder();
    ImageView imageOther;

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
        return R.layout.activity_all_brands;
    }

    @Override
    protected void getIntentData() {
        //do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.user_following));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);


        layoutManager = new GridLayoutManager(context,1, LinearLayoutManager.VERTICAL,false);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recycler_view_all_brands);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new BrandAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum brand = (ListOfDatum) objectArrayList.get(pos);

                Intent intent = new Intent(context, BrandDetail.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT,new Brand()
                        .setId(brand.getId())
                        .setName(brand.getName())
                        .setImage(brand.getImage())
                        .setCoverPhoto(brand.getCoverPhoto())
                        .setItems(brand.getItems())
                        .setRating(brand.getRating())
                        .setReviews(brand.getCount()));

                startActivity(intent);



            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_user_following")
                        .setConnection(Constant.CONNECTION.RETRIEVE_ALL_BRANDS)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_ALL_BRANDS));


        imageBack.setOnClickListener(this);

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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_ALL_BRANDS) {

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

    @Override
    public void onClick(View v) {

        if (v == imageBack) {
            finish();
        }

    }


}
