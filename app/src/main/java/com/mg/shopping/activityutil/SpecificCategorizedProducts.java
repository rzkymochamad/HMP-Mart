package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mg.shopping.adapterutil.CategorizedProductAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class SpecificCategorizedProducts extends BaseActivity {
    static final String FUNC_RETRIEVE_CATEGORIZED_PRODUCT = "retrieve_categorized_product";
    ImageView imageBack;
    ImageView imageOther;
    TextView txtMenu;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    CategorizedProductAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    DataObject dataObject;
    LayoutMarginDecoration itemDecoration;
    private String categoryType;

    int visibleThreshold = 1;
    private boolean loading;
    private String skipIds;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        initUI(); //Initialize UI


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
        return R.layout.activity_specific_categorized_product;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageOther = findViewById(R.id.image_other);
        imageOther.setVisibility(View.GONE);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(dataObject.getCategoryName());

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new CategorizedProductAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum productDatum = (ListOfDatum) objectArrayList.get(pos);

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
                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality(FUNC_RETRIEVE_CATEGORIZED_PRODUCT)
                                        .setType(categoryType)
                                        .setSkipIds(skipIds)
                                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                                        .setId(dataObject.getCategoryId())))
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));

                    }

                }
            }
        });


        categoryType = dataObject.getCategoryType();
        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality(FUNC_RETRIEVE_CATEGORIZED_PRODUCT)
                        .setType(categoryType)
                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                        .setId(dataObject.getCategoryId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS) {

                jsonObject.accumulate("category_id", jsObject.getId());
                jsonObject.accumulate("category_type", jsObject.getType());

                if (!Utility.isEmptyString(jsObject.getSkipIds()))
                    jsonObject.accumulate("skip_ids", jsObject.getSkipIds());

            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS) {

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


        } else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            BottomNavigationSample.showProductCartBottomSheet(context, dtObject);

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




