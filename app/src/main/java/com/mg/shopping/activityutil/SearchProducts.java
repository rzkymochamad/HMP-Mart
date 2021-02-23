package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.shopping.adapterutil.SearchProductAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.hotsearchesutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.nex3z.flowlayout.FlowLayout;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class SearchProducts extends BaseActivity implements View.OnClickListener, TextWatcher , TextView.OnEditorActionListener {
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    SearchProductAdapter dataAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    DataObject dataObject;
    TextView txtCancel;
    TextView txtType;
    LayoutMarginDecoration itemDecoration;

    boolean loading;
    FlowLayout layoutScroller;

    EditText editSearch;
    TextView txtTopSearches;

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
        return R.layout.activity_search_product;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        txtCancel = findViewById(R.id.txt_cancel);

        txtTopSearches = findViewById(R.id.txt_top_searches);
        layoutScroller = findViewById(R.id.layout_scroller);
        editSearch = findViewById(R.id.edit_search);

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);



        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_trending_searches")
                        .setConnection(Constant.CONNECTION.RETRIEVE_HOT_SEARCHES)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_HOT_SEARCHES));

         editSearch.setOnEditorActionListener(this);
         txtCancel.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

                jsonObject.accumulate("product_id", jsObject.getId());

            }
            else  if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SEARCH_PRODUCT) {

                jsonObject.accumulate("search", jsObject.getService());

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
    public void onClick(View v) {
        if (v==txtCancel){
            finish();
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        final DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_HOT_SEARCHES) {

            loading = false;

            layoutScroller.removeAllViews();
            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                View customView = LayoutInflater.from(context).inflate(R.layout.hot_searches_item_layout,null,false);
                final TextView txtSearchHot = customView.findViewById(R.id.txt_search);
                txtSearchHot.setText(((ListOfDatum) dtObject.getObjectList().get(i)).getName());

                final LinearLayout layoutSearch = customView.findViewById(R.id.layout_search);
                layoutSearch.setTag(i);
                layoutSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) layoutSearch.getTag();
                        editSearch.setText( ((ListOfDatum) dtObject.getObjectList().get(pos)).getName());
                    }
                });


                layoutScroller.addView(customView);

            }


        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            BottomNavigationSample.showProductCartBottomSheet(context,dtObject);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SEARCH_PRODUCT) {

            layoutScroller.setVisibility(View.GONE);
            txtTopSearches.setVisibility(View.GONE);
            recyclerViewProduct.setVisibility(View.VISIBLE);

            objectArrayList.clear();
            objectArrayList.addAll(dtObject.getObjectList());

            dataAdapter = new SearchProductAdapter(context, objectArrayList, new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {
                    Utility.Logger(tag, selectionObject.getAction());
                    int pos = selectionObject.getPosition();
                    com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum productDatum = (com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum) objectArrayList.get(pos);

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

                        com.mg.shopping.jsonutil.listofproductutil.ListOfDatum
                                product = new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum();

                        product.setId(productDatum.getId());
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

            itemDecoration = new LayoutMarginDecoration(3, Utility.dpToPx(5));
            recyclerViewProduct.addItemDecoration(itemDecoration);


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!Utility.isEmptyString(s.toString())){

            if (txtTopSearches.getVisibility() == View.GONE){
                return;
            }

            performSearch(s.toString());

        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            performSearch(editSearch.getText().toString());

            return true;
        }
        return false;
    }


    /**
     * <p>It is used for searching products</p>
     * @param search
     */
    private void performSearch(String search){

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_searches_products")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SEARCH_PRODUCT)
                        .setService(search)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SEARCH_PRODUCT));

    }

}




